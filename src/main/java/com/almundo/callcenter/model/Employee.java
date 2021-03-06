package com.almundo.callcenter.model;

/**
 * The Class Employee. This class represents an employee that will attend the
 * call.
 * 
 * @author Monica Bajonero
 */
public class Employee {

	/** The Constant SCRIPT_SEPARATOR. */
	public static final String SCRIPT_SEPARATOR = "_";

	/** The employee type. */
	private EmployeeType employeeType;

	/** The name. */
	private String name;

	/** The is available. */
	private boolean isAvailable;

	/**
	 * Instantiates a new employee. The employee is availability by default
	 *
	 * @param employeType
	 *            the employee type
	 * @param name
	 *            the employee name
	 */
	public Employee(EmployeeType employeType, int count) {
		super();
		this.employeeType = employeType;
		this.name = employeType.name() + SCRIPT_SEPARATOR + count;
		this.isAvailable = true;
	}

	/**
	 * Gets the employee type.
	 *
	 * @return the employee type
	 */
	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	/**
	 * Sets the employee type.
	 *
	 * @param employeType
	 *            the new employee type
	 */
	public void setEmployeeType(EmployeeType employeType) {
		this.employeeType = employeType;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is available.
	 *
	 * @return true, if is available
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * Sets the available.
	 *
	 * @param isAvailable
	 *            the new available
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
