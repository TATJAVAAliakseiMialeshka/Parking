package com.epam.ta.parking.service;

import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.service.exception.ServiceException;

/**
 * <code>VehicleService</code> interface
 * Define service methods for implementation instances
 *
 */
public interface VehicleService {

	Vehicle getVehicle(String regNumber) throws ServiceException;
	
	Vehicle createVehicle(Vehicle vehicle) throws ServiceException;
}
