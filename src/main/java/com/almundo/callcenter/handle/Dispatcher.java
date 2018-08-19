package com.almundo.callcenter.handle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeeType;
import com.almundo.callcenter.utils.Constants;

/**
 * The Class Dispatcher.
 * 
 * This class is responsible for handling calls.
 */

public class Dispatcher{
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	
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
		this.executorService = Executors.newFixedThreadPool(Constants.NUMBER_THREADS_POOL);
		this.semaphore = new Semaphore(Constants.getMaxNumberEmployees(), true);
		this.callsQueue = new ConcurrentLinkedQueue<>();
		this.employeesList = new ArrayList<>();
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
	private synchronized Employee getAvailableEmployee() {
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
		
		return employee.get();
	}
	
	/**
	 * Dispatcher call.
	 */
	public void dispatcherCall(Call call){
		
		logger.info("Incoming " + call.getName());
		callsQueue.add(call);
		
		executorService.submit(() -> {
			try {
				semaphore.acquire();
				Employee activeEmployee = getAvailableEmployee();
				
				logger.info("The employee "+activeEmployee.getName()+" will attend the "+call.getName());
				Thread.sleep(call.getDuration() * Constants.MILISECONDS);
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
	 */
	public void shutDownExecutorService(){
		executorService.shutdown();
	}

}
