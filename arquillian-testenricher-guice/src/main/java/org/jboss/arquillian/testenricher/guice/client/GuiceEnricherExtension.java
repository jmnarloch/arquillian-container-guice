package org.jboss.arquillian.testenricher.guice.client;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.TestEnricher;
import org.jboss.arquillian.testenricher.guice.GuiceInjectionEnricher;

/**
 *
 */
public class GuiceEnricherExtension implements LoadableExtension {

    public void register(ExtensionBuilder builder) {

        if(Validate.classExists("com.google.inject.Injector")) {
            builder.service(AuxiliaryArchiveAppender.class, GuiceEnricherArchiveAppender.class)
                    .service(ProtocolArchiveProcessor.class, GuiceProtocolArchiveProcessor.class);

            builder.service(TestEnricher.class, GuiceInjectionEnricher.class);
        }
    }
}
