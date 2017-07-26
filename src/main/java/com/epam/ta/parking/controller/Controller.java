package com.epam.ta.parking.controller;

import org.apache.log4j.Logger;

import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.command.CommandProvider;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.ParamList;
import com.epam.ta.parking.util.NullCheckUtil;

/**
 * <code>Controller</code> class is a from controller class
 * Received a request, dispatch it to a appropriate executor and form a response for a view
 * 
 * */
public class Controller {

	private Controller() {
	}

	private static Controller instance;
	private final CommandProvider provider = new CommandProvider();

	private final static Logger log = Logger.getLogger(Controller.class);
	private static final String GENERAL_FAILURE = "Failed to execute a task";

	public static synchronized Controller getInstance() {
		if (null == instance) {
			instance = new Controller();
		}
		return instance;
	}

	public ArtificialRequestWrapper executeTask(ArtificialRequestWrapper requestWrapper) {
		try {
			Command command = null;
			if (null != requestWrapper) {
				String commandName = (String) requestWrapper.getParameter(ParamList.COMMAND);

				if (NullCheckUtil.notNullCheck(commandName)) {
					
					// get an appropriate executor for request
					command = provider.getCommand(commandName);
					
					// get a response from a specific executor
					requestWrapper = command.execute(requestWrapper);
				}
			}
		} catch (Exception e) {
			log.error(GENERAL_FAILURE, e);
		}
		return requestWrapper;
	}

}
