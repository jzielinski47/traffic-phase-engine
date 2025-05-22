package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;
import com.jzielinski.interfaces.IVehicle;

public abstract class AbstractVehicle implements IVehicle {
    protected final String id;
    protected final Direction origin;
    protected final Direction destination;
    protected final boolean isEmergency;

    public AbstractVehicle(String id, Direction origin, Direction destination) {
        this(id, origin, destination, false);
    }

    public AbstractVehicle(String id, Direction origin, Direction destination, boolean isEmergency) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.isEmergency = isEmergency;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Direction getOrigin() {
        return origin;
    }

    @Override
    public Direction getDestination() {
        return destination;
    }

    @Override
    public boolean isEmergency() {
        return isEmergency;
    }

}
