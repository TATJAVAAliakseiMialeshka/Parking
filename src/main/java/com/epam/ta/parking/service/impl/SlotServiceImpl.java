package com.epam.ta.parking.service.impl;

import java.util.List;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.bean.Slot.Size;
import com.epam.ta.parking.bean.Vehicle.Type;
import com.epam.ta.parking.dao.SlotDao;
import com.epam.ta.parking.dao.SlotReservationDao;
import com.epam.ta.parking.dao.exception.DaoException;
import com.epam.ta.parking.dao.exception.DaoLogicalException;
import com.epam.ta.parking.dao.factory.DBType;
import com.epam.ta.parking.dao.factory.DaoFactory;
import com.epam.ta.parking.service.SlotService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.exception.ServiceLogicalException;

public class SlotServiceImpl implements SlotService {

	private static final String NULL_PARAMETER = "Received null parameter";

	@Override
	public Slot findFreeSlot(Type vehicleType, Boolean isCovered) throws ServiceException {
		Slot slot = null;

		if (null == vehicleType || null == isCovered) {
			throw new ServiceException(NULL_PARAMETER);
		}
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				SlotDao slotDao = factory.getSlotDao();
				Size requiredSlotSize = null;
				if (vehicleType == Type.CAR) {
					requiredSlotSize = Size.REGULAR;
				} else if (vehicleType == Type.MOTOCYCLE) {
					requiredSlotSize = Size.LITTLE;
				}
				slot = slotDao.findSlot(requiredSlotSize, isCovered);
			}
		} catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return slot;
	}

	@Override
	public boolean addSlot(Slot slot) throws ServiceException {

		Size size = null;

		if (null == slot || null == (size = slot.getSize())) {
			throw new ServiceException(NULL_PARAMETER);
		}
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				SlotDao slotDao = factory.getSlotDao();
				return slotDao.addSlot(size);
			}
		} catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return false;
	}

	@Override
	public SlotReservation occupy(Vehicle vehicle, Slot slot) throws ServiceException {

		if (null == vehicle || null == slot) {
			throw new ServiceException(NULL_PARAMETER);
		}
		SlotReservation reservation = null;
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				SlotReservationDao slotReservationDao = factory.getSlotReservationDao();
				Integer slotNumber = slot.getNumber();
				Integer vehicleId = vehicle.getId();
				reservation = slotReservationDao.occupy(slotNumber, vehicleId);
			}
		} catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return reservation;
	}

	@Override
	public List<Integer> getSlotsStatistics() throws ServiceException {
		List<Integer> slotStatistics = null;

		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				SlotDao slotDao = factory.getSlotDao();
				slotStatistics = slotDao.getSlotStatistics();
			}
		} catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return slotStatistics;
	}

	@Override
	public SlotReservation getCurrentSlotReservation(Vehicle vehicle) throws ServiceException {

		if (null == vehicle) {
			throw new ServiceException(NULL_PARAMETER);
		}
		SlotReservation reservation = null;
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				SlotReservationDao slotReservationDao = factory.getSlotReservationDao();
				String regNumber = vehicle.getRegNumber();
				reservation = slotReservationDao.findSlotOccupiedByVehicle(regNumber);
			}
		} catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return reservation;
	}

	@Override
	public SlotReservation freeSlotReservation(SlotReservation slotReservation) throws ServiceException {

		if (null == slotReservation) {
			throw new ServiceException(NULL_PARAMETER);
		}
		SlotReservation reservation = null;
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				SlotReservationDao slotReservationDao = factory.getSlotReservationDao();

				reservation = slotReservationDao.freeSlotReservation(slotReservation.getId());

			}
		} catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return reservation;
	}

}
