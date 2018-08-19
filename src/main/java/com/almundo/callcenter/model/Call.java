package com.almundo.callcenter.model;

import java.util.concurrent.ThreadLocalRandom;

import com.almundo.callcenter.utils.Constants;

/**
 * The Class Call.
 * This class allows instantiating the call object.
 */
public class Call {
	
	/** The call name. */
	private String name;
	
	/** The call duration. */
	private Integer duration;

	/**
	 * Instantiates a new call.
	 */
	public Call(String name) {
		super();
		this.name = name;
		this.duration = ThreadLocalRandom.current().nextInt(Constants.MIN_CALL_SECONDS, Constants.MAX_CALL_SECONDS + 1);
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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}