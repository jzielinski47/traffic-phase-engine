package com.jzielinski.core.service;

import com.jzielinski.domain.dto.StepStatus;
import com.jzielinski.domain.model.*;
import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TrafficService {

    private final SimulationContext context;
    private final RouteConflictMap routeConflictMap;
    private final TrafficLightService trafficLightService;

    public TrafficService(SimulationContext context) {
        this.context = context;
        routeConflictMap = new RouteConflictMap();
        trafficLightService = new TrafficLightService(context, routeConflictMap);
        trafficLightService.setAllSignals(Signal.red);
    }

    public void runSimulation() {
        Vehicle priorityVehicle = findPriorityVehicle();
        if (priorityVehicle == null) {
            System.out.println("No vehicles waiting at this step.");
            return;
        }

        Route priorityVehiclesRoute = new Route(priorityVehicle.getOrigin(), priorityVehicle.getDestination());
        Set<Route> compatibleRoutes = routeConflictMap
                .getCompatibleRoutesMap()
                .getOrDefault(priorityVehiclesRoute, Set.of());

        Set<Route> activeGreenRoutes = new HashSet<>(); // a temporary set to store all routes with active green lights
        trafficLightService.grantGreenIfCompatible(priorityVehiclesRoute);
        if (!compatibleRoutes.isEmpty()) {
            compatibleRoutes.forEach(route -> trafficLightService.grantGreenIfCompatible(route, activeGreenRoutes));
        }

        StepStatus stepStatus = new StepStatus(moveEligibleVehiclesAndReturnIds());
        context.addStepStatus(stepStatus);

        trafficLightService.setAllSignals(Signal.red);

    }

    private Vehicle findPriorityVehicle() {
        int timestamp = context.getStep();
        Vehicle selectedVehicle = null;
        Map<Direction, Road> intersection = context.getIntersection();
        for (Map.Entry<Direction, Road> entry : intersection.entrySet()) {
            ArrayList<Vehicle> queue = entry.getValue().getQueue();
            if (!queue.isEmpty()) {
                Vehicle vehicle = entry.getValue().getQueue().get(0);
                int vehicleAge = vehicle.getTimestamp();
                if (vehicleAge < timestamp) {
                    selectedVehicle = vehicle;
                    timestamp = vehicleAge;
                }
            }
        }
        return selectedVehicle;
    }

    private boolean canVehicleMove(Vehicle vehicle) {
        Route vehiclesRoute = new Route(vehicle.getOrigin(), vehicle.getDestination());
        Signal signal = context.getIntersection().get(vehicle.getOrigin()).getSignal(vehiclesRoute);
        return signal.equals(Signal.green);
    }

    private ArrayList<String> moveEligibleVehiclesAndReturnIds() {
        ArrayList<String> leftVehicles = new ArrayList<>();
        for (Map.Entry<Direction, Road> entry : context.getIntersection().entrySet()) {
            Road road = entry.getValue();
            if (!road.getQueue().isEmpty()) {
                Vehicle vehicle = road.getQueue().get(0);
                if (canVehicleMove(vehicle)) {
                    leftVehicles.add(vehicle.getId());
                    road.getQueue().remove(0);
                }
            }
        }
        return leftVehicles;
    }

}
