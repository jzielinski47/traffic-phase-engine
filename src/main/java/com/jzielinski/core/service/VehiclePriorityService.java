package com.jzielinski.core.service;

import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.RouteConflictMap;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.domain.model.Vehicle;
import com.jzielinski.enums.Direction;

import java.util.ArrayList;
import java.util.Map;

public class VehiclePriorityService {

    private final SimulationContext context;

    public VehiclePriorityService(SimulationContext context) {
        this.context = context;
    }

    protected Vehicle findPriorityVehicle() {
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
}
