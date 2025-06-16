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

    {
        routeConflictMap = new RouteConflictMap();
    }

    public TrafficService(SimulationContext context) {
        this.context = context;

        setAllSignals(Signal.green);
    }

    private void setAllSignals(Signal signal) {
        Map<Direction, Road> intersection = context.getIntersection();
        for (Map.Entry<Direction, Road> entry : intersection.entrySet()) {
            entry.getValue().setAllSignals(signal);
        }
    }

    private void setSignal(Route route, Signal signal) {
        Road road = context.getIntersection().get(route.getOrigin());
        if (road != null)
            road.setSignal(route, signal);
    }

    private boolean requestGreenSignal(Route route, Set<Route> activeGreenRoutes) {

        if (activeGreenRoutes.contains(route)) return false;
        Set<Route> compatibleRoutes = routeConflictMap
                .getCompatibleRoutesMap()
                .getOrDefault(route, Set.of());

        for (Route r : activeGreenRoutes) {
            if(!compatibleRoutes.contains(r))
                return false;
        }

        activeGreenRoutes.add(route);
        setSignal(route, Signal.green);
        return true;
    }

    private void requestGreenSignal(Route _route) {
        setSignal(_route, Signal.green);
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
        requestGreenSignal(priorityVehiclesRoute);
        if (!compatibleRoutes.isEmpty()) {
            compatibleRoutes.forEach(route -> requestGreenSignal(route, activeGreenRoutes));
        }

        StepStatus stepStatus = new StepStatus(getMovedVehicles());
        context.addStepStatus(stepStatus);

        setAllSignals(Signal.red);
//        System.out.println("Priority: " + priorityVehicle.getId());
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

    private final boolean canVehicleMove(Vehicle vehicle) {
        Route vehiclesRoute = new Route(vehicle.getOrigin(), vehicle.getDestination());
//        System.out.println("vehilce "+ vehicle.getId() + " tries to access traffic lights of " + vehicle.getOrigin() + " on direction " + vehicle.getDestination());
        Signal signal = context.getIntersection().get(vehicle.getOrigin()).getSignal(vehiclesRoute);
        return signal.equals(Signal.green);
    }

    private final ArrayList<String> getMovedVehicles() {
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
