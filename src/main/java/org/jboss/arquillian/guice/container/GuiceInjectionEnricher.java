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
package org.jboss.arquillian.guice.container;

import com.google.inject.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.guice.GuiceExtensionConsts;
import org.jboss.arquillian.guice.annotations.GuiceConfiguration;
import org.jboss.arquillian.test.spi.TestEnricher;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * A Google Guice test enricher, injects guice dependencies into the test class.
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 * @version $Revision: $
 */
public class GuiceInjectionEnricher implements TestEnricher {

    /**
     * Logger used by this class.
     */
    private static final Logger log = Logger.getLogger(GuiceInjectionEnricher.class.getName());

    /**
     * Instance of Guice {@link Injector}.
     */
    @Inject
    private Instance<Injector> injector;

    /**
     * {@inheritDoc}
     */
    public void enrich(Object testCase) {

        if(SecurityActions.isClassPresent(GuiceExtensionConsts.INJECTOR)
                && isGuiceTest(testCase)) {
            injectClass(testCase);
        }
    }

    /**
     * Returns whether the test case is annotated with {@link GuiceConfiguration} and requires guice injection.
     *
     * @param testCase the test case
     * @return true if the test is annotated with {@link GuiceConfiguration}, false otherwise
     */
    private boolean isGuiceTest(Object testCase) {
        return testCase.getClass().isAnnotationPresent(GuiceConfiguration.class);
    }

    /**
     * {@inheritDoc}
     */
    public Object[] resolve(Method method) {

        return new Object[method.getParameterTypes().length];
    }

    /**
     * Injects dependencies into test class.
     *
     * @param testCase the test class
     */
    private void injectClass(Object testCase) {

        // retrieves the injector
        Injector injector = getInjector();

        if (injector != null) {

            // injects the dependencies into test test class
            injector.injectMembers(testCase);
            log.fine("Injecting dependencies into guice test " + testCase.getClass().getSimpleName());
        }
    }

    /**
     * Retrieves the {@link Injector} instance
     *
     * @return hte {@link Injector} instance
     */
    private Injector getInjector() {

        return injector.get();
    }
}
