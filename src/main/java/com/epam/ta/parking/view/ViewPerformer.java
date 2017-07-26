package com.epam.ta.parking.view;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.epam.ta.parking.bean.Slot;
import com.epam.ta.parking.bean.SlotReservation;
import com.epam.ta.parking.bean.Vehicle;
import com.epam.ta.parking.controller.Controller;
import com.epam.ta.parking.controller.command.CommandName;
import com.epam.ta.parking.controller.request.ArtificialRequestWrapper;
import com.epam.ta.parking.controller.request.AttrList;
import com.epam.ta.parking.controller.request.ParamList;
import com.epam.ta.parking.controller.request.SessionAttrList;

/**
 * <code>ViewPerformer</code> class is a console application view Interacts with
 * user through the menu
 *
 */

public class ViewPerformer {

	public ViewPerformer() {
		controller = Controller.getInstance();
		requestWrapper = new ArtificialRequestWrapper();
	}

	private final static Logger log = Logger.getLogger(ViewPerformer.class);

	private final static String TRACK_VEHICLE = "1";
	private final static String RESERVE_SLOT = "2";
	private final static String FREE_SLOT_RESERVATION = "3";

	private final static String REG_VEHICLE = "4";
	private final static String ADD_SLOT = "5";
	private final static String GET_SLOT_STATISTICS = "6";
	private final static String GET_CURRENT_SLOT_RESERVATION = "7";

	private final static String EXIT = "0";

	private Controller controller;
	private ArtificialRequestWrapper requestWrapper;

	public void openConsoleSession() {

		controller = Controller.getInstance();

		Scanner terminal = new Scanner(System.in);
		log.info("====================================");
		log.info("*****Welcome to parking system!*****\n    What can I do for you today?");
		log.info("====================================");
		String menuIdx = "";

		while (!menuIdx.equals(EXIT)) {

			log.info("\n------main actions---------------");
			log.info("\n1. Track the vehicle");
			log.info("2. Reserve slot");
			log.info("3. Free slot reservation");
			log.info("0. Exit");
			log.info("\n-----additional actions---------\n");
			log.info("4. Register new vehicle");
			log.info("5. Add slot");
			log.info("6. Get slot statistics");
			log.info("7. Get current slot reservation");

			if (terminal.hasNext()) {
				menuIdx = terminal.next();
			}

			switch (menuIdx) {
			case TRACK_VEHICLE:

				processTrackVehicleChoice(terminal);

				break;

			case REG_VEHICLE:

				processRegVehicleChoice(terminal);

				break;

			case ADD_SLOT:

				processAddSlotChoice(terminal);

				break;

			case RESERVE_SLOT:

				processReserveSlot();

				break;

			case GET_SLOT_STATISTICS:

				processGetSlotStatistics();

				break;

			case GET_CURRENT_SLOT_RESERVATION:

				processGetCurrentSlotReservation(terminal);

				break;

			case FREE_SLOT_RESERVATION:

				processFreeSlotReservaton(terminal);

				break;

			case EXIT:

				break;

			default:
				log.info("Wrong input.");
				break;
			}
		}

	}

