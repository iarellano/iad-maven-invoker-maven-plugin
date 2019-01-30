package com.github.iarellano.maven_invoker;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;

import org.apache.maven.plugins.annotations.Parameter;
import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;

public class MavenInvokerMojoTest
{
    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable 
        {
        }

        @Override
        protected void after()
        {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testSomething()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        MavenInvokerMojo mavenInvokerMojo = (MavenInvokerMojo) rule.lookupConfiguredMojo( pom, "touch" );
        assertNotNull(mavenInvokerMojo);
        String mavenHome = System.getenv("MAVEN_HOME");
        rule.setVariableValueToObject(mavenInvokerMojo, "globalSettingsFile", new File(mavenHome + "/conf/settings.xml"));
        rule.setVariableValueToObject(mavenInvokerMojo, "mavenHome", new File(mavenHome));
        rule.setVariableValueToObject(mavenInvokerMojo, "userSettingsFile", new File(System.getProperty("user.home") + "/.m2/settings.xml"));
        rule.setVariableValueToObject(mavenInvokerMojo, "javaHome", new File(System.getProperty("java.home")));
        rule.setVariableValueToObject(mavenInvokerMojo, "localRepository", null);
        mavenInvokerMojo.execute();
    }
}

