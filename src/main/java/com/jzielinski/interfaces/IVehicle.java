package com.jzielinski.interfaces;

import com.jzielinski.enums.Direction;

public interface IVehicle {
    String getId();
    Direction getOrigin();
    Direction getDestination();
    boolean isEmergency();
}
