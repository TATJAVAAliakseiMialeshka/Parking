package com.epam.ta.parking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.Slot.Size;
import com.epam.ta.parking.dao.DaoErrorMessage;
import com.epam.ta.parking.dao.SlotDao;
import com.epam.ta.parking.dao.connector.ConnetionClosable;
import com.epam.ta.parking.dao.exception.DaoDBSearchException;
import com.epam.ta.parking.dao.exception.DaoDBUpdateException;
import com.epam.ta.parking.dao.exception.DaoException;
import com.epam.ta.parking.dao.factory.MySQLDao;

/**
 * <code>SlotDao</code> MySQL implementation and implements
 * interface methods
 * 
 */
public class MySqlSlotDao implements SlotDao, ConnetionClosable, MySQLDBQuery, DaoErrorMessage{


	private static MySqlSlotDao instance = null;

	private MySqlSlotDao() {
		super();

	}

	public static synchronized MySqlSlotDao getInstance() {
		if (instance == null) {
			instance = new MySqlSlotDao();
		}
		return instance;
	}

	public boolean addSlot(Size size) throws DaoException {

		Connection conn = null;
		PreparedStatement stm = null;
		try {
			if (null != size) {
				conn = MySQLDao.createConnection();

				stm = conn.prepareStatement(QUERY_ADD_SLOT);
				stm.setString(1, size.name());

				if (stm.executeUpdate() > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new DaoDBUpdateException(ERROR_DB_OPERATION_FAILED, e);
		} finally {
			closeConnection(stm, conn);
		}
		return false;
	}

	public Slot findSlot(Size size, boolean isCovered) throws DaoException {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stm = null;
		Slot slot = null;
		try {
			conn = MySQLDao.createConnection();
			if (null != size) {
				stm = conn.prepareStatement(QUERY_FIND_SLOT);
				stm.setString(1, String.valueOf(size));
				stm.setBoolean(2, isCovered);
				rs = stm.executeQuery();
				if (rs.next()) {
					slot = new Slot();
					slot.setNumber(rs.getInt(1));
					slot.setSize(rs.getString(2));

					slot.setIsCovered(rs.getBoolean(3));
					System.out.println();
				}
			}
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		}

		finally {
			closeConnection(rs, stm, conn);
		}
		return slot;
	}

	


	public List<Integer> getSlotStatistics() throws DaoException {
		List<Integer> statisticsData = new ArrayList<>(2);
		Connection conn = null;
		ResultSet rs = null;
		Statement stm = null;
		try {
			conn = MySQLDao.createConnection();

				stm = conn.createStatement();
				rs = stm.executeQuery(QUERY_GET_SLOT_STATISTICS);
				
				if (rs.next()) {
					statisticsData.add(rs.getInt(1));
					statisticsData.add(rs.getInt(2));
				}
			
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		}

		finally {
			closeConnection(rs, stm, conn);
		}
		return statisticsData;
	}
	

	@Override
	public Slot findSlot(Integer slotId) throws DaoException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stm = null;
		Slot slot = null;
		try {
			conn = MySQLDao.createConnection();

				stm = conn.prepareStatement(
						QUERY_GET_FIND_A_SLOT);
				stm.setInt(1, slotId);
				rs = stm.executeQuery();
				if (rs.next()) {
					slot = new Slot();
					slot.setNumber(rs.getInt(1));
					slot.setSize(rs.getString(2));
					slot.setIsCovered(rs.getBoolean(3));
				}
		} catch (SQLException e) {
			throw new DaoDBSearchException(ERROR_DB_OPERATION_FAILED, e);
		}

		finally {
			closeConnection(rs, stm, conn);
		}
		return slot;
	}


}
