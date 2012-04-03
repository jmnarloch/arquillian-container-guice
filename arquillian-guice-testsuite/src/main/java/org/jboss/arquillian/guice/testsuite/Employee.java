package org.jboss.arquillian.guice.testsuite;

/**
 *
 */
public class Employee {

    /**
     * Represents the employee name.
     */
    private String name;

    /**
     * Creates new instance of {@link Employee} class.
     */
    public Employee() {
        // empty constructor
    }

    /**
     * Retrieves the employee name.
     *
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee name.
     *
     * @param name the employee name
     */
    public void setName(String name) {
        this.name = name;
    }
}
