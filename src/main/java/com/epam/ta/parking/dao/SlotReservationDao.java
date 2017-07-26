package com.epam.ta.parking.dao;

import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.dao.exception.DaoException;

/**
 * <code>SlotReservationDao</code> interface
 * Define dao methods for implementation instances
 *
 */
public interface SlotReservationDao {
	
	SlotReservation freeSlotReservation(Integer id) throws DaoException;
	
	SlotReservation getSlotReservationById(Integer id) throws DaoException;
	
	SlotReservation occupy(Integer slotNumber, Integer vehicleId) throws DaoException;
	
	SlotReservation findSlotOccupiedByVehicle(String regNumber) throws DaoException;
	
	boolean deleteSlotOccupation(Integer id) throws DaoException;
}
