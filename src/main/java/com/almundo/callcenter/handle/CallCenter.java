package com.almundo.callcenter.handle;

import java.util.stream.IntStream;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeeType;
import com.almundo.callcenter.utils.Constants;

/**
 * The Class CallCenter.
 * 
 */
public class CallCenter{
	
	/**
	 * Sets the employees list.
	 * 
	 * <p>The employees list filled with the number of employees defined in the Constants class.
	 *
	 * @param dispatcher the new employees queue
	 */
	private static void setEmployeesList(Dispatcher dispatcher){
		
		IntStream.range(0, Constants.MAX_NUMBER_OPERATORS).forEach(
				i -> dispatcher.addEmployeeToQueue(new Employee(EmployeeType.OPERATOR, i)));
		
		IntStream.range(0, Constants.MAX_NUMBER_SUPERVISORS).forEach(
				i -> dispatcher.addEmployeeToQueue(new Employee(EmployeeType.SUPERVISOR, i)));
		
		IntStream.range(0, Constants.MAX_NUMBER_DIRECTORS).forEach(
				i -> dispatcher.addEmployeeToQueue(new Employee(EmployeeType.DIRECTOR, i)));
	}
	
    /**
     * The main method.
     * 
     * <p> The dispatcher is instantiated and set the employees list.
     * 
     * <p> The loop starts at 0 and ends at the number of calls defined in the Constants class.
     *
     * @param args the arguments
     */
    public static void main( String[] args ){
    	
    	Dispatcher dispatcher = new Dispatcher();
    	setEmployeesList(dispatcher);
    	for(int i = 0 ; i < Constants.CALLS; i++){
    		dispatcher.dispatcherCall(new Call(Constants.CALL_NAME + i));
    	}
    	dispatcher.shutDownExecutorService();
    }
}
