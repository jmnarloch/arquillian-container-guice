package org.jboss.arquillian.guice.testsuite.repository;

import org.jboss.arquillian.guice.testsuite.Employee;

import java.util.List;

/**
 *
 */
public interface EmployeeRepository {

    /**
     * Retrieves all employees.
     *
     * @return list of employees
     */
    List<Employee> getEmployees();
}
