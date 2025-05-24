package com.jzielinski.core.service;

import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.domain.model.Vehicle;
import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

import java.util.ArrayList;
import java.util.Map;

public class TrafficService {

    private final SimulationContext context;

    public TrafficService(SimulationContext context) {
        this.context = context;

        setAllSignals(Signal.red);
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

    public void runSimulation() {
        Vehicle priorityVehicle = resolvePriorityVehicle();
        setSignal(priorityVehicle.getOrigin(), Signal.green);

        moveAvailableVehicles();

        System.out.println("Priority: " + resolvePriorityVehicle().getId());
    }

    private Vehicle resolvePriorityVehicle() {
        int timestamp = context.getStep();
        int queueSize = 0;
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
                if (queue.size() > queueSize) {
                    queueSize = queue.size();
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

    private final void moveVehicle(Vehicle vehicle) {
        context.getDepartedVehicles().add(vehicle);
        context.getIntersection().get(vehicle.getOrigin()).getQueue().remove(vehicle);
    }

    private final void moveAvailableVehicles() {
        for (Map.Entry<Direction, Road> entry : context.getIntersection().entrySet()) {
            Road road = entry.getValue();
            if (road.getSignal() == Signal.green && !road.getQueue().isEmpty()) {
                Vehicle vehicle = road.getQueue().get(0);
                context.getDepartedVehicles().add(vehicle);
                road.getQueue().remove(0);
            }
        }
    }

}
