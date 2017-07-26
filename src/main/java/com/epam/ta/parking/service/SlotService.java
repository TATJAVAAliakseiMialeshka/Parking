package com.epam.ta.parking.service;

import java.util.List;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.bean.Vehicle.Type;
import com.epam.ta.parking.service.exception.ServiceException;

/**
 * <code>SlotService</code> interface
 * Define service methods for implementation instances
 *
 */
public interface SlotService {

	boolean addSlot(Slot slot) throws ServiceException;
	
	public Slot findFreeSlot(Type vehicleType, Boolean isCovered) throws ServiceException;
	
	SlotReservation occupy(Vehicle vehicle, Slot slot) throws ServiceException;
	
	List<Integer> getSlotsStatistics() throws ServiceException;
	
	SlotReservation getCurrentSlotReservation(Vehicle vehicle)  throws ServiceException;
	
	SlotReservation freeSlotReservation(SlotReservation slot) throws ServiceException;
	
}
