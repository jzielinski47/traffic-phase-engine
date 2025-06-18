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
    private final RouteConflictMap routeConflictMap;
    private final TrafficLightService trafficLightService;
    private final VehiclePriorityService vehiclePriorityService;
    private final VehicleMovementService vehicleMovementService;

    public TrafficService(SimulationContext context) {
        this.context = context;
        routeConflictMap = new RouteConflictMap();
        trafficLightService = new TrafficLightService(context, routeConflictMap);
        trafficLightService.setAllSignals(Signal.red);
        vehiclePriorityService = new VehiclePriorityService(context);
        vehicleMovementService = new VehicleMovementService(context);
    }

    public void runSimulation() {

        Vehicle priorityVehicle = vehiclePriorityService.findPriorityVehicle();

        if (priorityVehicle == null) {
            System.out.println("No vehicles waiting at this step.");
            return;
        }

        Set<Route> compatibleRoutes = routeConflictMap
                .getCompatibleRoutesMap()
                .getOrDefault(priorityVehicle.getRoute(), Set.of());

        Set<Route> activeGreenRoutes = new HashSet<>(); // a temporary set to store all routes with active green lights
        trafficLightService.grantGreenIfCompatible(priorityVehicle.getRoute());
        if (!compatibleRoutes.isEmpty()) {
            for(Route route : compatibleRoutes) {
                trafficLightService.grantGreenIfCompatible(route, activeGreenRoutes);
            }
        }

        StepStatus stepStatus = new StepStatus(vehicleMovementService.moveEligibleVehiclesAndReturnIds());
        context.addStepStatus(stepStatus);

        trafficLightService.setAllSignals(Signal.red);

    }

}
