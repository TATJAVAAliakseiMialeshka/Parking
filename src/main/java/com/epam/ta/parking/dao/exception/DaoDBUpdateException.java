package com.epam.ta.parking.dao.exception;

/**
 * error class inherits <code>DaoLogicalException<code>
 * Class to create specific <code>DaoDBUpdateEception</code> exception
 * objects in case logically wrong user query to db
 * by wrapping application <code>SQLException</code>
 * objects on dao application level
 * 
 */
public class DaoDBUpdateException extends DaoTechnicalException {

	private static final long serialVersionUID = -3011065191259400042L;

	/**
	 * @param message exception message
	 */
	public DaoDBUpdateException(String message) {
		super(message);
	}

	/**
	 * @param message exception message
	 * @param innerEx Throwable inner 
	 * exception instance, which is 
	 * the cause of this exception
	 */
	public DaoDBUpdateException(String message, Exception innerEx) {
		super(message);
		/* Initializes the cause of this exception to the specified value */
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public DaoDBUpdateException(Exception e) {
		super(e);
	}
}
