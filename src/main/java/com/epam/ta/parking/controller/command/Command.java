package com.epam.ta.parking.controller.command;

import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;

/**
 * <code>Command</code> interface define behavior for command implementation instances
 */
public interface Command {

	ArtificialRequestWrapper execute(ArtificialRequestWrapper requestWrapper);
}
