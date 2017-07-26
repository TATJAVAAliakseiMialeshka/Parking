package com.epam.ta.parking.dao.exception;

/**
 * error class inherits <code>AbstractParkingException<code> Class to create
 * <code>DoaTechnicalException<code> exception objects on dao application level
 * 
 */
public abstract class DaoTechnicalException extends DaoException {

	private static final long serialVersionUID = -6228238726885540561L;

	/**
	 * @param message
	 *            exception message
	 */
	public DaoTechnicalException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            exception message
	 * @param innerEx
	 *            Throwable inner exception instance, which is the cause of this
	 *            exception
	 */
	public DaoTechnicalException(String message, Exception innerEx) {
		super(message);
		/* Initializes the cause of this exception to the specified value */
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public DaoTechnicalException(Exception e) {
		super(e);
	}

}
