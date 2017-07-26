package com.epam.ta.parking.dao.factory;

import java.sql.Connection;

import com.epam.ta.parking.dao.SlotDao;
import com.epam.ta.parking.dao.SlotReservationDao;
import com.epam.ta.parking.dao.VehicleDao;
import com.epam.ta.parking.dao.connector.MySQLPoolConnection;
import com.epam.ta.parking.dao.exception.DaoConnectionException;
import com.epam.ta.parking.dao.impl.MySqlSlotDao;
import com.epam.ta.parking.dao.impl.MySqlSlotReservationDao;
import com.epam.ta.parking.dao.impl.MySqlVehicleDao;

/**
 * <code>DaoFactory</code> MySQL implementation
 * 
 */
public class MySQLDao extends DaoFactory {
	
	
	private static MySQLDao instance = null;
	
	private MySQLDao(){
		super();
	}
	
	public static synchronized MySQLDao getInstance(){
		if (instance == null){
			instance = new MySQLDao();
		}
		return instance;
	}
	
	public static Connection createConnection() throws DaoConnectionException {
		MySQLPoolConnection mySQLConn = MySQLPoolConnection.getInstance();
		return mySQLConn.getConnection();
	
	}
 

	public SlotDao getSlotDao() {
		return MySqlSlotDao.getInstance();
	}


	public VehicleDao getVehicleDao() {
		return MySqlVehicleDao.getInstance();
	}

	public SlotReservationDao getSlotReservationDao() {
		return MySqlSlotReservationDao.getInstance();
	}


	

}
