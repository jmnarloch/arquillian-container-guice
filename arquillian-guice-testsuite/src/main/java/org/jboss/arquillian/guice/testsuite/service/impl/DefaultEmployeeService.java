package org.jboss.arquillian.guice.testsuite.service.impl;

import com.google.inject.Inject;
import org.jboss.arquillian.guice.testsuite.Employee;
import org.jboss.arquillian.guice.testsuite.repository.EmployeeRepository;
import org.jboss.arquillian.guice.testsuite.service.EmployeeService;

import java.util.List;

/**
 *
 */
public class DefaultEmployeeService implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    /**
     * {@inheritDoc}
     */
    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }
}
