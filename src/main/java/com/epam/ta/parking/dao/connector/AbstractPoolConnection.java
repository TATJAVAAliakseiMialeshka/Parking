package com.epam.ta.parking.dao.connector;

import java.sql.Connection;

import com.epam.ta.parking.dao.exception.DaoConnectionException;

/**
 * This interface should be implemented by all connection pool classes
 */
public interface AbstractPoolConnection {

	/**
	 * method gets a connection from connection pool
	 * 
	 * @return Connection with data source
	 * @throws DaoConnectionException
	 * @throws DaoLookupException
	 */
	public Connection getConnection() throws DaoConnectionException;

}
