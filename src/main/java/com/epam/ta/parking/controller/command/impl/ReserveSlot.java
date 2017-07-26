package com.epam.ta.parking.controller.command.impl;

import org.apache.log4j.Logger;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.bean.Vehicle.Type;
import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.command.util.CommandParamValidator;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;
import com.epam.ta.parking.controller.request.SessionAttrList;
import com.epam.ta.parking.service.SlotService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.exception.ServiceLogicalException;
import com.epam.ta.parking.service.factory.ServiceFactory;

public class ReserveSlot implements Command {

	private static final Logger log = Logger.getLogger(ReserveSlot.class);
	private static final int RESERVATION_TRY_COUNT = 5;

	private static final String DEBUG_OCCUPY_SUCCESS = "Service return: slot occupied successfully.";
	private static final String DEBUG_OCCUPY_FAILED = "Service return: slot wasn't occupied.";
	private static final String DEBUG_ERROR_FREE_SLOT_NOT_FOUND = "Service return: slot wasn't found.";
	private static final String DEBUG_ERROR_NO_VEHICLE_PROVIDED = "No vehicle provided. Ask for input.";
	private static final String SUCCESS_VEHICLE_OCCUPIED = "Vehicle occupied provided slot.";
	private static final String ERROR_VEHICLE_OCCUPY = "Slot occupation failed due to technical error. Please, contact your technical support.";
	private static final String ERROR_FREE_SLOT_NOT_FOUND = "Failed to find free slot for this vehicle. Please, increase the amount of slots or wait for actual slot is reserved.";
	private static final String ERROR_NO_VEHICLE_PROVIDED = "No vehicle provided. Track the vehicle first.";
	private static final String ERROR_VEHICLE_ALREADY_HAS_SLOT = "This vehicle is already provided by a slot.";

	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SlotService slotService = serviceFactory.getSlotService();

		try {
			Vehicle vehicleForWhichSlotNeeded = (Vehicle) requestWrapper.getSessionAttr(SessionAttrList.VEHICLE);

			Type vehicleType = null;
			Slot slotToReserve = null;
			SlotReservation reservation = null;
			if (CommandParamValidator.validateVehicle(vehicleForWhichSlotNeeded) && null != (vehicleType = vehicleForWhichSlotNeeded.getType())) {

				// try to find and occupy free slot few times, in case someone
				// already occupied it
				for (int i = 0; i < RESERVATION_TRY_COUNT && reservation == null; i++) {
					slotToReserve = slotService.findFreeSlot(vehicleType, false);
					if (slotToReserve != null) {
						reservation = slotService.occupy(vehicleForWhichSlotNeeded, slotToReserve);
					}
				}
				if (slotToReserve != null) {

					if (reservation != null) {

						log.debug(DEBUG_OCCUPY_SUCCESS);
						requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.SUCCESS_OP);
						requestWrapper.setAttr(AttrList.SUCCESS_MESSAGE, SUCCESS_VEHICLE_OCCUPIED);
						requestWrapper.setSessionAttr(SessionAttrList.SLOT, slotToReserve);
						requestWrapper.setSessionAttr(SessionAttrList.SLOT_RESERVATION, reservation);
					} else {

						log.debug(DEBUG_OCCUPY_FAILED);
						requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
						requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_VEHICLE_OCCUPY);
					}

				} else {
					log.debug(DEBUG_ERROR_FREE_SLOT_NOT_FOUND);
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
					requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_FREE_SLOT_NOT_FOUND);
				}

			} else {
				log.debug(DEBUG_ERROR_NO_VEHICLE_PROVIDED);
				requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
				requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_NO_VEHICLE_PROVIDED);
			}
		} catch (ServiceLogicalException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_VEHICLE_ALREADY_HAS_SLOT);
		} catch (ServiceException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_VEHICLE_OCCUPY);
		}

		return requestWrapper;
	}

}
