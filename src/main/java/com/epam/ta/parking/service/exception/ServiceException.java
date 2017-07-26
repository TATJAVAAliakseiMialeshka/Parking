package com.epam.ta.parking.service.exception;

import com.epam.ta.parking.exception.AbstractParkingException;

/**
 * error class inherits <code>AbstractParkingException<code>
 * Class to create specific <code>ServiceException</code> exception
 * objects
 * 
 */
public class ServiceException extends AbstractParkingException {

	private static final long serialVersionUID = -6451701520877201819L;
	/**
	 * @param message
	 *            exception message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            exception message
	 * @param innerEx
	 *            Throwable inner exception instance, which is the cause of this
	 *            exception
	 */
	public ServiceException(String message, Throwable innerEx) {
		super(message, innerEx);
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public ServiceException(Exception e) {
		super(e);
	}
	
}
