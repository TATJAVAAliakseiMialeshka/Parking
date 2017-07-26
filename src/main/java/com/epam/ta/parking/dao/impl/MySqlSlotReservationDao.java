package com.epam.ta.parking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.dao.DaoErrorMessage;
import com.epam.ta.parking.dao.SlotReservationDao;
import com.epam.ta.parking.dao.connector.ConnetionClosable;
import com.epam.ta.parking.dao.exception.DaoDBSearchException;
import com.epam.ta.parking.dao.exception.DaoDBUpdateException;
import com.epam.ta.parking.dao.exception.DaoException;
import com.epam.ta.parking.dao.exception.DaoLogicalException;
import com.epam.ta.parking.dao.factory.MySQLDao;

/**
 * <code>SlotReservationDao</code> MySQL implementation and implements interface
 * methods
 * 
 */
public class MySqlSlotReservationDao implements SlotReservationDao, ConnetionClosable, MySQLDBQuery, DaoErrorMessage {

	private static MySqlSlotReservationDao instance = null;

	private MySqlSlotReservationDao() {
		super();

	}

	public static synchronized MySqlSlotReservationDao getInstance() {
		if (instance == null) {
			instance = new MySqlSlotReservationDao();
		}
		return instance;
	}

	public SlotReservation freeSlotReservation(Integer id) throws DaoException {

		Connection conn = null;
		PreparedStatement stm = null;
		SlotReservation endedSlotReservation = null;
		try {
			conn = MySQLDao.createConnection();
			stm = conn.prepareStatement(QUERY_FREE_SLOT_RESERVATION);
			stm.setInt(1, id);

			if (stm.executeUpdate() > 0) {
				endedSlotReservation = getSlotReservationById(id);
			}
			
			deleteSlotOccupation(endedSlotReservation.getId());
			
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		} finally {
			closeConnection(stm, conn);
		}
		return endedSlotReservation;
	}

	public SlotReservation occupy(Integer slotNumber, Integer vehicleId) throws DaoException {
		Connection conn = null;
		PreparedStatement stm = null;
		SlotReservation slotReservation = null;
		try {

			conn = MySQLDao.createConnection();

			stm = conn.prepareStatement(QUERY_OCCUPY_A_SLOT, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, slotNumber);
			stm.setInt(2, vehicleId);

			if (stm.executeUpdate() > 0) {

				ResultSet rs = stm.getGeneratedKeys();

				Integer slotReservationId = null;
				if (rs.next()) {
					slotReservationId = rs.getInt(1);
				}
				slotReservation = getSlotReservationById(slotReservationId);

			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DaoLogicalException(ERROR_SLOT_OCCUPIED, e);
		} catch (SQLException e) {
			throw new DaoDBUpdateException(ERROR_DB_OPERATION_FAILED, e);

		} finally {
			closeConnection(stm, conn);
		}
		return slotReservation;
	}

	public SlotReservation getSlotReservationById(Integer id) throws DaoException {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stm = null;
		SlotReservation slotReservation = null;
		try {
			conn = MySQLDao.createConnection();

			stm = conn.prepareStatement(QUERY_GET_SLOT_RESERVATION_BY_ID);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			if (rs.next()) {
				slotReservation = new SlotReservation();
				slotReservation.setId(rs.getInt(1));
				slotReservation.setSlotId(rs.getInt(2));
				slotReservation.setVehicleId(rs.getInt(3));
				slotReservation.setStart(rs.getTimestamp(4));
				slotReservation.setEnd(rs.getTimestamp(5));
			}
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		}

		finally {
			closeConnection(rs, stm, conn);
		}
		return slotReservation;

	}

	public SlotReservation findSlotOccupiedByVehicle(String regNumber) throws DaoException {

		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		SlotReservation slotReservation = null;
		try {

			conn = MySQLDao.createConnection();

			stm = conn.prepareStatement(QUERY_FIND_SLOT_OCCUPIED_BY_VEHICLE);
			stm.setString(1, regNumber);
			rs = stm.executeQuery();
			if (rs.next()) {
				slotReservation = new SlotReservation();
				slotReservation.setId(rs.getInt(1));
				slotReservation.setSlotId(rs.getInt(2));
				slotReservation.setVehicleId(rs.getInt(3));
				slotReservation.setStart(rs.getTimestamp(4));
				slotReservation.setEnd(rs.getTimestamp(5));
			}
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		} finally {
			closeConnection(rs, stm, conn);
		}
		return slotReservation;

	}
	
	public boolean deleteSlotOccupation(Integer id) throws DaoException {

		Connection conn = null;
		PreparedStatement stm = null;
		try {

			conn = MySQLDao.createConnection();

			stm = conn.prepareStatement(QUERY_DELETE_SLOT_RESERVATION);
			stm.setInt(1, id);
			if(stm.executeUpdate() > 0){
				return true;
			}
			
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		} finally {
			closeConnection(stm, conn);
		}
		return false;

	}

}
