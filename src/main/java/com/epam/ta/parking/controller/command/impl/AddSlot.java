package com.epam.ta.parking.controller.command.impl;

import org.apache.log4j.Logger;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.command.util.CommandParamValidator;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;
import com.epam.ta.parking.controller.request.ParamList;
import com.epam.ta.parking.service.SlotService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.factory.ServiceFactory;

public class AddSlot implements Command{

	
	private static final Logger log = Logger.getLogger(AddSlot.class);

	private static final String DEBUG_SLOT_ADD_FAILED = "Service return slot wasn't added";
	private static final String DEBUG_NULL_PARAM = "Received null parameter.";
	
	private static final String SUCCESS_SLOT_ADDED = "New slot added successfully.";
	private static final String ERROR_FAILED_TO_ADD_SLOT = "Failed to add slot due technical error. Please, contact your technical support.";
	private static final String ERROR_WRONG_REQUEST = "Wrong request. Check your input data.";
	
	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SlotService slotService = serviceFactory.getSlotService();

		try {
			Slot slotToAdd = (Slot) requestWrapper.getParameter(ParamList.SLOT);

			if (CommandParamValidator.validateSlot(slotToAdd) && null != slotToAdd.getSize()) {

				if (slotService.addSlot(slotToAdd)) {
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.SUCCESS_OP);
					requestWrapper.setAttr(AttrList.SUCCESS_MESSAGE, SUCCESS_SLOT_ADDED);
				} else {
					log.debug(DEBUG_SLOT_ADD_FAILED);
					requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
					requestWrapper.setAttr(AttrList.ERROR_MESSAGE,
							ERROR_FAILED_TO_ADD_SLOT);
				}

			} else {
				log.debug(DEBUG_NULL_PARAM);
				requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
				requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_WRONG_REQUEST);
			}
		} catch (ServiceException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE,
					ERROR_FAILED_TO_ADD_SLOT);
		}

		return requestWrapper;
	}

}
