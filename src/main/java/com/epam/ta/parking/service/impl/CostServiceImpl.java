package com.epam.ta.parking.service.impl;

import com.epam.ta.parking.bean.Slot.Size;
import com.epam.ta.parking.dao.SlotDao;
import com.epam.ta.parking.dao.exception.DaoException;
import com.epam.ta.parking.dao.exception.DaoLogicalException;
import com.epam.ta.parking.dao.factory.DBType;
import com.epam.ta.parking.dao.factory.DaoFactory;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.service.CostService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.exception.ServiceLogicalException;

public class CostServiceImpl implements CostService {

	private final static int MILLIS_IN_HOUR = 3600000;
	private final static double BASE_HOUR_PRICE = 0.5;
	private final static int LOW_DURATION = 4;
	private final static int MIDDLE_DURATION = 12;
	private final static double LOW_TIME_COEFFICIENT = 1.5;
	private final static double MIDDLE_TIME_COEFFICIENT = 1.2;
	private final static double HIGH_TIME_COEFFICIENT = 1d;

	private final static double LOW_SIZE_COEFFICIENT = 1d;
	private final static double HIGH_SIZE_COEFFICIENT = 1.2;

	private final static double LOW_IS_COVERED_COEFFICIENT = 1;
	private final static double HIGH_IS_COVERED_COEFFICIENT = 1.2;

	private static final String NULL_PARAMETER = "Received null parameter";
	private static final String CALC_FAILURE_DISCOUNT = "Failed to calculate cost with provided discount";

	public Double getOccupationCost(SlotReservation slotReservation, String discountStr) throws ServiceException {

		if (null == slotReservation) {
			throw new ServiceException(NULL_PARAMETER);
		}
		Double occupationCost = null;
		Slot slot = null;
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				SlotDao slotDao = factory.getSlotDao();

				slot = slotDao.findSlot(slotReservation.getSlotId());
			}

			long start = slotReservation.getStart().getTime();
			long end = slotReservation.getEnd().getTime();
			long occupationTime = end - start;

			double hours = occupationTime / MILLIS_IN_HOUR;

			Size slotSize = slot.getSize();
			Boolean isCovered = slot.getIsCovered();

			if (null != slotSize && null != isCovered) {

				occupationCost = calcTimeCofficient(hours) * calcSlotSizeCofficient(slotSize)
						* calcIsCovereCoefficient(isCovered) * hours * BASE_HOUR_PRICE;
			}

			if (null != discountStr) {
				Double discount = Double.valueOf(discountStr);
				if (discount > 0) {
					occupationCost *= (1 - (discount / 100));
				}
			}
			occupationCost = round(occupationCost, 2);
		} catch (NumberFormatException e) {
			throw new ServiceLogicalException(CALC_FAILURE_DISCOUNT, e);
		} catch (DaoLogicalException e) {
			throw new ServiceLogicalException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return occupationCost;

	}

	public double calcTimeCofficient(double hours) {
		if (hours < LOW_DURATION) {
			return LOW_TIME_COEFFICIENT;
		} else if (hours < MIDDLE_DURATION) {
			return MIDDLE_TIME_COEFFICIENT;
		} else {
			return HIGH_TIME_COEFFICIENT;
		}
	}

	public double calcSlotSizeCofficient(Size size) {
		double sizeCoefficient = HIGH_SIZE_COEFFICIENT;
		if (null != size) {
			if (size == Size.REGULAR) {
				sizeCoefficient = LOW_SIZE_COEFFICIENT;
			} else {
				sizeCoefficient = HIGH_SIZE_COEFFICIENT;
			}
		}
		return sizeCoefficient;
	}

	public double calcIsCovereCoefficient(boolean isCovered) {
		if (isCovered) {
			return HIGH_IS_COVERED_COEFFICIENT;
		} else {
			return LOW_IS_COVERED_COEFFICIENT;
		}
	}

	private double round(double value, int places) {
		double result = value;
		if (places > 0) {
			long factor = (long) Math.pow(10, places);
			value = value * factor;
			long tmp = Math.round(value);
			result = (double) tmp / factor;
		}
		return result;
	}

}
