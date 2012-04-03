package org.jboss.arquillian.guice.testsuite.service.impl;

import com.google.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.guice.annotations.GuiceConfiguration;
import org.jboss.arquillian.guice.testsuite.Employee;
import org.jboss.arquillian.guice.testsuite.EmployeeModule;
import org.jboss.arquillian.guice.testsuite.repository.EmployeeRepository;
import org.jboss.arquillian.guice.testsuite.repository.impl.DefaultEmployeeRepository;
import org.jboss.arquillian.guice.testsuite.service.EmployeeService;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
@RunWith(Arquillian.class)
@GuiceConfiguration({EmployeeModule.class})
public class DefaultEmployeeServiceTestSuite {

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "guice-test.jar")
                .addClasses(Employee.class,
                        EmployeeService.class, DefaultEmployeeService.class,
                        EmployeeRepository.class, DefaultEmployeeRepository.class,
                        EmployeeModule.class);
    }

    @Inject
    private EmployeeService employeeService;

    @Test
    public void testGetEmployees() throws Exception {

        List<Employee> result = employeeService.getEmployees();

        assertNotNull("Method returned null list as result.", result);
        assertEquals("Two employees were expected.", 2, result.size());
    }
}
