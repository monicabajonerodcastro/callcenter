package com.almundo.callcenter.handle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeeType;

/**
 * The Class Dispatcher.
 * 
 * This class is responsible for handling calls.
 */

public class Dispatcher{
	
	/** The Constant MAX_NUMBER_OPERATORS. The number of available operators */
	private final static int MAX_NUMBER_OPERATORS = 4;
	
	/** The Constant MAX_NUMBER_SUPERVISORS. The number of available supervisors */
	private final static int MAX_NUMBER_SUPERVISORS = 2;
	
	/** The Constant MAX_NUMBER_DIRECTORS. The number of available directors */
	private final static int MAX_NUMBER_DIRECTORS = 1;
	
	/** The Constant NUMBER_THREADS_POOL. */
	public final static int NUMBER_THREADS_POOL = 10;
		
	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(Dispatcher.class);
	
    /** The executor service: Automates the execution of class */
    private ExecutorService executorService;
    
    /** The semaphore: Manages and assigns employee turns */
    private Semaphore semaphore;
    
    /** The calls queue: Stores calls in a queue*/
    private ConcurrentLinkedQueue<Call> callsQueue;
    
    /** The employees list: Stores employees with their type and availability */
    private List<Employee> employeesList;

	/**
	 * Instantiates a new dispatcher.
	 * 
	 * <p> The executor service is instantiated with a fixed size pool. 
	 * This size is defined in the Constants class.
	 * 
	 * <p> The semaphore is instantiated with the number of turns 
	 * correspondent to number of employees defined in the Constants class.
	 * 
	 * <p> The calls queue is instantiated with a empty queue.
	 * 
	 * <p> The employees list is instantiated with a empty list.
	 */
	public Dispatcher() {
		super();
		this.executorService = Executors.newFixedThreadPool(NUMBER_THREADS_POOL);
		this.semaphore = new Semaphore(getMaxNumberEmployees(), true);
		this.callsQueue = new ConcurrentLinkedQueue<>();
		this.employeesList = new ArrayList<>();
	}
	
	 /**
 	 * Gets the max number employees.
 	 *
 	 * @return the max number employees
 	 */
	public Integer getMaxNumberEmployees(){
    	return MAX_NUMBER_DIRECTORS + MAX_NUMBER_SUPERVISORS + MAX_NUMBER_OPERATORS;
    }
	
	/**
	 * Adds the employee to queue.
	 * 
	 * <p> An employee is received as a parameter and employee is added
	 * to the employees list 
	 *
	 * @param employee the employee
	 */
	public void addEmployeeToQueue(Employee employee){
		employeesList.add(employee);
	}
	
	/**
	 * Sets the employees list.
	 * 
	 * <p>The employees list filled with the number of employees defined in the Constants class.
	 *
	 * @param dispatcher the new employees queue
	 */
	
	public void setEmployeesList(){
		
		IntStream.range(0, MAX_NUMBER_OPERATORS).forEach(i -> addEmployeeToQueue(new Employee(EmployeeType.OPERATOR, i)));
		IntStream.range(0, MAX_NUMBER_SUPERVISORS).forEach(i -> addEmployeeToQueue(new Employee(EmployeeType.SUPERVISOR, i)));
		IntStream.range(0, MAX_NUMBER_DIRECTORS).forEach(i -> addEmployeeToQueue(new Employee(EmployeeType.DIRECTOR, i)));
	}
	
	/**
	 * Find available employee by type.
	 * 
	 * <p> Find an employee within the employees list depending on the employee
	 * type and availability  
	 *
	 * @param employeeType the employee type
	 * @return the optional employee
	 */
	private Optional<Employee> findAvailableEmployeeByType(EmployeeType employeeType){
		return employeesList.stream().filter(e -> e.isAvailable() && employeeType.equals(e.getEmployeeType())).findFirst();
	}
	
	/**
	 * Change employee availability.
	 * 
	 * <p> Change the employee's availability status. If the availability is true,
	 * change to false and vice versa
	 *
	 * @param employee the employee
	 */
	private void changeEmployeeAvailability(Employee employee){
		employeesList.forEach(e -> {
			if(e == employee){
				e.setAvailable(!e.isAvailable());
			}
		});
	}
	
	/**
	 * Gets the available employee.
	 * 
	 * <p> Search in the employees list an available employee. First search operator employees,
	 * if there isn't availability employees, search supervisor employees, if there isn't 
	 * availability employees, search director employees.
	 * 
	 * <p> In case of any employee is availability, the loop starts again.
	 *
	 * <p> When a employee is found, change the availability.
	 * 
	 * <p> This method is declared as synchronized for solve the race condition.
	 *
	 * @return the available employee
	 */
	public synchronized Employee getAvailableEmployee(Call call) {
		Optional<Employee> employee;
		do{
			employee = findAvailableEmployeeByType(EmployeeType.OPERATOR);
			if(!employee.isPresent()){
				employee = findAvailableEmployeeByType(EmployeeType.SUPERVISOR);
				if(!employee.isPresent()){
					employee = findAvailableEmployeeByType(EmployeeType.DIRECTOR);
				}
			}
		}while(!employee.isPresent());
		
		changeEmployeeAvailability(employee.get());
		
		logger.info("The employee "+employee.get().getName()+" will attend the "+call.getName());
		return employee.get();
	}
	
	/**
	 * Dispatcher call.
	 * 
	 * <p> The calls queue is filled with incoming calls. The submit of executor service 
	 * asks for a turn of the semaphore. An available employee is sought. A thread is 
	 * active while the duration of the call. When the call is over, the availability of 
	 * the employee changes.
	 */
	public void dispatcherCall(Call call){
		
		logger.info("Incoming " + call.getName());
		callsQueue.add(call);
		
		executorService.submit(() -> {
			try {
				semaphore.acquire();
				Employee activeEmployee = getAvailableEmployee(call);
				
				Thread.sleep(call.getDuration() * 1000);
				
				changeEmployeeAvailability(activeEmployee);
				logger.info("The "+call.getName()+" ended in "+call.getDuration()+" seconds");
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Shut down executor service.
	 * 
	 * <p> When all the calls were answered, the executor service stops.
	 * 
	 */
	public void shutDownExecutorService(){
		executorService.shutdown();
	}
	
	/**
	 * Gets the employees list.
	 *
	 * @return the employees list
	 */
	public List<Employee> getEmployeesList(){
		return employeesList;
	}
		
	
	/**
	 * Wait for executor service finish.
	 * 
	 * <p> This method is used to wait until all task have completed
	 * execution in test class or the timeout occurs. 
	 */
	public void waitForExecutorServiceFinish() {
        try {
        	executorService.awaitTermination(300, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
