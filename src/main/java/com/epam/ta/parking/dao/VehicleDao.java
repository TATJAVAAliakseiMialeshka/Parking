package com.epam.ta.parking.dao;

import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.dao.exception.DaoException;

/**
 * <code>VehicleDao</code> interface
 * Define dao methods for implementation instances
 *
 */
public interface VehicleDao {

	Vehicle getVehicleByRegNumber(String regNumber) throws DaoException;
	
	Vehicle createVehicle(String regNumber, String type) throws DaoException;
	
}
