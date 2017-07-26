package com.epam.ta.parking.controller.command.impl;

import org.apache.log4j.Logger;

import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.command.util.CommandParamValidator;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;
import com.epam.ta.parking.controller.request.ParamList;
import com.epam.ta.parking.service.CostService;
import com.epam.ta.parking.service.SlotService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.exception.ServiceLogicalException;
import com.epam.ta.parking.service.factory.ServiceFactory;

public class FreeSlotReservation implements Command {

	private static final Logger log = Logger.getLogger(FreeSlotReservation.class);

	private static final String DEBUG_RESERVATION_ENDED_SUCCESS = "Service return: Reservation slot successfully ended";

	private static final String DEBUG_FAILED_RESERVATION_ENDED = "Service return: fail to end slot reservation";
	private static final String FAILED_CALC_COST = "Failed to calc reservation cost.";
	private static final String DEBUG_RESERVATION_NOT_FOUND = "Reservation slot for provided car wasn't found.";
	private static final String DEBUG_NULL_PARAMETER = "Received null parameter.";

	private static final String SUCCESS_RESERVATION_ENDED = "Reservaton slot for this vehicle successfully ended:";
	private static final String FAILED_RESERVATION_ENDED = "Fail to end slot reservation:";
	private static final String RESERV_RESERVATION_NOT_FOUND = "There is not reservation slot registered on that vehicle.";
	private static final String ERROR_END_RESERVATION = "Failed to register vehicle due technical error. Please, contact your technical support.";
	private static final String ERROR_WRONG_DISCOUNT_FORMAT = "Wrong discont format. Check your input data.";
	private static final String ERROR_WRONG_REQUEST = "Wrong request. Check your input data.";

	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SlotService slotService = serviceFactory.getSlotService();
		CostService costService = serviceFactory.getCostService();
		SlotReservation currentSlotReservation = null;
		try {

			Vehicle vehicleOnWhichCheckSlotReservation = (Vehicle) requestWrapper.getParameter(ParamList.VEHICLE);

			if (CommandParamValidator.validateVehicle(vehicleOnWhichCheckSlotReservation)) {

				currentSlotReservation = slotService.getCurrentSlotReservation(vehicleOnWhichCheckSlotReservation);

				if (null != currentSlotReservation) {
					SlotReservation endedSlotReservation = currentSlotReservation = slotService
							.freeSlotReservation(currentSlotReservation);

					if (null != endedSlotReservation) {
											
						String discount = (String) requestWrapper.getParameter(ParamList.DISCOUNT);

						if (CommandParamValidator.validateDiscount(discount)) {
							
							Double reservationCost = costService.getOccupationCost(endedSlotReservation, null == discount ? discount : null);

							if (null != reservationCost) {

								log.debug(DEBUG_RESERVATION_ENDED_SUCCESS);
								requestWrapper.setAttr(AttrList.SLOT_RESERVATION, endedSlotReservation);
								requestWrapper.setAttr(AttrList.RESERVATION_COST, reservationCost);
								requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.SUCCESS_OP);
								requestWrapper.setAttr(AttrList.SUCCESS_MESSAGE, SUCCESS_RESERVATION_ENDED);
							} else {
								log.debug(FAILED_CALC_COST);
								requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
								requestWrapper.setAttr(AttrList.ERROR_MESSAGE, FAILED_CALC_COST);
							}
						}
					} else {
						log.debug(DEBUG_FAILED_RESERVATION_ENDED);
						requestWrapper.setAttr(AttrList.SLOT_RESERVATION, endedSlotReservation);
						requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
						requestWrapper.setAttr(AttrList.ERROR_MESSAGE, FAILED_RESERVATION_ENDED);
					}
				} else {
					log.debug(DEBUG_RESERVATION_NOT_FOUND);
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
					requestWrapper.setAttr(AttrList.ERROR_MESSAGE, RESERV_RESERVATION_NOT_FOUND);
				}

			} else {
				log.debug(DEBUG_NULL_PARAMETER);
				requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
				requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_WRONG_REQUEST);
			}
		} catch (ServiceLogicalException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_WRONG_DISCOUNT_FORMAT);
		} catch (ServiceException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_END_RESERVATION);
		}

		return requestWrapper;
	}

}
