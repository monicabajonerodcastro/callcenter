package com.almundo.callcenter.model;

/**
 * The Enum EmployeeType.
 * This Enum represent the employee type
 */
public enum EmployeeType {
	
	/** The operator. */
	OPERATOR("Operator"),
	
	/** The supervisor. */
	SUPERVISOR("Supervisor"),
	
	/** The director. */
	DIRECTOR("Director");
	
	/** The type name. */
	private String typeName;
	
	/**
	 * Instantiates a new employee type.
	 *
	 * @param typeName the type name
	 */
	EmployeeType(String typeName){
		
		this.typeName = typeName;
		
	}
	
	/**
	 * Type name.
	 *
	 * @return the string
	 */
	public String typeName(){
		return typeName;
	}

}
