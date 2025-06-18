package com.jzielinski.core.service;

import com.jzielinski.domain.dto.StepStatus;
import com.jzielinski.domain.model.Route;
import com.jzielinski.domain.model.RouteConflictMap;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.domain.model.Vehicle;
import com.jzielinski.enums.Signal;

import java.util.HashSet;
import java.util.Set;

public class TrafficService {

    private final SimulationContext context;
    private final RouteService routeService;
    private final VehiclePriorityService vehiclePriorityService;
    private final VehicleMovementService vehicleMovementService;

    public TrafficService(SimulationContext context) {
        this.context = context;
        this.routeService = new RouteService(context);
        this.vehiclePriorityService = new VehiclePriorityService(context);
        this.vehicleMovementService = new VehicleMovementService(context);
        this.routeService.resetRoutes();
    }

    public void runStep() {

        Vehicle priorityVehicle = vehiclePriorityService.findPriorityVehicle();

        if (priorityVehicle == null) {
            System.out.println("No vehicles waiting at this step.");
            return;
        }

        routeService.configureRoutes(priorityVehicle.getRoute());

        StepStatus stepStatus = new StepStatus(vehicleMovementService.moveEligibleVehiclesAndReturnIds());
        context.addStepStatus(stepStatus);

        routeService.resetRoutes();

    }

}
