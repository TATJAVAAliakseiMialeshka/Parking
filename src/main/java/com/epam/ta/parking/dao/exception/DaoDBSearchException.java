package com.epam.ta.parking.dao.exception;

/**
 * error class inherits <code>DaoLogicalException<code>
 * Class to create specific <code>DaoDBSearchEception</code> exception
 * objects in case logically wrong user query to db
 * by wrapping application <code>SQLException</code>
 * objects on dao application level
 * 
 */
public class DaoDBSearchException extends DaoTechnicalException {

	private static final long serialVersionUID = -422642363522429608L;

	/**
	 * @param message exception message
	 */
	public DaoDBSearchException(String message) {
		super(message);
	}

	/**
	 * @param message exception message
	 * @param innerEx Throwable inner 
	 * exception instance, which is 
	 * the cause of this exception
	 */
	public DaoDBSearchException(String message, Exception innerEx) {
		super(message);
		/* Initializes the cause of this exception to the specified value */
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public DaoDBSearchException(Exception e) {
		super(e);
	}
}
