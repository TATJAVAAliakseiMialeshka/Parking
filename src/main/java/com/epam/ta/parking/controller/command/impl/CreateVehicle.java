package com.epam.ta.parking.controller.command.impl;

import org.apache.log4j.Logger;

import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.command.util.CommandParamValidator;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;
import com.epam.ta.parking.controller.request.ParamList;
import com.epam.ta.parking.controller.request.SessionAttrList;
import com.epam.ta.parking.service.VehicleService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.exception.ServiceLogicalException;
import com.epam.ta.parking.service.factory.ServiceFactory;

public class CreateVehicle implements Command {

	private static final Logger log = Logger.getLogger(CreateVehicle.class);

	private static final String DEBUG_VEHICLE_CREATED = "Service return: vehicle created.";
	private static final String DEBUG_VEHICLE_NOT_CREATED = "Service return: vehicle wasn't created.";
	private static final String DEBUG_NULL_PARAMETER = "Received null parameter.";
	
	private static final String SUCCESS_VEHICLE_REGISTERED = "Vehicle registered.";
	private static final String ERROR_REGISTER_VEHICLE = "Failed to register vehicle due technical error. Please, contact your technical support.";
	private static final String ERROR_WRONG_REQUEST = "Wrong request. Check your input data.";
	
	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		VehicleService vehicleService = serviceFactory.getVehicleService();
		
		try {
			Vehicle vehicle = (Vehicle) requestWrapper.getParameter(ParamList.VEHICLE);

			if (CommandParamValidator.validateVehicle(vehicle) && null != vehicle.getType()) {

				Vehicle registeredVehicle = vehicleService.createVehicle(vehicle);
				if (null != registeredVehicle) {
					log.debug(DEBUG_VEHICLE_CREATED);
					requestWrapper.setSessionAttr(SessionAttrList.VEHICLE, registeredVehicle);
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.SUCCESS_OP);
					requestWrapper.setAttr(AttrList.SUCCESS_MESSAGE, SUCCESS_VEHICLE_REGISTERED);
				} else {
					log.debug(DEBUG_VEHICLE_NOT_CREATED);
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
					requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_REGISTER_VEHICLE);
				}

			} else {
				log.debug(DEBUG_NULL_PARAMETER);
				requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
				requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_WRONG_REQUEST);
			}
		} catch (ServiceLogicalException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE, e.getCause().getMessage());
		} catch (ServiceException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE,
					ERROR_REGISTER_VEHICLE);
		}

		return requestWrapper;
	}

}
