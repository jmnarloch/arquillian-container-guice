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

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.CachedAuxilliaryArchiveAppender;
import org.jboss.arquillian.guice.annotations.GuiceConfiguration;
import org.jboss.arquillian.guice.container.GuiceEnricherRemoteExtension;
import org.jboss.arquillian.guice.container.GuiceInjectionEnricher;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 * @version $Revision: $
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