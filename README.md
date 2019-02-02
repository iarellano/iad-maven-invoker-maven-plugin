# iad-maven-invoker-maven-plugin
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 
[![Maven Central](https://img.shields.io/maven-central/v/com.github.iarellano/iad-maven-invoker-maven-plugin.svg)](https://mvnrepository.com/artifact/com.github.iarellano/iad-maven-invoker-maven-plugin)

## Maven Invoker maven plugin

This is a simple maven plugin wrapper for the [Apache Maven Invoker](https://maven.apache.org/shared/maven-invoker/) which allows
to invoke a Maven execution on an independent maven project.

## Table of contents
- [Getting started](#Getting-started)
- [Example](#Example)
- [Unit Testing](#Unit-Testing)
- [Parameters](#Parameters)
    - [localRepository](#localRepository) 
    - [baseDirectory](#baseDirectory) 
    - [workingDirectory](#workingDirectory) 
    - [pomFile](#pomFile) 
    - [goals](#goals) 
    - [profiles](#profiles) 
    - [mavenOpts](#mavenOpts) 
    - [recursive](#recursive) 
    - [alsoMake](#alsoMake) 
    - [alsoMakeDependents](#alsoMakeDependents) 
    - [debug](#debug) 
    - [resumeFrom](#resumeFrom) 
    - [globalSettingsFile](#globalSettingsFile) 
    - [userSettingsFile](#userSettingsFile) 
    - [mavenHome](#mavenHome) 
    - [javaHome](#javaHome) 
    - [properties](#properties) 
    - [environment](#environment) 
    - [shellEnvironmentInherited](#shellEnvironmentInherited) 
    - [propertiesFile](#propertiesFile) 
    - [projects](#projects) 
    - [threads](#threads) 
    - [offline](#offline) 
    - [showErrors](#showErrors) 
    - [updateSnapshots](#updateSnapshots) 
    - [toolchainsFile](#toolchainsFile) 
    - [globalToolchainsFile](#globalToolchainsFile) 
    - [nonPluginUpdates](#nonPluginUpdates) 
    - [globalChecksumPolicy](#globalChecksumPolicy) 
    - [reactorFailureBehavior](#reactorFailureBehavior) 
    - [timeoutInSeconds](#timeoutInSeconds) 

## Getting started

Check the example at [example/invoker-project](example/invoker-project) which builds project [example/target-project](example/target-project).

## Example

```xml
<plugin>
    <groupId>com.github.iarellano</groupId>
    <artifactId>iad-maven-invoker-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <id>build-project</id>
        <phase>process-resources</phase>
        <goals>
            <goal>execute</goal>
        </goals>
        <configuration>
            <baseDirectory>/path/to/other/project</baseDirectory>
            <pomFile>pom.xml</pomFile>
            <goals>
                <goal>clean</goal>
                <goal>compile</goal>
                <goal>test</goal>
                <goal>install</goal>
            </goals>
          <debug>true</debug>
        </configuration>
    </executions>
</plugin>
```
## Unit Testing
```bash
mvn clean test
```

## Parameters
- [localRepository](#localRepository) 
- [baseDirectory](#baseDirectory) 
- [workingDirectory](#workingDirectory) 
- [pomFile](#pomFile) 
- [goals](#goals) 
- [profiles](#profiles) 
- [mavenOpts](#mavenOpts) 
- [recursive](#recursive) 
- [alsoMake](#alsoMake) 
- [alsoMakeDependents](#alsoMakeDependents) 
- [debug](#debug) 
- [resumeFrom](#resumeFrom) 
- [globalSettingsFile](#globalSettingsFile) 
- [userSettingsFile](#userSettingsFile) 
- [mavenHome](#mavenHome) 
- [javaHome](#javaHome) 
- [properties](#properties) 
- [environment](#environment) 
- [shellEnvironmentInherited](#shellEnvironmentInherited) 
- [propertiesFile](#propertiesFile) 
- [projects](#projects) 
- [threads](#threads) 
- [offline](#offline) 
- [showErrors](#showErrors) 
- [updateSnapshots](#updateSnapshots) 
- [toolchainsFile](#toolchainsFile) 
- [globalToolchainsFile](#globalToolchainsFile) 
- [nonPluginUpdates](#nonPluginUpdates) 
- [globalChecksumPolicy](#globalChecksumPolicy) 
- [reactorFailureBehavior](#reactorFailureBehavior) 
- [timeoutInSeconds](#timeoutInSeconds) 


### localRepository
The path to the base directory of the local repository to use for the Maven invocation. Defaults to ```${settings.localRepository}```.

|          |     |
| -------- | --- |
| Type     | File path                         |
| Default  | ```${settings.localRepository}``` |
| Required | Yes                               |

[Go to parameters list](#Parameters)

### baseDirectory
The path to the base directory of the POM for the Maven invocation. This setting only affects the working directory for the Maven invocation.

|          |     |
| -------- | --- |
| Type     | File path    |
| Required | Yes          |

[Go to parameters list](#Parameters)

### workingDirectory
The working directory for the Maven invocation.

|          |     |
| -------- | --- |
| Type     | File path                               |
| Default  | Same as [baseDirectory](#baseDirectory) |
| Required | Yes                                     |

[Go to parameters list](#Parameters)

### pomFile
The name of the POM file for the Maven invocation which is contained in {@link #baseDirectory}.

|          |     |
| -------- | --- |
| Type     | String    |
| Default  | pom.xml   |
| Required | Yes       |

[Go to parameters list](#Parameters)

### goals
The goals for the Maven invocation.

|          |     |
| -------- | --- |
| Type     | List of Strings  |
| Required | Yes              |

[Go to parameters list](#Parameters)

### profiles
The profiles for the Maven invocation. Equivalent of {@code -P} and {@code --active-profiles}.

|          |     |
| -------- | --- |
| Type     | List of Strings  |
| Required | No               |

[Go to parameters list](#Parameters)

### mavenOpts
The value of the <code>MAVEN_OPTS</code> environment variable. Uses the default options if not provided.

|          |     |
| -------- | --- |
| Type     | String                  |
| Default  | Default maven options   |
| Required | No                      |

[Go to parameters list](#Parameters)

### recursive
Recursion behavior of a reactor invocation. <em>Inverse</em> equivalent of {@code -N} and {@code --non-recursive}. Defaults to <code>true</code>.

|          |     |
| -------- | --- |
| Type     | boolean  |
| Default  | true     |
| Required | No       |

[Go to parameters list](#Parameters)

### alsoMake
Enable the 'also make' mode. Equivalent of {@code -am} or {@code --also-make}. Defaults to <code>false</code>.

|          |     |
| -------- | --- |
| Type          | boolean  |
| Default       | false    |
| Required      | No       |
| Since         | 2.1      |

[Go to parameters list](#Parameters)

### alsoMakeDependents
Enable the 'also make dependents' mode. Equivalent of {@code -amd} or {@code --also-make-dependents}.

|          |     |
| -------- | --- |
| Type     | boolean  |
| Default  | false    |
| Required | No       |
| Since    | 2.1      |

[Go to parameters list](#Parameters)

### debug
Enable the debug mode of the Maven invocation. Equivalent of {@code -X} and {@code --debug}. Defaults to <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | false   |
| Required | No      |

[Go to parameters list](#Parameters)

### resumeFrom
Resume reactor from specified project. Equivalent of {@code -rf} or {@code --resume-from}.

|          |     |
| -------- | --- |
| Type     | String |
| Required | No     |
| Since    | 2.1    |

[Go to parameters list](#Parameters)

### globalSettingsFile
The path to the global settings for the Maven invocation. Equivalent of {@code -gs} and
{@code --global-settings}. If not provided then it uses global settings from the default location.

|          |     |
| -------- | --- |
| Type     | File path                             |
| Default  | ```${maven.home}/conf/settings.xml``` |
| Required | No                                    |
| Since    | 2.1                                   |

[Go to parameters list](#Parameters)

### userSettingsFile
The path to the user settings for the Maven invocation. Equivalent of {@code -s} and {@code --settings}. If
not provided then it uses the user settings from the default location.

|          |     |
| -------- | --- |
| Type     | File path                              |
| Default  | ```${user.home}/.m2/settings.xml```    |
| Required | No                                     |

[Go to parameters list](#Parameters)

### mavenHome
Sets the path to the base directory of the Maven installation used to invoke Maven. This parameter may be left
unspecified to use the default Maven installation which will be discovered by evaluating the system property
<code>maven.home</code> and the environment variable <code>M2_HOME</code>. Change it if you want to use a
different maven installation.

|          |     |
| -------- | --- |
| Type     | File path            |
| Default  | ```${maven.home}```  |
| Required | No                   |

[Go to parameters list](#Parameters)

### javaHome
Sets the path to the base directory of the Java installation used to run Maven. If not provided then it uses the default Java home.

|          |     |
| -------- | --- |
| Type     | No                  |
| Default  | ```${java.home}```  |
| Required | No                  |

[Go to parameters list](#Parameters)

### properties
Additional properties to pass to the Maven invocation.

|          |     |
| -------- | --- |
| Type     | [Map](https://maven.apache.org/guides/mini/guide-configuring-plugins.html#Mapping_Maps) |
| Required | No            |

[Go to parameters list](#Parameters)

### environment
Additional environment variables to pass to the Maven invocation.

|          |     |
| -------- | --- |
| Type     | [Map](https://maven.apache.org/guides/mini/guide-configuring-plugins.html#Mapping_Maps) |
| Required | No    |

[Go to parameters list](#Parameters)

### shellEnvironmentInherited
Specifies whether the environment variables of the current process should be propagated to the Maven invocation.

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | true    |
| Required | No      |

[Go to parameters list](#Parameters)

### propertiesFile
Path to external file containing properties to be included in the Maven invocation. Path is relative to the base
directory of the project using this plugin, absolute path may be set.

|          |     |
| -------- | --- |
| Type     | File path  |
| Required | No         |

[Go to parameters list](#Parameters)

### projects
Sets the reactor project list. Equivalent of {@code -pl} or {@code --projects}, ignored if {@code #recusive} is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | List of Strings |
| Required | No              |

[Go to parameters list](#Parameters)

### threads
Thread count, for instance 2.0C where C is core multiplied Equivalent of {@code -T} or {@code --threads}.
|          |     |
| -------- | --- |
| Type     | String |
| Required | No     |
| Since    | 2.1    |

[Go to parameters list](#Parameters)

### offline
The network mode of the Maven invocation. Equivalent of {@code -o} and {@code --offline},  <code>true</code> if
Maven should be executed in offline mode, <code>false</code> if the online. Default is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | false   |
| Required | No      |

[Go to parameters list](#Parameters)

### showErrors
Sets the exception output mode of the Maven invocation. Equivalent of {@code -e} and {@code --errors}.
<code>true</code> if Maven should print stack traces, <code>false</code> otherwise. Default is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | false   |
| Required | No      |

[Go to parameters list](#Parameters)

### updateSnapshots
Specifies whether Maven should enforce an update check for plugins and snapshots. Equivalent of {@code -U} and
{@code --update-snapshots}. <code>true</code> if plugins and snapshots should be updated, <code>false</code>
otherwise. Default is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolen |
| Default  | false  |
| Required | No     |

[Go to parameters list](#Parameters)

### toolchainsFile
The alternate path for the user toolchains file Equivalent of {@code -t} or {@code --toolchains}.

|          |     |
| -------- | --- |
| Type     | File path |
| Required | No        |
| Since    | 2.1       |

[Go to parameters list](#Parameters)

### globalToolchainsFile
The alternate path for the global toolchains file Equivalent of {@code -gt} or {@code --global-toolchains}

|          |     |
| -------- | --- |
| Type     | File path  |
| Required | No         |
| Since    | 3.0.0      |

[Go to parameters list](#Parameters)

### nonPluginUpdates
Specifies whether Maven should check for plugin updates.
<p>
    Equivalent of {@code -npu} or {@code --no-plugin-updates}
</p>
<p>
    <strong>note: </strong>Ineffective with Maven3, only kept for backward compatibility
</p>

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | false   |
| Required | No      |

[Go to parameters list](#Parameters)

### globalChecksumPolicy
Checksum mode of the Maven invocation. Equivalent of {@code -c} or {@code --lax-checksums}, {@code -C}
or {@code --strict-checksums}. The checksum mode, must be one of {@link org.apache.maven.shared.invoker.InvocationRequest.CheckSumPolicy#Warn} and
{@link org.apache.maven.shared.invoker.InvocationRequest.CheckSumPolicy#Fail}.

|          |     |
| -------- | --- |
| Type     | String  |
| Possible values     | Warn,  Fail  |
| Required | No     |
| Since    | 3.0.0  |

[Go to parameters list](#Parameters)

### reactorFailureBehavior
Sets the failure mode of the Maven invocation. Equivalent of {@code -ff} and {@code --fail-fast}, {@code -fae}
and {@code --fail-at-end}, {@code -fn} and {@code --fail-never}

The failure mode, must be one of {@link org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior#FailFast},
{@link org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior#FailAtEnd} and {@link org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior#FailNever}.

|          |     |
| -------- | --- |
| Type     | String    |
| Possible values      | FailFast,  FailAtEnd, FailNever  |
| Default  | FailFast  |
| Required | No          |
| Since    | 3.0.0     |

[Go to parameters list](#Parameters)

### timeoutInSeconds
The timeout in seconds to execute the project. A value of <code>0</code> means no timeout.

|          |     |
| -------- | --- |
| Type     | int    |
| Default  | 0      |
| Required | No     |
| Since    | 3.0.1  |

[Go to parameters list](#Parameters)