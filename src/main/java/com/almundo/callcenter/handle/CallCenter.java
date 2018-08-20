package com.almundo.callcenter.handle;

import org.apache.log4j.BasicConfigurator;

import com.almundo.callcenter.model.Call;

/**
 * The Class CallCenter.
 * 
 */
public class CallCenter{
	
	/** The Constant CALLS. */
	public final static int CALLS = 10;
	
	/** The Constant CALL_NAME. */
	public final static String CALL_NAME = "Call_";
	
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
    	
    	BasicConfigurator.configure();
    	
    	Dispatcher dispatcher = new Dispatcher();
    	dispatcher.setEmployeesList();
    	for(int i = 0 ; i < CALLS; i++){
    		dispatcher.dispatcherCall(new Call(CALL_NAME + i));
    	}
    	dispatcher.shutDownExecutorService();
    }
}
