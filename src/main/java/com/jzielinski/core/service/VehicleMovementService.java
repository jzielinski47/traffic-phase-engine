package com.jzielinski.core.service;

import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.domain.model.Vehicle;
import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

import java.util.ArrayList;
import java.util.Map;

public class VehicleMovementService {

    private final SimulationContext context;

    public VehicleMovementService(SimulationContext context) {
        this.context = context;
    }

    private boolean canVehicleMove(Vehicle vehicle) {
        Signal signal = context.getIntersection().get(vehicle.getOrigin()).getSignal(vehicle.getRoute());
        return signal.equals(Signal.green);
    }

    protected ArrayList<String> moveEligibleVehiclesAndReturnIds() {
        ArrayList<String> leftVehicles = new ArrayList<>();
        for (Map.Entry<Direction, Road> directionRoadEntry : context.getIntersection().entrySet()) {
            Road road = directionRoadEntry.getValue();
            if (road.getQueue().isEmpty()) continue;
            Vehicle vehicle = road.getQueue().get(0);
            if (canVehicleMove(vehicle)) {
                leftVehicles.add(vehicle.getId());
                road.getQueue().remove(0);
            }
        }
        return leftVehicles;
    }

}
