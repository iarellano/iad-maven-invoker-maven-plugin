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

1. Clone the repo
    ```bash
    git clone git@github.com:iarellano/iad-maven-invoker-maven-plugin.git
    ```
2. From within the cloned directory, change to example project directory
    ```bash
    cd example/invoker-project
    ```
3. Execute the maven goal
    ```bash
    mvn com.github.iarellano:iad-maven-invoker-maven-plugin:1.0:execute
    ```

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
1. Clone the repo
    ```bash
    git clone git@github.com:iarellano/iad-maven-invoker-maven-plugin.git
    ```
2. From within the cloned directory, change to example project directory
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
The path to the base directory of the local repository to use for the Maven invocation. Defaults to `${settings.localRepository}`.

|          |     |
| -------- | --- |
| Type     | File path                   |
| Default  | ${settings.localRepository} |
| Required | Yes                         |

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
The name of the POM file for the Maven invocation which is contained in [baseDirectory](#baseDirectory).

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
The profiles for the Maven invocation. Equivalent of `-P` and `--active-profiles`.

|          |     |
| -------- | --- |
| Type     | List of Strings  |
| Required | No               |

[Go to parameters list](#Parameters)

### mavenOpts
The value of the `MAVEN_OPTS` environment variable. Uses the default options if not provided.

|          |     |
| -------- | --- |
| Type     | String                  |
| Default  | Default maven options   |
| Required | No                      |

[Go to parameters list](#Parameters)

### recursive
Recursion behavior of a reactor invocation. <em>Inverse</em> equivalent of `-N` and `--non-recursive`. Defaults to <code>true</code>.

|          |     |
| -------- | --- |
| Type     | boolean  |
| Default  | true     |
| Required | No       |

[Go to parameters list](#Parameters)

### alsoMake
Enable the 'also make' mode. Equivalent of `-am` or `--also-make`. Defaults to <code>false</code>.

|          |     |
| -------- | --- |
| Type          | boolean  |
| Default       | false    |
| Required      | No       |
| Since         | 2.1      |

[Go to parameters list](#Parameters)

### alsoMakeDependents
Enable the 'also make dependents' mode. Equivalent of `-amd` or `--also-make-dependents`.

|          |     |
| -------- | --- |
| Type     | boolean  |
| Default  | false    |
| Required | No       |
| Since    | 2.1      |

[Go to parameters list](#Parameters)

### debug
Enable the debug mode of the Maven invocation. Equivalent of `-X` and `--debug`. Defaults to <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | false   |
| Required | No      |

[Go to parameters list](#Parameters)

### resumeFrom
Resume reactor from specified project. Equivalent of `-rf` or `--resume-from`.

|          |     |
| -------- | --- |
| Type     | String |
| Required | No     |
| Since    | 2.1    |

[Go to parameters list](#Parameters)

### globalSettingsFile
The path to the global settings for the Maven invocation. Equivalent of `-gs` and
`--global-settings`. If not provided then it uses global settings from the default location.

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
| Type     | [Map](https://maven.apache.org/guides/mini/guide-configuring-plugins.html#Mapping_Properties) |
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
Sets the reactor project list. Equivalent of `-pl` or `--projects`, ignored if [recusive](#recusive) is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | List of Strings |
| Required | No              |

[Go to parameters list](#Parameters)

### threads
Thread count, for instance <em>2.0C<em> where <em<C<em> is core multiplied Equivalent of `-T` or `--threads`.

|          |     |
| -------- | --- |
| Type     | String |
| Required | No     |
| Since    | 2.1    |

[Go to parameters list](#Parameters)

### offline
The network mode of the Maven invocation. Equivalent of `-o` and `--offline`,  <code>true</code> if
Maven should be executed in offline mode, <code>false</code> if the online. Default is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | false   |
| Required | No      |

[Go to parameters list](#Parameters)

### showErrors
Sets the exception output mode of the Maven invocation. Equivalent of `-e` and `--errors`.
<code>true</code> if Maven should print stack traces, <code>false</code> otherwise. Default is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolean |
| Default  | false   |
| Required | No      |

[Go to parameters list](#Parameters)

### updateSnapshots
Specifies whether Maven should enforce an update check for plugins and snapshots. Equivalent of `-U` and
`--update-snapshots`. <code>true</code> if plugins and snapshots should be updated, <code>false</code>
otherwise. Default is <code>false</code>.

|          |     |
| -------- | --- |
| Type     | boolen |
| Default  | false  |
| Required | No     |

[Go to parameters list](#Parameters)

### toolchainsFile
The alternate path for the user toolchains file Equivalent of `-t` or `--toolchains`.

|          |     |
| -------- | --- |
| Type     | File path |
| Required | No        |
| Since    | 2.1       |

[Go to parameters list](#Parameters)

### globalToolchainsFile
The alternate path for the global toolchains file Equivalent of `-gt` or `--global-toolchains`

|          |     |
| -------- | --- |
| Type     | File path  |
| Required | No         |
| Since    | 3.0.0      |

[Go to parameters list](#Parameters)

### nonPluginUpdates
Specifies whether Maven should check for plugin updates.
<p>
    Equivalent of `-npu` or `--no-plugin-updates`
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
Checksum mode of the Maven invocation. Equivalent of `-c` or `--lax-checksums`, `-C`
or `--strict-checksums`. The checksum mode, must be one of [Warn](https://maven.apache.org/shared/maven-invoker/apidocs/org/apache/maven/shared/invoker/InvocationRequest.CheckSumPolicy.html#Warn) and
[Fail](https://maven.apache.org/shared/maven-invoker/apidocs/org/apache/maven/shared/invoker/InvocationRequest.CheckSumPolicy.html#Fail).

|          |     |
| -------- | --- |
| Type     | String  |
| Possible values     | Warn,  Fail  |
| Required | No     |
| Since    | 3.0.0  |

[Go to parameters list](#Parameters)

### reactorFailureBehavior
Sets the failure mode of the Maven invocation. Equivalent of `-ff` and `--fail-fast`, `-fae`
and `--fail-at-end`, `-fn` and `--fail-never`.

The failure mode, must be one of [FailFast](https://maven.apache.org/shared/maven-invoker/apidocs/org/apache/maven/shared/invoker/InvocationRequest.ReactorFailureBehavior.html#FailFast),
[FailAtEnd](https://maven.apache.org/shared/maven-invoker/apidocs/org/apache/maven/shared/invoker/InvocationRequest.ReactorFailureBehavior.html#FailAtEnd)
and [FailNever](https://maven.apache.org/shared/maven-invoker/apidocs/org/apache/maven/shared/invoker/InvocationRequest.ReactorFailureBehavior.html#FailNever)

|          |     |
| -------- | --- |
| Type     | String    |
| Possible values      | FailFast,  FailAtEnd, FailNever  |
| Default  | FailFast  |
| Required | No          |
| Since    | 3.0.0     |

[Go to parameters list](#Parameters)

### timeoutInSeconds
The timeout in seconds to execute the project. A value of <string>`0`</strong> means no timeout.

|          |     |
| -------- | --- |
| Type     | int    |
| Default  | 0      |
| Required | No     |
| Since    | 3.0.1  |

[Go to parameters list](#Parameters)