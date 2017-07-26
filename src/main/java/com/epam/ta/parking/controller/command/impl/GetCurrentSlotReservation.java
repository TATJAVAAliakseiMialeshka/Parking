package com.epam.ta.parking.controller.command.impl;

import org.apache.log4j.Logger;

import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.command.util.CommandParamValidator;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;
import com.epam.ta.parking.controller.request.ParamList;
import com.epam.ta.parking.service.SlotService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.factory.ServiceFactory;

public class GetCurrentSlotReservation implements Command {

	private static final Logger log = Logger.getLogger(GetCurrentSlotReservation.class);
	
	private static final String DEBUG_RESERVATION_SLOT_FOUND = "Service return: Reservation slot found";
	private static final String DEBUG_RESERVATION_NOT_FOUND = "Reservation slot for provided car wasn't found.";
	private static final String DEBUG_NULL_PARAMETER = "Received null parameter.";
	private static final String SUCCESS_RESERVATION_FOUND = "Reservaton slot for this vehicle found:";
	private static final String RESERVATION_NOT_FOUND = "There is not reservation slot for that vehicle.";
	private static final String ERROR_FIND_RESERVATION = "Reservation slot for provided car wasn't found due to technical error. Please, contact your technical support.";
	private static final String ERROR_WRONG_REQUEST = "Wrong request. Check your input data.";
	
	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SlotService slotService = serviceFactory.getSlotService();

		try {
			Vehicle vehicle = (Vehicle) requestWrapper.getParameter(ParamList.VEHICLE);

			if (CommandParamValidator.validateVehicle(vehicle) && null != vehicle.getRegNumber()) {
				SlotReservation slotReservation = slotService.getCurrentSlotReservation(vehicle);
				if (null != slotReservation) {
					log.debug(DEBUG_RESERVATION_SLOT_FOUND);
					requestWrapper.setAttr(AttrList.SLOT_RESERVATION, slotReservation);
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.SUCCESS_OP);
					requestWrapper.setAttr(AttrList.SUCCESS_MESSAGE, SUCCESS_RESERVATION_FOUND);
				} else {
					log.debug(DEBUG_RESERVATION_NOT_FOUND);
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
					requestWrapper.setAttr(AttrList.ERROR_MESSAGE,
							RESERVATION_NOT_FOUND);
				}

			} else {
				log.debug(DEBUG_NULL_PARAMETER);
				requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
				requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_WRONG_REQUEST);
			}
		} catch (ServiceException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE,
					ERROR_FIND_RESERVATION);
		}

		return requestWrapper;
	}

}
