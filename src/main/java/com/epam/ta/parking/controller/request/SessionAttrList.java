package com.epam.ta.parking.controller.request;

	/**
	 * <code>SessionAttrList</code> enum keeps allowed session attribute names
	 */
public enum SessionAttrList {

	VEHICLE("vehicle"),
	SLOT("slot"),
	SLOT_RESERVATION("slotReservation");
	
	
	/**
	 * This is instance that keeps attribute names
	 */
	private String value;

	private SessionAttrList(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}
}
