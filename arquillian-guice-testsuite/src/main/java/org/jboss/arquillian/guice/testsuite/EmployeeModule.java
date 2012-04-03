package org.jboss.arquillian.guice.testsuite;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.jboss.arquillian.guice.testsuite.repository.EmployeeRepository;
import org.jboss.arquillian.guice.testsuite.repository.impl.DefaultEmployeeRepository;
import org.jboss.arquillian.guice.testsuite.service.EmployeeService;
import org.jboss.arquillian.guice.testsuite.service.impl.DefaultEmployeeService;

/**
 *
 */
public class EmployeeModule implements Module {

    /**
     * {@inheritDoc}
     */
    public void configure(Binder binder) {
        binder.bind(EmployeeRepository.class).to(DefaultEmployeeRepository.class);
        binder.bind(EmployeeService.class).to(DefaultEmployeeService.class);
    }
}
