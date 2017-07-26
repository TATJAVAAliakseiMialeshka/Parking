package com.epam.ta.parking.exception;

/**
 * Abstract exception class
 * It will inherit by all 
 * application exception classes
 */
public abstract class AbstractParkingException extends Exception{

	private static final long serialVersionUID = -6451701520877201819L;
	/**
	 * @param message
	 *            exception message
	 */
	public AbstractParkingException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            exception message
	 * @param innerEx
	 *            Throwable inner exception instance, which is the cause of this
	 *            exception
	 */
	public AbstractParkingException(String message, Throwable innerEx) {
		super(message, innerEx);
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public AbstractParkingException(Exception e) {
		super(e);
	}
}
