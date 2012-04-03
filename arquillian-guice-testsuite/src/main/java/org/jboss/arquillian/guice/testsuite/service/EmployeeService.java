package org.jboss.arquillian.guice.testsuite.service;

import org.jboss.arquillian.guice.testsuite.Employee;

import java.util.List;

/**
 *
 */
public interface EmployeeService {

    /**
     * Retrieves all employees.
     *
     * @return list of employees
     */
    List<Employee> getEmployees();
}
