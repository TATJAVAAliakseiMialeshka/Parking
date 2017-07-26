package com.epam.ta.parking.service.factory;

import com.epam.ta.parking.service.CostService;
import com.epam.ta.parking.service.SlotService;
import com.epam.ta.parking.service.VehicleService;
import com.epam.ta.parking.service.impl.CostServiceImpl;
import com.epam.ta.parking.service.impl.SlotServiceImpl;
import com.epam.ta.parking.service.impl.VehicleServiceImpl;

public final class ServiceFactory {

	private static ServiceFactory instance;
	private final VehicleService vehicleService = new VehicleServiceImpl();
	private final SlotService slotService = new SlotServiceImpl();
	private final CostService costService = new CostServiceImpl();
	
	private ServiceFactory (){
		super();
	}
	
	public static synchronized ServiceFactory getInstance(){
		if(null==instance){
			instance = new ServiceFactory();
		}
		return instance;
	}

	public VehicleService getVehicleService() {
		return vehicleService;
	}

	public SlotService getSlotService() {
		return slotService;
	}

	public CostService getCostService() {
		return costService;
	}

	
}
