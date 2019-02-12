package com.github.iarellano.maven_invoker;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.invoker.*;
import static org.apache.maven.shared.invoker.InvocationRequest.CheckSumPolicy;
import static org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "execute", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MavenInvokerMojo extends AbstractMojo {

    /**
     * The path to the base directory of the local repository to use for the Maven invocation.
     * Defaults to ${settings.localRepository}
     */
    @Parameter(defaultValue = "${settings.localRepository}")
    private File localRepository;

    /**
     * The path to the base directory of the POM for the Maven invocation. This setting only affects
     * the working directory for the Maven invocation.
     */
    @Parameter(required = true)
    private File baseDirectory;

    /**
     * The working directory for the Maven invocation, may be <code>null</code> to derive the
     * working directory from the base directory of the processed POM.
     */
    private File workingDirectory;

    /**
     * The name of the POM file for the Maven invocation which is contained in {@link #baseDirectory}.
     * Defaults to {@code pom.xml}
     */
    @Parameter(defaultValue = "pom.xml", required = true)
    private String pomFile;

    /**
     * The goals for the Maven invocation.
     */
    @Parameter(required = true)
    private List<String> goals;

    /**
     * The profiles for the Maven invocation. Equivalent of {@code -P} and {@code --active-profiles}.
     */
    @Parameter
    private List<String> profiles = new ArrayList<>();

    /**
     * Sets the value of the <code>MAVEN_OPTS</code> environment variable. Uses the default options if not provided.
     */
    @Parameter
    private String mavenOpts;

    /**
     * Recursion behavior of a reactor invocation. <em>Inverse</em> equivalent of {@code -N} and
     * {@code --non-recursive}. Defaults to <code>true</code>.
     */
    @Parameter(defaultValue = "true")
    private boolean recursive = true;

    /**
     * Enable the 'also make' mode. Equivalent of {@code -am} or {@code --also-make}. Defaults to <code>false</code>.
     * @since 2.1
     */
    @Parameter(defaultValue = "false")
    private boolean alsoMake;

    /**
     * Enable the 'also make dependents' mode. Equivalent of {@code -amd} or {@code --also-make-dependents}.
     * @since 2.1
     */
    @Parameter(defaultValue = "false")
    private boolean alsoMakeDependents;

    /**
     * Enable the debug mode of the Maven invocation. Equivalent of {@code -X} and {@code --debug}. Defaults to <code>false</code>.
     */
    @Parameter(defaultValue = "false")
    private boolean debug;

    /**
     * Resume reactor from specified project. Equivalent of {@code -rf} or {@code --resume-from}
     * @since 2.1
     */
    @Parameter
    private String resumeFrom;

    /**
     * The path to the global settings for the Maven invocation. Equivalent of {@code -gs} and
     * {@code --global-settings}. If not provided then it uses global settings from the default location.
     * @since 2.1
     */
    @Parameter(defaultValue = "${maven.home}/conf/settings.xml", required = true)
    private File globalSettingsFile;

    /**
     * The path to the user settings for the Maven invocation. Equivalent of {@code -s} and {@code --settings}. If
     * not provided then it uses the user settings from the default location.
     */
    @Parameter(defaultValue = "${user.home}/.m2/settings.xml", required = true)
    private File userSettingsFile;

    /**
     * Sets the path to the base directory of the Maven installation used to invoke Maven. This parameter may be left
     * unspecified to use the default Maven installation which will be discovered by evaluating the system property
     * <code>maven.home</code> and the environment variable <code>M2_HOME</code>. Change it if you want to use a
     * different maven installation.
     */
    @Parameter(defaultValue = "${maven.home}", required = true)
    private File mavenHome;

    /**
     * Sets the path to the base directory of the Java installation used to run Maven. If not provided then it uses
     * the default Java home.
     */
    @Parameter(defaultValue = "${java.home}", required = true)
    private File javaHome;

    /**
     * Additional properties to pass to the Maven invocation.
     */
    @Parameter
    private Properties properties = new Properties();

    /**
     * Additional environment variables to pass to the Maven invocation.
     */
    @Parameter
    private Map<String, String> environment = new HashMap<>();

    /**
     * Specifies whether the environment variables of the current process should be propagated to the Maven invocation.
     * Defatults to <code>true</code>.
     */
    @Parameter(defaultValue = "true")
    private boolean shellEnvironmentInherited = true;

    /**
     * Path to external file containing properties to be included in the Maven invocation. Path is relative to the base
     * directory of the project using this plugin, absolute path may be provided.
     */
    @Parameter
    private File propertiesFile;

    /**
     * Sets the reactor project list. Equivalent of {@code -pl} or {@code --projects}, ignored if {@code #recusive}
     * is <code>false</code>.
     */
    @Parameter
    private List<String> projects;

    /**
     * Thread count, for instance 2.0C where C is core multiplied Equivalent of {@code -T} or {@code --threads}.
     * @since 2.1
     */
    @Parameter
    private String threads;

    /**
     * The network mode of the Maven invocation. Equivalent of {@code -o} and {@code --offline},  <code>true</code> if
     * Maven should be executed in offline mode, <code>false</code> if the online. Default is <code>false</code>.
     */
    @Parameter(defaultValue = "false")
    private boolean offline;

    /**
     * Sets the exception output mode of the Maven invocation. Equivalent of {@code -e} and {@code --errors}.
     * <code>true</code> if Maven should print stack traces, <code>false</code> otherwise. Default is <code>false</code>.
     */
    @Parameter(defaultValue = "false")
    private boolean showErrors;

    /**
     * Specifies whether Maven should enforce an update check for plugins and snapshots. Equivalent of {@code -U} and
     * {@code --update-snapshots}. <code>true</code> if plugins and snapshots should be updated, <code>false</code>
     * otherwise. Default is <code>false</code>.
     */
    @Parameter(defaultValue = "false")
    private boolean updateSnapshots;

    /**
     * The alternate path for the user toolchains file Equivalent of {@code -t} or {@code --toolchains}.
     * @since 2.1
     */
    @Parameter
    private File toolchainsFile;

    /**
     * The alternate path for the global toolchains file Equivalent of {@code -gt} or {@code --global-toolchains}
     * @since 3.0.0
     */
    @Parameter
    private File globalToolchainsFile;

    /**
     * Specifies whether Maven should check for plugin updates.
     * <p>
     * Equivalent of {@code -npu} or {@code --no-plugin-updates}
     * </p>
     * <p>
     * <strong>note: </strong>Ineffective with Maven3, only kept for backward compatibility
     * </p>
     */
    @Parameter(defaultValue = "false")
    private boolean nonPluginUpdates;

    /**
     * Sets the checksum mode of the Maven invocation. Equivalent of {@code -c} or {@code --lax-checksums}, {@code -C}
     * or {@code --strict-checksums}. The checksum mode, must be one of {@link org.apache.maven.shared.invoker.InvocationRequest.CheckSumPolicy#Warn} and
     *            {@link org.apache.maven.shared.invoker.InvocationRequest.CheckSumPolicy#Fail}.
     * @since 3.0.0
     */
    @Parameter
    private CheckSumPolicy globalChecksumPolicy;

    /**
     * Sets the failure mode of the Maven invocation. Equivalent of {@code -ff} and {@code --fail-fast}, {@code -fae}
     * and {@code --fail-at-end}, {@code -fn} and {@code --fail-never}
     *
     * The failure mode, must be one of {@link org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior#FailFast},
     *            {@link org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior#FailAtEnd} and {@link org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior#FailNever}.
     * @since 3.0.0
     */
    @Parameter(defaultValue = "FailFast")
    private ReactorFailureBehavior reactorFailureBehavior = ReactorFailureBehavior.FailFast;

    /**
     * The timeout in seconds to execute the project. A value of <code>0</code> means no timeout.
     * @since 3.0.1
     */
    @Parameter(defaultValue = "0")
    private int timeoutInSeconds = 0;

    private int exitCode;

    private Properties mergeProperties() throws MojoExecutionException {
        Properties mergedProperties = new Properties();
        if (propertiesFile != null) {
            try {
                mergedProperties.load(new FileInputStream(propertiesFile));
            } catch (IOException e) {
                throw new MojoExecutionException("Could no read properties file: " + propertiesFile, e);
            }
        }
        mergedProperties.putAll(properties);
        return mergedProperties;
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
        request.setShellEnvironmentInherited(shellEnvironmentInherited);
        request.setProjects(projects);
        request.setThreads(threads);
        request.setOffline(offline);
        request.setShowErrors(showErrors);
        request.setUpdateSnapshots(updateSnapshots);
        request.setLocalRepositoryDirectory(localRepository);
        request.setToolchainsFile(toolchainsFile);
        request.setGlobalToolchainsFile(globalToolchainsFile);
        request.setGlobalChecksumPolicy(globalChecksumPolicy);
        request.setReactorFailureBehavior(reactorFailureBehavior);
        request.setTimeoutInSeconds(timeoutInSeconds);

        for (String envVar : environment.keySet()) {
            request.addShellEnvironment(envVar, environment.get(envVar));
        }
        request.setShowVersion(true);

        getLog().info("Building project " + pom.getPath());
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(mavenHome);
        invoker.setWorkingDirectory(workingDirectory);
        try {
            InvocationResult result = invoker.execute(request);
            this.exitCode = result.getExitCode();
            if (result.getExitCode() != 0) {
                throw new MojoExecutionException("Could not invoke external project " + pom.getAbsolutePath(), result.getExecutionException());
            }

        } catch (MavenInvocationException e) {
            this.exitCode = 0;
            e.printStackTrace();
        }
        getLog().info("------------------------------------- Build completed -------------------------------------");
    }

    public int getExitCode() {
        return exitCode;
    }
}
