package org.jboss.arquillian.testenricher.guice.client;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.CachedAuxilliaryArchiveAppender;
import org.jboss.arquillian.guice.annotations.GuiceConfiguration;
import org.jboss.arquillian.testenricher.guice.GuiceInjectionEnricher;
import org.jboss.arquillian.testenricher.guice.container.GuiceEnricherRemoteExtension;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 *
 */
public class GuiceEnricherArchiveAppender extends CachedAuxilliaryArchiveAppender {
    @Override
    protected Archive<?> buildArchive() {
        return ShrinkWrap.create(JavaArchive.class, "arquillian-testenricher-guice.jar")
                .addPackage(GuiceInjectionEnricher.class.getPackage())
                .addPackage(GuiceEnricherRemoteExtension.class.getPackage())
                .addPackage(GuiceConfiguration.class.getPackage())
                .addAsServiceProvider(RemoteLoadableExtension.class, GuiceEnricherRemoteExtension.class);
    }
}
