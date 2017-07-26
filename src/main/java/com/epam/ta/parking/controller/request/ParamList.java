package com.epam.ta.parking.controller.request;

/**
 * <code>ParamList</code> enum keeps allowed request parameter names
 */
public enum ParamList {

	COMMAND("command"),
	REG_NUM("regNum"),
	VEHICLE("vehicle"),
	SLOT("slot"),
	DISCOUNT("discount")
	;
	
	/**
	 * This is instance that keeps parameter names
	 */
	private String value;

	private ParamList(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}
	
}