	private void processTrackVehicleChoice(Scanner terminal) {

		log.info("Enter the vehicle registration number:");
		if (terminal.hasNext()) {
			String regNumber = terminal.next();
			requestWrapper.setParameter(ParamList.COMMAND, CommandName.TRACK_THE_VEHICLE.get());
			requestWrapper.setParameter(ParamList.REG_NUM, regNumber);

			requestWrapper = controller.executeTask(requestWrapper);

			AttrList requestResult = null;
			Vehicle registeredVehicle = null;

			requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);

			if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
				registeredVehicle = (Vehicle) requestWrapper.getSessionAttr(SessionAttrList.VEHICLE);
				if (null != registeredVehicle) {
					log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE) + "\nType: " + registeredVehicle.getType()
							+ ", Registration number: " + registeredVehicle.getRegNumber());
				}
			} else if (requestResult.equals(AttrList.FAILED_OP)) {

				log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));

			} else if (requestResult.equals(AttrList.VEHICLE_NOT_FOUND)) {
				log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE) + " Would you like to register it (Y/N) ?");
				if (terminal.hasNext()) {
					String regNewVehicle = terminal.next();

					if (regNewVehicle.equalsIgnoreCase("y")) {

						Vehicle toRegister = new Vehicle();

						log.info("Enter the vehicle type (Car/Motocycle):");

						boolean correctVehicleType = false;

						while (!correctVehicleType) {
							if (terminal.hasNext()) {
								String vehicleType = terminal.next();
								if (toRegister.setType(vehicleType)) {
									correctVehicleType = true;
								} else {
									log.info("Vehicle type is incorrect.\nEnter the vehicle type (Car/Motocycle):");
								}
							}
						}

						toRegister.setRegNumber(regNumber);

						requestWrapper.setParameter(ParamList.COMMAND, CommandName.CREATE_THE_VEHICLE.get());
						requestWrapper.setParameter(ParamList.VEHICLE, toRegister);
						requestWrapper = controller.executeTask(requestWrapper);

						requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);

						if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
							registeredVehicle = (Vehicle) requestWrapper.getSessionAttr(SessionAttrList.VEHICLE);
							if (null != registeredVehicle) {
								log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE) + "\nType: "
										+ registeredVehicle.getType() + ", Registration 1number: "
										+ registeredVehicle.getRegNumber());
							}
						} else {
							log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));
						}
					} else {
						return;
					}
				}
			}
		}
		requestWrapper.clearRequestStorage();
	}

	private void processRegVehicleChoice(Scanner terminal) {
		String regNumber = null;
		Vehicle toRegister = null;
		Vehicle registeredVehicle = null;
		AttrList requestResult = null;

		log.info("Enter the vehicle type (Car/Motocycle):");

		toRegister = new Vehicle();

		boolean correctVehicleType = false;

		while (!correctVehicleType) {
			if (terminal.hasNext()) {
				String vehicleType = terminal.next();
				if (toRegister.setType(vehicleType)) {
					correctVehicleType = true;
				} else {
					log.info("Vehicle type is incorrect.\nEnter the vehicle type (Car/Motocycle):");
				}
			}
		}

		log.info("Enter the vehicle registration number:");
		if (terminal.hasNext()) {
			regNumber = terminal.next();
			toRegister.setRegNumber(regNumber);
		}

		requestWrapper.setParameter(ParamList.COMMAND, CommandName.CREATE_THE_VEHICLE.get());
		requestWrapper.setParameter(ParamList.VEHICLE, toRegister);
		requestWrapper = controller.executeTask(requestWrapper);

		requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);

		if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
			registeredVehicle = (Vehicle) requestWrapper.getSessionAttr(SessionAttrList.VEHICLE);
			if (null != registeredVehicle) {
				log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE) + "\nType: " + registeredVehicle.getType()
						+ ", Registration number: " + registeredVehicle.getRegNumber());
			}
		} else {
			log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));
		}
		requestWrapper.clearRequestStorage();
	}

	private void processAddSlotChoice(Scanner terminal) {
		log.info("Enter the vehicle type (Regular/Little):");

		Slot toAdd = new Slot();
		AttrList requestResult = null;
		boolean correctSlotType = false;

		while (!correctSlotType) {
			if (terminal.hasNext()) {
				String slotType = terminal.next();
				if (toAdd.setSize(slotType)) {
					correctSlotType = true;
				} else {
					log.info("Slot size is incorrect.\nEnter the slot size (Regular/Little):");
				}
			}
		}

		requestWrapper.setParameter(ParamList.COMMAND, CommandName.ADD_SLOT.get());
		requestWrapper.setParameter(ParamList.SLOT, toAdd);
		requestWrapper = controller.executeTask(requestWrapper);
		requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);
		if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
			log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE));
		} else {
			log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));
		}
		requestWrapper.clearRequestStorage();
	}

	private void processReserveSlot() {

		requestWrapper.setParameter(ParamList.COMMAND, CommandName.RESERVE_SLOT.get());
		requestWrapper = controller.executeTask(requestWrapper);

		AttrList requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);
		if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
			log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE));
			Vehicle currentVehicle = (Vehicle) requestWrapper.getSessionAttr(SessionAttrList.VEHICLE);
			Slot reservedSlot = (Slot) requestWrapper.getSessionAttr(SessionAttrList.SLOT);
			SlotReservation reservation = (SlotReservation) requestWrapper
					.getSessionAttr(SessionAttrList.SLOT_RESERVATION);

			log.info("Slot reservation info:\n " + "\tSlot number: " + reservedSlot.getNumber() + "\n\tVehicle type: "
					+ currentVehicle.getType().name() + ", registration number: " + currentVehicle.getRegNumber()
					+ "\n\tReservation time: " + reservation.getStart());
		} else {
			log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));
		}
		requestWrapper.clearRequestStorage();
	}

	private void processGetSlotStatistics() {
		requestWrapper.setParameter(ParamList.COMMAND, CommandName.GET_SLOT_STATISTICS.get());
		requestWrapper = controller.executeTask(requestWrapper);

		AttrList requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);
		if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
			log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE));

			List<?> slotStatistics = (List<?>) requestWrapper.getAttr(AttrList.SLOT_STATISTICS);

			log.info("\tAll slots count: " + slotStatistics.get(0) + "\n\tFree slots count: " + slotStatistics.get(1));
		} else {
			log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));
		}
		requestWrapper.clearRequestStorage();
	}

	private void processGetCurrentSlotReservation(Scanner terminal) {

		Vehicle vehicleToCheckIsHasReservation = new Vehicle();

		log.info("Enter the vehicle registration number:");
		if (terminal.hasNext()) {
			String regNumber = terminal.next();
			vehicleToCheckIsHasReservation.setRegNumber(regNumber);
		}

		requestWrapper.setParameter(ParamList.COMMAND, CommandName.GET_CURRENT_SLOT_RESERVATION.get());
		requestWrapper.setParameter(ParamList.VEHICLE, vehicleToCheckIsHasReservation);

		requestWrapper = controller.executeTask(requestWrapper);

		AttrList requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);
		if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
			log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE));

			SlotReservation slotReservation = (SlotReservation) requestWrapper.getAttr(AttrList.SLOT_RESERVATION);

			log.info("\tReservaton start time: " + slotReservation.getStart());
		} else {
			log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));
		}
		requestWrapper.clearRequestStorage();
	}

	private void processFreeSlotReservaton(Scanner terminal) {

		Vehicle vehicleToCheckIsHasReservation = new Vehicle();

		log.info("Enter the vehicle registration number:");
		if (terminal.hasNext()) {
			String regNumber = terminal.next();
			vehicleToCheckIsHasReservation.setRegNumber(regNumber);
		}

		log.info("Does this costumer has a discount? (Y/N):");

		if (terminal.hasNext()) {
			String isCustomerHasADiscount = terminal.next();
			if (isCustomerHasADiscount.equalsIgnoreCase("Y")) {
				log.info("Enter customer discount (%):");
				if (terminal.hasNext()) {
					String discount = terminal.next();
					requestWrapper.setParameter(ParamList.DISCOUNT, discount);
				}
			}
		}

		requestWrapper.setParameter(ParamList.COMMAND, CommandName.FREE_SLOT_RESERVATION.get());
		requestWrapper.setParameter(ParamList.VEHICLE, vehicleToCheckIsHasReservation);

		requestWrapper = controller.executeTask(requestWrapper);

		AttrList requestResult = (AttrList) requestWrapper.getAttr(AttrList.REQUEST_RESULT);
		if (null != requestResult && requestResult.equals(AttrList.SUCCESS_OP)) {
			log.info(requestWrapper.getAttr(AttrList.SUCCESS_MESSAGE));

			SlotReservation slotReservation = (SlotReservation) requestWrapper.getAttr(AttrList.SLOT_RESERVATION);

			log.info("\tReservation start time: " + slotReservation.getStart());
			log.info("\tReservation end time: " + slotReservation.getEnd());
			Double reservationCost = (Double) requestWrapper.getAttr(AttrList.RESERVATION_COST);
			if (null != reservationCost) {
				log.info("\tReservation cost: " + reservationCost + "$");
			}
		} else {
			log.info(requestWrapper.getAttr(AttrList.ERROR_MESSAGE));
		}
	}
}
