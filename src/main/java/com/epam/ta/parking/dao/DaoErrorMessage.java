package com.epam.ta.parking.dao;

public interface DaoErrorMessage {

	String ERROR_DB_OPERATION_FAILED = "Database operation failed.";

	String ERROR_SLOT_OCCUPIED = "Slot already exists.";

	String ERROR_VEHICLE_EXISTS = "Vehicle already registered.";
	
	String ERROR_ADD_VEHICLE_FAILED = "Failed to add vehicle. DB error reseived.";
}
