package org.jboss.arquillian.guice.testsuite.repository.impl;

import org.jboss.arquillian.guice.testsuite.Employee;
import org.jboss.arquillian.guice.testsuite.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DefaultEmployeeRepository implements EmployeeRepository {

    /**
     * {@inheritDoc}
     */
    public List<Employee> getEmployees() {

        Employee employee;
        List<Employee> employees = new ArrayList<Employee>();

        employee = new Employee();
        employee.setName("John Smith");
        employees.add(employee);

        employee = new Employee();
        employee.setName("Marty Smith");
        employees.add(employee);

        return employees;
    }
}
