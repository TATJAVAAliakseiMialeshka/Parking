package com.epam.ta.parking.dao.exception;

public class DaoConnectionException extends DaoTechnicalException {

	/**
	 * error class inherits <code>DaoTechnicalException<code>
	 * Class to create specific <code>DaoConnectionException</code> exception
	 * objects by wrapping application <code>SQLException</code> objects on dao
	 * application level
	 * 
	 */
	private static final long serialVersionUID = 2427795788555344310L;

	/**
	 * @param message exception message
	 */
	public DaoConnectionException(String message) {
		super(message);
	}

	/**
	 * @param message exception message
	 * @param innerEx Throwable inner 
	 * exception instance, which is 
	 * the cause of this exception
	 */
	public DaoConnectionException(String message, Exception innerEx) {
		super(message);
		/* Initializes the cause of this exception to the specified value */
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public DaoConnectionException(Exception e) {
		super(e);
	}
}
