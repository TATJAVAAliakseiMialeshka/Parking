package com.epam.ta.parking.dao.factory;

import org.apache.log4j.Logger;

import com.epam.ta.parking.dao.SlotDao;
import com.epam.ta.parking.dao.SlotReservationDao;
import com.epam.ta.parking.dao.VehicleDao;


/**
 * abstract dao factory class
 * create get dao methods,
 * which will be implemented
 * by concrete generators
 */
public abstract class DaoFactory {

	/**
	 * get <code>SlotDao</code> 
	 * must be realized by 
	 * concrete generator
	 * @return <code>SlotDao</code> 
	 */
	public abstract SlotDao getSlotDao();
	
	/**
	 * get <code>SlotReservationDao</code> 
	 * must be realized by 
	 * concrete generator
	 * @return <code>SlotReservationDao</code> 
	 */
	public abstract SlotReservationDao getSlotReservationDao();
	
	/**
	 * get <code>VehicleDao</code> 
	 * must be realized by 
	 * concrete generator
	 * @return <code>VehicleDao</code> 
	 */
	public abstract VehicleDao getVehicleDao();

	
	public final static Logger logger = Logger.getLogger(DaoFactory.class);
	
	/**
	 * method to choose
	 * concrete dao generator
	 * @param factoryName
	 * @return
	 */
	public static DaoFactory getDaoFactory(DBType dbType){
				
		switch (dbType) {
			case MYSQL:
				return  MySQLDao.getInstance();
			default:
				return null;
			}
	}	

}

