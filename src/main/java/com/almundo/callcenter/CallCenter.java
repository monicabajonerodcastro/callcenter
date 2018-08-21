package com.almundo.callcenter;

import java.util.stream.IntStream;

import org.apache.log4j.BasicConfigurator;

import com.almundo.callcenter.dispatcher.Dispatcher;
import com.almundo.callcenter.model.Call;

/**
 * The Class CallCenter.
 * 
 * @author Monica Bajonero
 * 
 */
public class CallCenter {

	/** The Constant CALLS. */
	public final static int CALLS = 10;

	/** The Constant CALL_NAME_PREFIX. */
	public final static String CALL_NAME_PREFIX = "Call_";

	/**
	 * The main method.
	 * 
	 * <p>
	 * The dispatcher is instantiated and set the employees list.
	 * 
	 * <p>
	 * The loop starts at 0 and ends at the number of calls defined in the
	 * Constants class.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void fillEmployeesList(String[] args) {

		BasicConfigurator.configure();

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.fillEmployeesList();

		IntStream.range(0, CALLS).forEach(i -> dispatcher.dispatchCall(new Call(CALL_NAME_PREFIX + i)));
		dispatcher.shutDownExecutorService();
	}
}
