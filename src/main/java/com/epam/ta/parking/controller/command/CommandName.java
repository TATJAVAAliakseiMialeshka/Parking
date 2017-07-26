package com.epam.ta.parking.controller.command;

/**
 * <code>CommandName</code> enum keeps allowed command names
 */
public enum CommandName {
	TRACK_THE_VEHICLE("track_the_vehicle"),
	CREATE_THE_VEHICLE("create_the_vehicle"),
	WRONG_REQUEST("wrong_request"),
	ADD_SLOT("add_slot"),
	RESERVE_SLOT("reserve_slot"),
	GET_SLOT_STATISTICS("get_slot_statistics"),
	GET_CURRENT_SLOT_RESERVATION("get_current_slot_reservation"),
	FREE_SLOT_RESERVATION("free_slot_reservation");
	
	private String value;

	private CommandName(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}
}
