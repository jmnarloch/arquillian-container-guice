package org.jboss.arquillian.testenricher.guice.container;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.TestEnricher;
import org.jboss.arquillian.testenricher.guice.GuiceInjectionEnricher;

/**
 *
 */
public class GuiceEnricherRemoteExtension implements RemoteLoadableExtension {

    public void register(ExtensionBuilder builder) {

        if(Validate.classExists("com.google.inject.Injector")) {

            builder.service(TestEnricher.class, GuiceInjectionEnricher.class)
                    .observer(InjectorProducer.class);
        }
    }
}
