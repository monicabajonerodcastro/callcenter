package com.almundo.callcenter.dispatcher;

import java.util.stream.IntStream;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.almundo.callcenter.dispatcher.Dispatcher;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeeType;

import static org.junit.Assert.assertEquals;

/**
 * The Class DispatcherTest.
 * 
 * @author Monica Bajonero
 */
public class DispatcherTest {

	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(DispatcherTest.class);

	/** The dispatcher. */
	Dispatcher dispatcher;

	/**
	 * Sets the initial configuration for class.
	 */
	@BeforeClass
	public static void setUpClass() {
		BasicConfigurator.configure();
	}
	
	/**
	 * Sets the initial configuration for Test.
	 */
	@Before
	public void setUp() {
		dispatcher = new Dispatcher();
	}

	/**
	 * Dispatcher call with defined configuration in class.
	 */
	@Test
	public void dispatcherCallWithConfigurationClass() {

		logger.info("***** Testing dispatcherCallWithConfigurationClass *****");

		dispatcher.fillEmployeesList();

		IntStream.range(0, 10).forEach(i -> dispatcher.dispatchCall(new Call("Call_" + i)));
		dispatcher.shutDownExecutorService();
		dispatcher.waitForExecutorServiceFinish();
	}

	/**
	 * Dispatcher call test with more calls than employees.
	 */
	@Test
	public void dispatcherCallTestWithMoreCallsThanEmployees() {

		logger.info("***** Testing dispatcherCallTestWithMoreCallsThanEmployees *****");

		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.OPERATOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.SUPERVISOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.DIRECTOR, 0));

		IntStream.range(0, 10).forEach(i -> dispatcher.dispatchCall(new Call("Call_" + i)));
		dispatcher.shutDownExecutorService();
		dispatcher.waitForExecutorServiceFinish();
	}

	/**
	 * Dispatcher call test with more employees than calls.
	 */
	@Test
	public void dispatcherCallTestWithMoreEmployeesThanCalls() {

		logger.info("***** Testing dispatcherCallTestWithMoreEmployesThanCalls *****");

		IntStream.range(0, 8).forEach(i -> dispatcher.addEmployeeToQueue(new Employee(EmployeeType.OPERATOR, i)));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.SUPERVISOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.DIRECTOR, 0));

		IntStream.range(0, 7).forEach(i -> dispatcher.dispatchCall(new Call("Call_" + i)));
		dispatcher.shutDownExecutorService();
		dispatcher.waitForExecutorServiceFinish();
	}

	/**
	 * Find employee by priority and availability.
	 */
	@Test
	public void findEmployeeByPriorityAndAvailability() {

		logger.info("***** Testing findEmployeeByPriorityAndAvailability *****");

		Call call = new Call("Test Call");

		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.OPERATOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.SUPERVISOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.DIRECTOR, 0));

		Employee operatorEmployee = dispatcher.getAvailableEmployee(call);
		assertEquals(operatorEmployee.getEmployeeType(), EmployeeType.OPERATOR);

		Employee supervisorEmployee = dispatcher.getAvailableEmployee(call);
		assertEquals(supervisorEmployee.getEmployeeType(), EmployeeType.SUPERVISOR);

		Employee directorEmployee = dispatcher.getAvailableEmployee(call);
		assertEquals(directorEmployee.getEmployeeType(), EmployeeType.DIRECTOR);
	}

}
