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
    private final RouteConflictMap routeConflictMap = new RouteConflictMap();

    public TrafficService(SimulationContext context) {
        this.context = context;

        setAllSignals(Signal.green);
    }

    private void setAllSignals(Signal signal) {
        Map<Direction, Road> intersection = context.getIntersection();
        for (Map.Entry<Direction, Road> entry : intersection.entrySet()) {
            entry.getValue().setSignal(signal);
        }
    }

    private void setSignal(Direction direction, Signal signal) {
        Road road = context.getIntersection().get(direction);
        if (road != null)
            road.setSignal(signal);
    }

    private void setGreenSignal(Route _route, Set<Route> activeGreenRoutes) {
        if (!activeGreenRoutes.contains(_route)) {
            activeGreenRoutes.add(_route);
            setSignal(_route.getOrigin(), Signal.green);
        }
    }

    public void runSimulation() {
        Vehicle priorityVehicle = findPriorityVehicle();
        if (priorityVehicle == null) {
            System.out.println("No vehicles waiting at this step.");
            return;
        }

        Route priorityVehiclesRoute = new Route(priorityVehicle.getOrigin(), priorityVehicle.getDestination());
        Set<Route> activeGreenRoutes = new HashSet<>();

        setGreenSignal(priorityVehiclesRoute, activeGreenRoutes);
        routeConflictMap.getCompatibleRoutesMap().get(priorityVehiclesRoute).forEach(route -> {
            setGreenSignal(route, activeGreenRoutes);
        });
        setSignal(priorityVehicle.getOrigin(), Signal.green);

        StepStatus stepStatus = new StepStatus(getMovedVehicles());
        context.addStepStatus(stepStatus);

        System.out.println("Priority: " + priorityVehicle.getId());
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
        Signal signal = context.getIntersection().get(vehicle.getOrigin()).getSignal();
        return signal.equals(Signal.green);
    }

    private final ArrayList<String> getMovedVehicles() {
        ArrayList<String> leftVehicles = new ArrayList<>();
        for (Map.Entry<Direction, Road> entry : context.getIntersection().entrySet()) {
            Road road = entry.getValue();
            if (!road.getQueue().isEmpty()) {
                Vehicle vehicle = road.getQueue().get(0);
                if(canVehicleMove(vehicle)) {
                    leftVehicles.add(vehicle.getId());
                    road.getQueue().remove(0);
                }
            }
        }
        return leftVehicles;
    }

}
