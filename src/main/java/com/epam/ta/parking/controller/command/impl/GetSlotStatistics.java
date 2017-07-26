package com.epam.ta.parking.controller.command.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;
import com.epam.ta.parking.service.SlotService;
import com.epam.ta.parking.service.exception.ServiceException;
import com.epam.ta.parking.service.factory.ServiceFactory;

public class GetSlotStatistics implements Command{

	private final static Logger log = Logger.getLogger(GetSlotStatistics.class);

	private static final String DEBUG_STATISTIC_LOAD_SUCCESS = "Service return: slot statistics loaded successfully";
	private static final String DEBUG_STATISTIC_LOAD_FAILED = "Service return: slot statistics is empty.";
	private static final String STATISTIC_LOAD_SUCCESS = "Slot statistics successfully loaded:";
	private static final String ERROR_STATISTIC_LOAD_FAILED = "Slot statistics is empty.";
	private static final String ERROR_STATISTIC_LOAD_TECHNICAL= "Failed to load slot statistics due to technical error. Please, contact your technical support.";

	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		SlotService slotService = serviceFactory.getSlotService();
		
		try {
			
			List<Integer> slotStatistics = slotService.getSlotsStatistics();
			
					if(slotStatistics != null && slotStatistics.size() > 0){
						log.debug(DEBUG_STATISTIC_LOAD_SUCCESS);
						requestWrapper.setAttr(AttrList.SLOT_STATISTICS, slotStatistics);
						requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.SUCCESS_OP);
						requestWrapper.setAttr(AttrList.SUCCESS_MESSAGE, STATISTIC_LOAD_SUCCESS);
					} else {
						log.debug(DEBUG_STATISTIC_LOAD_FAILED);
						requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
						requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_STATISTIC_LOAD_FAILED);
					}

			
		} catch (ServiceException e) {
			log.error(e);
			requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
			requestWrapper.setAttr(AttrList.ERROR_MESSAGE, ERROR_STATISTIC_LOAD_TECHNICAL);
		}

		return requestWrapper;
	}

}
