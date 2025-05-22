package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;

public class Vehicle extends AbstractVehicle {

    public Vehicle(String id, Direction origin, Direction destination) {
        super(id, origin, destination);
    }

    public Vehicle(String id, Direction origin, Direction destination, boolean isEmergency) {
        super(id, origin, destination, isEmergency);
    }




}
