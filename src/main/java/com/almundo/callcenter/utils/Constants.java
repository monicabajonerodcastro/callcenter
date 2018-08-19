package com.almundo.callcenter.utils;

/**
 * The Class Constants.
 */
public class Constants {
	
	/** The Constant MAX_NUMBER_OPERATORS. */
	public final static int MAX_NUMBER_OPERATORS = 5;
	
	/** The Constant MAX_NUMBER_SUPERVISORS. */
	public final static int MAX_NUMBER_SUPERVISORS = 3;
	
	/** The Constant MAX_NUMBER_DIRECTORS. */
	public final static int MAX_NUMBER_DIRECTORS = 1;
	
	/** The Constant NUMBER_THREADS_POOL. */
	public final static int NUMBER_THREADS_POOL = 10;
	
	public final static int CALLS = 10;
	
	/** The Constant MIN_CALL_SECONDS. */
	public static final int MIN_CALL_SECONDS = 5;
	
	/** The Constant MAX_CALL_SECONDS. */
	public static final int MAX_CALL_SECONDS = 10;
	
	/** The Constant MILISECONDS. */
	public static final int MILISECONDS = 1000;
	
	/** The Constant SCRIPT_SEPARATOR. */
	public static final String SCRIPT_SEPARATOR = "_";
	
	/** The Constant OPERATOR_NAME. */
	//public final static String OPERATOR_NAME = "Operator_";
	
	/** The Constant SUPERVISOR_NAME. */
	//public final static String SUPERVISOR_NAME = "Supervisor_";
	
	/** The Constant DIRECTOR_NAME. */
	//public final static String DIRECTOR_NAME = "Director_";
	
	/** The Constant CALL_NAME. */
	public final static String CALL_NAME = "Call_";

	
	
	 /**
 	 * Gets the max number employees.
 	 *
 	 * @return the max number employees
 	 */
 	public static Integer getMaxNumberEmployees(){
    	return MAX_NUMBER_DIRECTORS + MAX_NUMBER_SUPERVISORS + MAX_NUMBER_OPERATORS;
    }
	
}
