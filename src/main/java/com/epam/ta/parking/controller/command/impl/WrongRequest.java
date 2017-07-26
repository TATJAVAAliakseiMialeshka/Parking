package com.epam.ta.parking.controller.command.impl;

import com.epam.ta.parking.controller.command.Command;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;

public class WrongRequest implements Command {

	@Override
	public ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper) {

		requestWrapper.setAttr(AttrList.REQUEST_RESULT, AttrList.FAILED_OP);
		requestWrapper.setAttr(AttrList.ERROR_MESSAGE, "Wrong request.");

		return requestWrapper;
	}

}
