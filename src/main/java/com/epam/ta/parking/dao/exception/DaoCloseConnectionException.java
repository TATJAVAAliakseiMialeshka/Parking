package com.epam.ta.parking.dao.exception;

/**
 * error class inherits <code>DaoTechnicalException<code>
 * Class to create specific <code>DaoCloseConnectionException</code> exception
 * objects by wrapping application <code>SQLException</code> objects on dao
 * application level
 * 
 */
public class DaoCloseConnectionException extends DaoTechnicalException {

	private static final long serialVersionUID = -8975008130143462980L;

	/**
	 * @param message exception message
	 */
	public DaoCloseConnectionException(String message) {
		super(message);
	}

	/**
	 * @param message exception message
	 * @param innerEx Throwable inner 
	 * exception instance, which is 
	 * the cause of this exception
	 */
	public DaoCloseConnectionException(String message, Exception innerEx) {
		super(message);
		 /* Initializes the cause of this exception to the specified value */
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public DaoCloseConnectionException(Exception e) {
		super(e);
	}
	
}
