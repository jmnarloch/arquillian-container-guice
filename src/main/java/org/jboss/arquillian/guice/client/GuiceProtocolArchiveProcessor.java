/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.guice.client;

import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.guice.GuiceExtensionConsts;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import java.io.File;

/**
 * Guice {@link ProtocolArchiveProcessor} that adds all the required dependencies into test deployment.
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 * @version $Revision: $
 */
public class GuiceProtocolArchiveProcessor implements ProtocolArchiveProcessor {

    /**
     * {@inheritDoc}
     */
    public void process(TestDeployment testDeployment, Archive<?> protocolArchive) {
        if (isEnterpriseArchive(testDeployment.getApplicationArchive()) ||
                isWebArchive(testDeployment.getApplicationArchive())) {
            addGuiceLibraries(testDeployment.getApplicationArchive());
        } else if (isEnterpriseArchive(protocolArchive) || isWebArchive(protocolArchive)) {
            // otherwise try to add the required dependencies into the protocol archive
            addGuiceLibraries(protocolArchive);
        }
    }

    /**
     * Returns whether the passed archive is an enterprise archive (EAR)
     *
     * @param archive the archive
     *
     * @return true if passed archive is an enterprise archive, false otherwise
     */
    private boolean isEnterpriseArchive(Archive<?> archive) {
        return archive instanceof EnterpriseArchive;
    }

    /**
     * Returns whether the passed archive is an web archive (WAR)
     *
     * @param archive the archive
     *
     * @return true if passed archive is an web archive, false otherwise
     */
    private boolean isWebArchive(Archive<?> archive) {
        return archive instanceof WebArchive;
    }

    /**
     * Adds the required by Guice libraries.
     *
     * @param archive the archive to which the libraries will be added
     */
    private void addGuiceLibraries(Archive<?> archive) {

        File[] guiceLibraries = resolveGuiceDependencies();

        if (archive instanceof EnterpriseArchive) {
            ((EnterpriseArchive) archive).addAsModules(guiceLibraries);
        } else if (archive instanceof WebArchive) {
            ((WebArchive) archive).addAsLibraries(guiceLibraries);
        }
    }

    /**
     * Resolves Guice dependencies using maven.
     * 
     * @return the resolved dependencies
     */
    private File[] resolveGuiceDependencies() {

        return resolveArtifact(GuiceExtensionConsts.GUICE_ARTIFACT_NAME, GuiceExtensionConsts.GUICE_ARTIFACT_VERSION);
    }

    /**
     * Resolves the given artifact in specified version with help of maven build system.
     *
     * @param artifact the artifact name
     * @param version  the artifact version
     *
     * @return the resolved files
     */
    private File[] resolveArtifact(String artifact, String version) {
        File[] artifacts = null;
        try {
            artifacts = resolveArtifact(artifact);
        } catch (Exception e) {
            artifacts = resolveArtifact(artifact + ":" + version);
        }
        return artifacts;
    }

    /**
     * Resolves the given artifact by it's name with help of maven build system.
     *
     * @param artifact the fully qualified artifact name
     *
     * @return the resolved files
     */
    private File[] resolveArtifact(String artifact) {
        MavenDependencyResolver mvnResolver = DependencyResolvers.use(MavenDependencyResolver.class);

        if (isMavenUsed()) {
            mvnResolver.loadMetadataFromPom(GuiceExtensionConsts.POM_XML);
        }

        return mvnResolver.artifacts(artifact)
                .resolveAsFiles();
    }

    /**
     * Returns whether maven is being used in project.
     *
     * @return true if maven is being used in project, false otherwise
     */
    private boolean isMavenUsed() {
        return new File(GuiceExtensionConsts.POM_XML).exists();
    }
}
