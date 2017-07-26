package com.epam.ta.parking.service.impl;

import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.dao.VehicleDao;
import com.epam.ta.parking.dao.exception.DaoException;
import com.epam.ta.parking.dao.exception.DaoLogicalException;
import com.epam.ta.parking.dao.factory.DBType;
import com.epam.ta.parking.dao.factory.DaoFactory;
import com.epam.ta.parking.service.VehicleService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.exception.ServiceLogicalException;
import com.epam.ta.parking.util.NullCheckUtil;

public class VehicleServiceImpl implements VehicleService {

	private static final String NULL_PARAMETER = "Received null parameter";

	@Override
	public Vehicle getVehicle(String regNumber) throws ServiceException {
		if (!NullCheckUtil.notNullCheck(regNumber)) {
			throw new ServiceException(NULL_PARAMETER);
		}
		Vehicle vehicle = null;
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				VehicleDao vehicleDao = factory.getVehicleDao();
				vehicle = vehicleDao.getVehicleByRegNumber(regNumber);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return vehicle;
	}

	@Override
	public Vehicle createVehicle(Vehicle vehicle) throws ServiceException {
		
		String regNumber = null;
		String type = null;
		
		if(null != vehicle){
			regNumber = vehicle.getRegNumber();
			type = vehicle.getType().name();
			if (!NullCheckUtil.notNullCheck(vehicle.getRegNumber(), vehicle.getType().name())) {
				throw new ServiceException(NULL_PARAMETER);
			}
		}
		Vehicle registeredVehicle = null;		
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				VehicleDao vehicleDao = factory.getVehicleDao();
				registeredVehicle = vehicleDao.createVehicle(regNumber, type);
			}
		}catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return registeredVehicle;
	}

}
