package com.epam.ta.parking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.dao.DaoErrorMessage;
import com.epam.ta.parking.dao.VehicleDao;
import com.epam.ta.parking.dao.connector.ConnetionClosable;
import com.epam.ta.parking.dao.exception.DaoDBSearchException;
import com.epam.ta.parking.dao.exception.DaoDBUpdateException;
import com.epam.ta.parking.dao.exception.DaoException;
import com.epam.ta.parking.dao.exception.DaoLogicalException;
import com.epam.ta.parking.dao.factory.MySQLDao;

/**
 * <code>VehicleDao</code> MySQL implementation and implements interface methods
 * 
 */
public class MySqlVehicleDao implements VehicleDao, ConnetionClosable, MySQLDBQuery, DaoErrorMessage {

	private static MySqlVehicleDao instance = null;

	private MySqlVehicleDao() {
		super();
	}

	public static synchronized MySqlVehicleDao getInstance() {
		if (instance == null) {
			instance = new MySqlVehicleDao();
		}
		return instance;
	}

	public Vehicle getVehicleByRegNumber(String regNumber) throws DaoException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stm = null;
		Vehicle vehicle = null;
		try {
			conn = MySQLDao.createConnection();

			stm = conn.prepareStatement(QUERY_GET_VEHICLE_BY_REG_NUMBER);
			stm.setString(1, regNumber);
			rs = stm.executeQuery();
			if (rs.next()) {
				vehicle = new Vehicle();
				vehicle.setId(rs.getInt(1));
				vehicle.setRegNumber(rs.getString(2));
				vehicle.setType(rs.getString(3));
			}
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		}

		finally {
			closeConnection(rs, stm, conn);
		}
		return vehicle;
	}

	public Vehicle createVehicle(String regNumber, String type) throws DaoException {
		Connection conn = null;
		PreparedStatement stm = null;
		Vehicle registeredVehicle = null;
		try {
			conn = MySQLDao.createConnection();
			stm = conn.prepareStatement(QUERY_CREATE_A_VEHICLE);
			stm.setString(1, regNumber);
			stm.setString(2, type);

			if (stm.executeUpdate() > 0) {
				registeredVehicle = getVehicleByRegNumber(regNumber);
			} else {
				throw new DaoDBUpdateException(ERROR_ADD_VEHICLE_FAILED);
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DaoLogicalException(ERROR_VEHICLE_EXISTS, e);
		} catch (SQLException e) {
			throw new DaoDBUpdateException(ERROR_DB_OPERATION_FAILED, e);
		} finally {
			closeConnection(stm, conn);
		}
		return registeredVehicle;
	}

}
