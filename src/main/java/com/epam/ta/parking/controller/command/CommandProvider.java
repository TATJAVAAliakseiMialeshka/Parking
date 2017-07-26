package com.epam.ta.parking.controller.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.epam.ta.parking.controller.command.impl.AddSlot;
import com.epam.ta.parking.controller.command.impl.CreateVehicle;
import com.epam.ta.parking.controller.command.impl.FreeSlotReservation;
import com.epam.ta.parking.controller.command.impl.GetCurrentSlotReservation;
import com.epam.ta.parking.controller.command.impl.GetSlotStatistics;
import com.epam.ta.parking.controller.command.impl.ReserveSlot;
import com.epam.ta.parking.controller.command.impl.TrackTheVehicle;
import com.epam.ta.parking.controller.command.impl.WrongRequest;

/**
 * <code>CommandProvider</code> class return the command instance that matches String input
 */
public class CommandProvider {

	private final Map<CommandName, Command> repository = new HashMap<>();

	private final static Logger log = Logger.getLogger(CommandProvider.class);

	public CommandProvider() {

		repository.put(CommandName.TRACK_THE_VEHICLE, new TrackTheVehicle());
		repository.put(CommandName.CREATE_THE_VEHICLE, new CreateVehicle());
		repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
		repository.put(CommandName.ADD_SLOT, new AddSlot());
		repository.put(CommandName.RESERVE_SLOT, new ReserveSlot());
		repository.put(CommandName.GET_SLOT_STATISTICS, new GetSlotStatistics());
		repository.put(CommandName.GET_CURRENT_SLOT_RESERVATION, new GetCurrentSlotReservation());
		repository.put(CommandName.FREE_SLOT_RESERVATION, new FreeSlotReservation());

	}

	public Command getCommand(String commandString) {

		CommandName commandName = null;
		Command command = null;
		try {
			commandName = CommandName.valueOf(commandString.toUpperCase());
			command = repository.get(commandName);
		} catch (IllegalArgumentException | NullPointerException e) {
			log.error(e);
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}
}
