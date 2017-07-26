package com.epam.ta.parking.service.exception;

public class ServiceLogicalException extends ServiceException {

	private static final long serialVersionUID = -6451701520877201819L;
	/**
	 * @param message
	 *            exception message
	 */
	public ServiceLogicalException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            exception message
	 * @param innerEx
	 *            Throwable inner exception instance, which is the cause of this
	 *            exception
	 */
	public ServiceLogicalException(String message, Throwable innerEx) {
		super(message, innerEx);
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public ServiceLogicalException(Exception e) {
		super(e);
	}
}
