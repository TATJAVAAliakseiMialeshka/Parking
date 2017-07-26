package com.epam.ta.parking.dao.connector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.log4j.Logger;

import com.epam.ta.parking.dao.exception.DaoConnectionException;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * This <code>MySQLPoolConnection</code> class contains db connection pool
 * 
 */
public class MySQLPoolConnection implements AbstractPoolConnection {

	public static final String JDBS_CONTEXT_PATH = "java:/comp/env/jdbc/parking";
	public static final String ERROR_MSG_CONNECTION_NULL = "Connection is null!";
	public static final String ERROR_CONNECTON_POOL_FAIL = "Can't get connection from pool!";
	public static final String ERROR_DB_PROP_NOT_FOUND = "DB properties file wasn't found!";
	public static final String ERROR_DB_PROP_LOAD = "Error received on reading db properties!";

	/**
	 * This is logger which print some messages to log file
	 */
	private final static Logger logger = Logger.getLogger(MySQLPoolConnection.class);

	private final String DB_PROP_FILENAME = "/db.properties";

	private static volatile MySQLPoolConnection instance = null;

	private MySQLPoolConnection() {
		super();
	}

	/**
	 * @return an instance of MySQLPoolConnection class
	 */
	public static synchronized MySQLPoolConnection getInstance() {
		if (instance == null) {
			instance = new MySQLPoolConnection();
		}
		return instance;
	}

	/**
	 * method gets a connection from connection pool In case of
	 * <code>SQLException</code> wrap it with appropriate dao exception
	 * 
	 * @return Connection with data source
	 * @throws DaoConnectionException
	 */
	public Connection getConnection() throws DaoConnectionException {
		Connection connection = null;
		try {

			DataSource ds = getMySQLDataSource();
			connection = ds.getConnection();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoConnectionException((ERROR_MSG_CONNECTION_NULL), e);

		} catch (FileNotFoundException e) {
			throw new DaoConnectionException((ERROR_DB_PROP_NOT_FOUND), e);
		} catch (IOException e) {
			throw new DaoConnectionException((ERROR_DB_PROP_LOAD), e);
		}
		return connection;
	}

	/**
	 * method create a <code>DataSource</code> object and sets db properties
	 * into it <code>SQLException</code> wrap it with appropriate dao exception
	 * 
	 * @return an instance of MysqlDataSource class
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private DataSource getMySQLDataSource() throws FileNotFoundException, IOException {

		Properties props = new Properties();
		MysqlDataSource ds = null;

		InputStream in = getClass().getResourceAsStream(DB_PROP_FILENAME);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		props.load(br);

		ds = new MysqlConnectionPoolDataSource();
		ds.setURL(props.getProperty("mysql.url"));
		ds.setUser(props.getProperty("mysql.username"));
		ds.setPassword(props.getProperty("mysql.password"));

		return ds;
	}

}
