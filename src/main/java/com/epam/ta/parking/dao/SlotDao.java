package com.epam.ta.parking.dao;


import java.util.List;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.Slot.Size;
import com.epam.ta.parking.dao.exception.DaoException;

/**
 * <code>SlotDao</code> interface
 * Define dao methods for implementation instances
 *
 */

public interface SlotDao {
	
	boolean addSlot(Size size) throws DaoException;
	
	Slot findSlot(Size size, boolean isCovered) throws DaoException;
	
	List<Integer> getSlotStatistics() throws DaoException;
	
	Slot findSlot(Integer slotId) throws DaoException;

}
