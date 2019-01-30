package com.github.iarellano.maven_invoker;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MavenInvokerMojo extends AbstractMojo {

    /**
     * Sets the path to the base directory of the POM for the Maven invocation. If @link #getPomFile()} does not return
     * <code>null</code>, this setting only affects the working directory for the Maven invocation.
     *
     * @param baseDirectory The path to the base directory of the POM, may be <code>null</code> if not used.
     */
    @Parameter(defaultValue = "${settings.localRepository}")
    private File localRepository;

    @Parameter(required = true)
    private File baseDirectory;

    @Parameter(defaultValue = "pom.xml", required = true)
    private String pomFile;

    @Parameter(required = true)
    private List<String> goals;

    @Parameter
    private List<String> profiles = new ArrayList<>();

    @Parameter
    private String mavenOpts;

    @Parameter(defaultValue = "true")
    private boolean recursive;

    @Parameter(defaultValue = "false")
    private boolean alsoMake;

    @Parameter(defaultValue = "false")
    private boolean alsoMakeDependents;

    @Parameter(defaultValue = "false")
    private boolean debug;

    @Parameter
    private String resumeFrom;

    @Parameter(defaultValue = "${maven.home}/conf/settings.xml", required = true)
    private File globalSettingsFile;

    @Parameter(defaultValue = "${user.home}/.m2/settings.xml", required = true)
    private File userSettingsFile;

    @Parameter(defaultValue = "${maven.home}", required = true)
    private File mavenHome;

    @Parameter(defaultValue = "${java.home}", required = true)
    private File javaHome;

    @Parameter
    private Properties properties = new Properties();

    @Parameter
    private Map<String, String> environment = new HashMap<>();

    @Parameter(defaultValue = "true")
    private boolean inheritShellEnvironment;

    @Parameter
    private File propertiesFile;

    @Parameter
    private List<String> projects;

    @Parameter
    private String threads;


    @Parameter(defaultValue = "false")
    private boolean offline;

    @Parameter(defaultValue = "false")
    private boolean showErrors;

    @Parameter(defaultValue = "false")
    private boolean updateSnapshots;

    @Parameter
    private File toolchainsFile;

    @Parameter
    private File globalToolchainsFile;

    @Parameter(defaultValue = "false")
    private boolean nonPluginUpdates;

    private Properties mergeProperties() throws MojoExecutionException {
        Properties mergedProps = new Properties();
        if (propertiesFile != null) {
            try {
                mergedProps.load(new FileInputStream(propertiesFile));
            } catch (IOException e) {
                throw new MojoExecutionException("Could no read properties file: " + propertiesFile, e);
            }
        }
        mergedProps.putAll(mergedProps);
        return mergedProps;
    }

    public void execute()

            throws MojoExecutionException {

        getLog().info("------------------------------------- Build started -------------------------------------");
        File pom = new File(baseDirectory, this.pomFile);
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pom);
        request.setGoals(goals);
        request.setBaseDirectory(baseDirectory);
        request.setProfiles(profiles);
        request.setMavenOpts(mavenOpts);
        request.setRecursive(recursive);
        request.setAlsoMake(alsoMake);
        request.setAlsoMakeDependents(alsoMakeDependents);
        request.setDebug(debug);
        request.setResumeFrom(resumeFrom);
        request.setUserSettingsFile(globalSettingsFile);
        request.setGlobalSettingsFile(userSettingsFile);
        request.setProperties(mergeProperties());
        request.setJavaHome(javaHome);
        request.setShellEnvironmentInherited(inheritShellEnvironment);
        request.setProjects(projects);
        request.setThreads(threads);
        request.setOffline(offline);
        request.setShowErrors(showErrors);
        request.setUpdateSnapshots(updateSnapshots);
        request.setLocalRepositoryDirectory(localRepository);
        request.setToolchainsFile(toolchainsFile);
        request.setGlobalToolchainsFile(globalToolchainsFile);

        for (String envVar : environment.keySet()) {
            request.addShellEnvironment(envVar, environment.get(envVar));
        }
        request.setShowVersion(true);

        getLog().info("Building project " + pom.getPath());
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(mavenHome);
        try {
            InvocationResult result = invoker.execute(request);
            if (result.getExitCode() != 0) {
                throw new MojoExecutionException("Could not invoke external project " + pom.getAbsolutePath(), result.getExecutionException());
            }
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
        getLog().info("------------------------------------- Build completed -------------------------------------");
    }

    /**
     * Sets the path to the base directory of the POM for the Maven invocation. If @link #getPomFile()} does not return
     * <code>null</code>, this setting only affects the working directory for the Maven invocation.
     *
     * @param baseDirectory The path to the base directory of the POM, may be <code>null</code> if not used.
     */
    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Sets the path to the POM for the Maven invocation. If no base directory is set, the parent directory of this POM
     * will be used as the working directory for the Maven invocation.
     *
     * @param pomFile The path to the POM for the Maven invocation, may be <code>null</code> if not used.
     */
    public void setPomFile(String pomFile) {
        this.pomFile = pomFile;
    }

    /**
     * Sets the goals for the Maven invocation.
     *
     * @param goals The goals for the Maven invocation, may be <code>null</code> to execute the POMs default goal.
     */
    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    /**
     * Sets the profiles for the Maven invocation. Equivalent of {@code -P} and {@code --active-profiles}
     *
     * @param profiles The profiles for the Maven invocation, may be <code>null</code> to use the default profiles.
     */
    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    /**
     * Sets the value of the <code>MAVEN_OPTS</code> environment variable.
     *
     * @param mavenOpts The value of the <code>MAVEN_OPTS</code> environment variable, may be <code>null</code> to use
     *                  the default options.
     */
    public void setMavenOpts(String mavenOpts) {
        this.mavenOpts = mavenOpts;
    }

    /**
     * Sets the recursion behavior of a reactor invocation. <em>Inverse</em> equivalent of {@code -N} and
     * {@code --non-recursive}
     *
     * @param recursive <code>true</code> if sub modules should be build, <code>false</code> otherwise.
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    /**
     * Enable the 'also make' mode. Equivalent of {@code -am} or {@code --also-make}
     *
     * @param alsoMake enable 'also make' mode
     * @since 2.1
     */
    public void setAlsoMake(boolean alsoMake) {
        this.alsoMake = alsoMake;
    }

    /**
     * Enable the 'also make dependents' mode. Equivalent of {@code -amd} or {@code --also-make-dependents}
     *
     * @param alsoMakeDependents enable 'also make' mode
     * @since 2.1
     */
    public void setAlsoMakeDependents(boolean alsoMakeDependents) {
        this.alsoMakeDependents = alsoMakeDependents;
    }

    /**
     * Sets the debug mode of the Maven invocation. Equivalent of {@code -X} and {@code --debug}
     *
     * @param debug <code>true</code> if Maven should be executed in debug mode, <code>false</code> if the normal mode
     *              should be used.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Resume reactor from specified project. Equivalent of {@code -rf} or {@code --resume-from}
     *
     * @param resumeFrom set the project to resume from
     * @since 2.1
     */
    public void setResumeFrom(String resumeFrom) {
        this.resumeFrom = resumeFrom;
    }

    /**
     * Sets the path to the global settings for the Maven invocation. Equivalent of {@code -gs} and
     * {@code --global-settings}
     *
     * @param globalSettingsFile The path to the global settings for the Maven invocation, may be <code>null</code> to load
     *                           the global settings from the default location.
     * @since 2.1
     */
    public void setGlobalSettingsFile(File globalSettingsFile) {
        this.globalSettingsFile = globalSettingsFile;
    }

    /**
     * Sets the path to the user settings for the Maven invocation. Equivalent of {@code -s} and {@code --settings}
     *
     * @param userSettingsFile The path to the user settings for the Maven invocation, may be <code>null</code> to load the
     *                         user settings from the default location.
     */
    public void setUserSettingsFile(File userSettingsFile) {
        this.userSettingsFile = userSettingsFile;
    }

    public void setMavenHome(File mavenHome) {
        this.mavenHome = mavenHome;
    }

    /**
     * Sets the path to the base directory of the Java installation used to run Maven.
     *
     * @param javaHome The path to the base directory of the Java installation used to run Maven, may be
     *                 <code>null</code> to use the default Java home.
     */
    public void setJavaHome(File javaHome) {
        this.javaHome = javaHome;
    }

    /**
     * Sets the system properties for the Maven invocation.
     *
     * @param properties The system properties for the Maven invocation, may be <code>null</code> if not set.
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Adds the specified environment variables to the Maven invocation.
     *
     * @param environment The environment variables, either names nor values must no be <code>null</code>.
     */
    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    /**
     * Specifies whether the environment variables of the current process should be propagated to the Maven invocation.
     *
     * @param inheritShellEnvironment <code>true</code> if the environment variables should be propagated,
     *                                  <code>false</code> otherwise.
     */
    public void setInheritShellEnvironment(boolean inheritShellEnvironment) {
        this.inheritShellEnvironment = inheritShellEnvironment;
    }

    public void setPropertiesFile(File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    /**
     * Sets the reactor project list. Equivalent of {@code -pl} or {@code --projects}
     *
     * @param projects the reactor project list
     * @since 2.1
     */
    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    /**
     * Thread count, for instance 2.0C where C is core multiplied Equivalent of {@code -T} or {@code --threads}
     * <p>
     * <strong>note: </strong>available since Maven3
     * </p>
     *
     * @param threads the threadcount
     * @since 2.1
     */
    public void setThreads(String threads) {
        this.threads = threads;
    }

    /**
     * Sets the network mode of the Maven invocation. Equivalent of {@code -o} and {@code --offline}
     *
     * @param offline <code>true</code> if Maven should be executed in offline mode, <code>false</code> if the online
     *                mode is used.
     */
    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    /**
     * Sets the exception output mode of the Maven invocation. Equivalent of {@code -e} and {@code --errors}
     *
     * @param showErrors <code>true</code> if Maven should print stack traces, <code>false</code> otherwise.
     */
    public void setShowErrors(boolean showErrors) {
        this.showErrors = showErrors;
    }

    /**
     * Specifies whether Maven should enforce an update check for plugins and snapshots. Equivalent of {@code -U} and
     * {@code --update-snapshots}
     *
     * @param updateSnapshots <code>true</code> if plugins and snapshots should be updated, <code>false</code>
     *                        otherwise.
     */
    public void setUpdateSnapshots(boolean updateSnapshots) {
        this.updateSnapshots = updateSnapshots;
    }


    /**
     * Sets the path to the base directory of the local repository to use for the Maven invocation.
     *
     * @param localRepository The path to the base directory of the local repository, may be <code>null</code>.
     */
    public void setLocalRepositoryDirectory(File localRepository) {
        this.localRepository = localRepository;
    }

    /**
     * Sets the alternate path for the user toolchainsFile file Equivalent of {@code -t} or {@code --toolchainsFile}
     *
     * @param toolchainsFile the alternate path for the user toolchainsFile file
     * @since 2.1
     */
    public void setToolchainsFile(File toolchainsFile) {
        this.toolchainsFile = toolchainsFile;
    }

    /**
     * Sets the alternate path for the global toolchainsFile file Equivalent of {@code -gt} or {@code --global-toolchainsFile}
     *
     * @param globalToolchainsFile the alternate path for the global toolchainsFile file
     * @since 3.0.0
     */
    public void setGlobalToolchainsFile(File globalToolchainsFile) {
        this.globalToolchainsFile = globalToolchainsFile;
    }

    /**
     * Specifies whether Maven should check for plugin updates.
     * <p>
     * Equivalent of {@code -npu} or {@code --no-plugin-updates}
     * </p>
     * <p>
     * <strong>note: </strong>Ineffective with Maven3, only kept for backward compatibility
     * </p>
     *
     * @param nonPluginUpdates <code>true</code> if plugin updates should be suppressed, <code>false</code> otherwise.
     */
    public void setNonPluginUpdates(boolean nonPluginUpdates) {
        this.nonPluginUpdates = nonPluginUpdates;
    }
}
