package org.jboss.arquillian.testenricher.guice.container;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.guice.annotations.GuiceConfiguration;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 */
public class InjectorProducer {

    /**
     * The logger used by this class.
     */
    private static final Logger log = Logger.getLogger(InjectorProducer.class.getName());

    /**
     * Producer proxy for {@link Injector}.
     */
    @Inject
    @ApplicationScoped
    private InstanceProducer<Injector> injectorProducer;

    public void initInjector(@Observes BeforeClass beforeClass) {

        Injector injector = createInjector(beforeClass.getTestClass());

        if(injector != null) {
            injectorProducer.set(injector);
        }
    }

    private Injector createInjector(TestClass testClass) {

        if(testClass.isAnnotationPresent(GuiceConfiguration.class)) {
            GuiceConfiguration guiceConfiguration = testClass.getAnnotation(GuiceConfiguration.class);

            return Guice.createInjector(instantiateModules(guiceConfiguration.value()));
        } 
        
        return null;
    }

    private Module[] instantiateModules(Class<? extends Module>[] classes) {
        List<Module> modules = new ArrayList<Module>();

        for(Class<? extends Module> c : classes) {

            modules.add(instantiateClass(c));
        }

        return modules.toArray(new Module[modules.size()]);
    }

    private Module instantiateClass(Class<? extends Module> clazz) {

        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not instantiate Guice module.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not instantiate Guice module.", e);
        }
    }
}
