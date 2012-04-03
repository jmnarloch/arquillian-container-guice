package org.jboss.arquillian.testenricher.guice.client;

import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import java.io.File;

/**
 *
 */
public class GuiceProtocolArchiveProcessor implements ProtocolArchiveProcessor {

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

    private void addGuiceLibraries(Archive<?> archive) {

        File[] springLibraries = resolveGuiceDependencies();

        if (archive instanceof EnterpriseArchive) {
            ((EnterpriseArchive) archive).addAsModules(springLibraries);
        } else if (archive instanceof WebArchive) {
            ((WebArchive) archive).addAsLibraries(springLibraries);
        } else {
            throw new RuntimeException("Unsupported archive format[" + archive.getClass().getSimpleName()
                    + ", " + archive.getName() + "] for Spring application. Please use WAR or EAR.");
        }
    }

    private File[] resolveGuiceDependencies() {

        return resolveArtifact("com.google.inject:guice", "3.0");
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
            mvnResolver.loadMetadataFromPom("pom.xml");
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
        return new File("pom.xml").exists();
    }
}
