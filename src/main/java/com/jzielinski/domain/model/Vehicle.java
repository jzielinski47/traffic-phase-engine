package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;

public class Vehicle extends AbstractVehicle {

    private final int timestamp;

    public Vehicle(String id, Direction origin, Direction destination, SimulationContext context) {
        super(id, origin, destination);
        this.timestamp = context.getStep();
    }

    public Vehicle(String id, Direction origin, Direction destination, SimulationContext context, boolean isEmergency) {
        super(id, origin, destination, isEmergency);
        this.timestamp = context.getStep();
    }

    public int getTimestamp() {
        return timestamp;
    }

}
