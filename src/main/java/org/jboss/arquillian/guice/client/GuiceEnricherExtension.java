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

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.guice.GuiceExtensionConsts;
import org.jboss.arquillian.guice.container.GuiceInjectionEnricher;
import org.jboss.arquillian.test.spi.TestEnricher;

/**
 * Initializes the Guice extension.
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 * @version $Revision: $
 */
public class GuiceEnricherExtension implements LoadableExtension {

    /**
     * {@inheritDoc}
     */
    public void register(ExtensionBuilder builder) {

        if (Validate.classExists(GuiceExtensionConsts.INJECTOR)) {
            builder.service(AuxiliaryArchiveAppender.class, GuiceEnricherArchiveAppender.class)
                    .service(ProtocolArchiveProcessor.class, GuiceProtocolArchiveProcessor.class);

            builder.service(TestEnricher.class, GuiceInjectionEnricher.class);
        }
    }
}
