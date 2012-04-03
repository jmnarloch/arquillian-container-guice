package org.jboss.arquillian.testenricher.guice;

import com.google.inject.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.spi.TestEnricher;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 *
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

        injectClass(testCase);
    }

    /**
     * {@inheritDoc}
     */
    public Object[] resolve(Method method) {

        return new Object[method.getParameterTypes().length];
    }

    private void injectClass(Object testCase) {
        
        Injector injector = getInjector();

        if(injector != null) {

            injector.injectMembers(testCase);
        }
    }

    private Injector getInjector() {

        return injector.get();
    }
}
