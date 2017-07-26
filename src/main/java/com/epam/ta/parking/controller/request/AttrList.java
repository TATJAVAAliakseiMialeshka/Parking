package com.epam.ta.parking.controller.request;

/**
 * <code>AttrList</code> enum keeps allowed request attribute names
 */
public enum AttrList {


	REQUEST_RESULT("requestResult"),
	ERROR_MESSAGE("errorMessage"),
	SUCCESS_MESSAGE("successMessage"),
	SLOT_STATISTICS("slotStatistics"),
	SLOT_RESERVATION("slotReservation"),
	RESERVATION_COST("reservationCost"),
	SUCCESS_OP("success"),
	FAILED_OP("failed"),
	VEHICLE_NOT_FOUND("Vehicle wasn't found in db."),
	;
	
	/**
	 * This is instance that keeps attribute names
	 */	
	private String value;

	private AttrList(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}

}
