package com.epam.ta.parking.dao.connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.ta.parking.dao.exception.DaoCloseConnectionException;

/**
 * <code>ConnetionClosable</code> has a default db closing connection methods
 * connections
 */

public interface ConnetionClosable {

	String ERROR_CLOSING_CONNECTION = "Failed to close database connection.";

	default void closeConnection(PreparedStatement stm, Connection conn) throws DaoCloseConnectionException {
		if (stm != null || conn != null) {
			try {
				stm.close();
				conn.close();
			} catch (SQLException e) {
				throw new DaoCloseConnectionException(ERROR_CLOSING_CONNECTION, e);
			}
		}
	}

	default void closeConnection(ResultSet rs, PreparedStatement stm, Connection conn)
			throws DaoCloseConnectionException {
		if (rs != null || stm != null || conn != null) {
			try {
				rs.close();
				stm.close();
				conn.close();
			} catch (SQLException e) {
				throw new DaoCloseConnectionException(ERROR_CLOSING_CONNECTION, e);
			}
		}
	}

	default void closeConnection(ResultSet rs, Statement stm, Connection conn) throws DaoCloseConnectionException {
		if (rs != null || stm != null || conn != null) {
			try {
				rs.close();
				stm.close();
				conn.close();
			} catch (SQLException e) {
				throw new DaoCloseConnectionException(ERROR_CLOSING_CONNECTION, e);
			}
		}
	}
}
