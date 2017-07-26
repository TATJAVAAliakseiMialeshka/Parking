package com.epam.ta.parking.controller.command.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.util.NullCheckUtil;

/**
 * <code>CommandParamValidator</code> validates command parameters
 */
public class CommandParamValidator {

	private final static String REG_NUM_PATT = "[A-z0-9_-]{4,12}";
	private final static String ID_PATT = "[0-9]{1,9}";
	private static final String TIMESTAMP_PATT = "\\d{4}-[0-1]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d{4}";
	private static final String DISCOUNT_PATT = "([0-9]{0,2}\\.)?[0-9]{1,8}";

	public static boolean validateId(Integer id) {

		Matcher idMatcher = null;
		if (NullCheckUtil.notNullCheck(id)) {
			idMatcher = Pattern.compile(ID_PATT).matcher(String.valueOf(id));
			if (idMatcher.matches()) {
				return true;
			}
		}
		return false;
	}

	public static boolean validateTimestamp(Timestamp time) {

		Matcher idMatcher = null;

		if (null != time) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String stringTime = dateFormat.format(new Date());

			idMatcher = Pattern.compile(TIMESTAMP_PATT).matcher(stringTime);
			if (idMatcher.matches()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validateDiscount(String discount) {

		Matcher discountMatcher = null;

		if (null != discount) {
			discountMatcher = Pattern.compile(DISCOUNT_PATT).matcher(discount);
			if (!discountMatcher.matches()) {
				return false;
			}
		}
		return true;
	}


	public static boolean validateRegNum(String regNum) {

		if (NullCheckUtil.notNullCheck(regNum)) {
			Matcher loginMatcher = Pattern.compile(REG_NUM_PATT).matcher(regNum);
			if (loginMatcher.matches()) {
				return true;
			}
		}
		return false;
	}

	public static boolean validateSlot(Slot slot) {

		if (null == slot) {
			return false;
		}

		Integer slotNumber = slot.getNumber();
		if (NullCheckUtil.notNullCheck(slotNumber)) {
			if (!validateId(slotNumber)) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateVehicle(Vehicle vehicle) {

		if (null == vehicle) {
			return false;
		}

		Integer vehicleId = vehicle.getId();
		if (NullCheckUtil.notNullCheck(vehicleId)) {
			if (!validateId(vehicleId)) {
				return false;
			}
		}

		String regNum = vehicle.getRegNumber();
		if (NullCheckUtil.notNullCheck(regNum)) {
			if (!validateRegNum(regNum)) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateSlotReservation(SlotReservation reservation) {

		if (null == reservation) {
			return false;
		}

		Integer reservationId = reservation.getId();
		if (NullCheckUtil.notNullCheck(reservationId)) {
			if (!validateId(reservationId)) {
				return false;
			}
		}

		Integer vehicleId = reservation.getId();
		if (NullCheckUtil.notNullCheck(vehicleId)) {
			if (!validateId(vehicleId)) {
				return false;
			}
		}

		Integer slotId = reservation.getSlotId();
		if (NullCheckUtil.notNullCheck(slotId)) {
			if (!validateId(slotId)) {
				return false;
			}
		}

		Timestamp startTime = reservation.getStart();
		if (null != startTime) {
			if (!validateTimestamp(startTime)) {
				return false;
			}
		}

		Timestamp endTime = reservation.getStart();
		if (null != endTime) {
			if (!validateTimestamp(endTime)) {
				return false;
			}
		}
		return true;
	}

}
