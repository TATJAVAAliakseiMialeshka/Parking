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
import com.epam.ta.parking.service.factory.ServiceFactory;

public class TrackTheVehicle implements Command{

	private final static Logger log = Logger.getLogger(TrackTheVehicle.class);
	
	private static final String DEBUG_VEHICLE_FOUND = "Service return: vehicle found";
	private static final String DEBUG_VEHICLE_NOT_FOUND = "Service return: vehicle wasn't found";
	private static final String DEBUG_NULL_PARAMETER = "Received null parameter.";
	
	private static final String SUCCESS_VEHICLE_FOUND = "Vehicle found.";
	private static final String ERROR_VEHICLE_NOT_FOUND = "Vehicle wasn't found in db.";
	private static final String ERROR_FIND_VEHICLE_TECHNICAL = "Failed to find the vehicle due technical error. Please, contact your technical support.";
	private static final String ERROR_WRONG_REQUEST = "Wrong request. Check your input data.";
	
	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		VehicleService vehicleService = serviceFactory.getVehicleService();
		
		try {
			String regNum = (String) requestWrapper.getParameter(ParamList.REG_NUM);
			
			if (CommandParamValidator.validateRegNum(regNum)) {
				
					Vehicle vehicle = vehicleService.getVehicle(regNum);
					if(vehicle != null){
						log.debug(DEBUG_VEHICLE_FOUND);
						requestWrapper.setSessionAttr(SessionAttrList.VEHICLE, vehicle);
						requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.SUCCESS_OP);
						requestWrapper.setAttr(AttrList.SUCCESS_MESSAGE, SUCCESS_VEHICLE_FOUND);
					} else {
						log.debug(DEBUG_VEHICLE_NOT_FOUND);
						requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.VEHICLE_NOT_FOUND);
						requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_VEHICLE_NOT_FOUND);
					}

			} else {
				log.debug(DEBUG_NULL_PARAMETER);
				requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
				requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_WRONG_REQUEST);
			}
		} catch (ServiceException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_FIND_VEHICLE_TECHNICAL);
		}

		return requestWrapper;
	}

}
