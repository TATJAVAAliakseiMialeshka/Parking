package com.epam.ta.parking.service;

import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.service.exception.ServiceException;

/**
 * <code>CostService</code> interface
 * Define service methods for implementation instances
 *
 */
public interface CostService {

	Double getOccupationCost(SlotReservation slotReservation, String discount) throws ServiceException;
}
