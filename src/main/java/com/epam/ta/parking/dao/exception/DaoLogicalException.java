package com.epam.ta.parking.dao.exception;

/**
 * error class inherits <code>AbstractParkingException<code> Class to create
 * <code>DoaLogicalException<code> exception objects on dao application level
 * 
 */
public class DaoLogicalException extends DaoException {

	private static final long serialVersionUID = -7568046999353888158L;

	/**
	 * @param message
	 *            exception message
	 */
	public DaoLogicalException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            exception message
	 * @param innerEx
	 *            Throwable inner exception instance, which is the cause of this
	 *            exception
	 */
	public DaoLogicalException(String message, Exception innerEx) {
		super(message);
		/* Initializes the cause of this exception to the specified value */
		initCause(innerEx);
	}

	/**
	 * @param e
	 *            exception
	 */
	public DaoLogicalException(Exception e) {
		super(e);
	}

}
