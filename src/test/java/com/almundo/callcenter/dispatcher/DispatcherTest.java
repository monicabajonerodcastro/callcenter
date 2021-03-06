package com.almundo.callcenter.dispatcher;

import java.util.Optional;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	 * Dispatcher call test with more calls than employees.
	 * 
	 * <p> There should not be any available employee because they are all busy attending calls
	 */
	@Test
	public void dispatcherCallTestWithMoreCallsThanEmployees() {

		logger.info("***** Testing dispatcherCallTestWithMoreCallsThanEmployees *****");

		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.OPERATOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.SUPERVISOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.DIRECTOR, 0));

		IntStream.range(0, 10).forEach(i -> dispatcher.dispatchCall(new Call("Call_" + i)));
		
		Optional<Employee> nextAvailableEmployee = dispatcher.getEmployeesList().stream().filter(e -> e.isAvailable()).findFirst();
		assertFalse(nextAvailableEmployee.isPresent());
		
		dispatcher.shutDownExecutorService();
		dispatcher.waitForExecutorServiceFinish();
	}

	/**
	 * Dispatcher call test with more employees than calls.
	 * 
	 * There should find an operators-type employee.
	 */
	@Test
	public void dispatcherCallTestWithMoreEmployeesThanCalls() {

		logger.info("***** Testing dispatcherCallTestWithMoreEmployesThanCalls *****");

		IntStream.range(0, 8).forEach(i -> dispatcher.addEmployeeToQueue(new Employee(EmployeeType.OPERATOR, i)));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.SUPERVISOR, 0));
		dispatcher.addEmployeeToQueue(new Employee(EmployeeType.DIRECTOR, 0));
		
		IntStream.range(0, 7).forEach(i -> dispatcher.dispatchCall(new Call("Call_" + i)));
		
		Optional<Employee> nextAvailableEmployee = dispatcher.getEmployeesList().stream().filter(e -> e.isAvailable()).findFirst();
		assertTrue(nextAvailableEmployee.isPresent());
		assertEquals(EmployeeType.OPERATOR, nextAvailableEmployee.get().getEmployeeType());
		
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
