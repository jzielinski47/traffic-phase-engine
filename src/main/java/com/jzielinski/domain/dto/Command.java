package com.jzielinski.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jzielinski.enums.CommandType;
import com.jzielinski.enums.Direction;

public class Command {
    protected CommandType type;
    protected String vehicleId;
    protected Direction startRoad;
    protected Direction endRoad;

    @JsonCreator
    public Command(
            @JsonProperty("type") CommandType type,
            @JsonProperty("vehicleId") String vehicleId,
            @JsonProperty("startRoad") Direction startRoad,
            @JsonProperty("endRoad") Direction endRoad
    ) {
        this.type = type;
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Direction getStartRoad() {
        return startRoad;
    }

    public void setStartRoad(Direction startRoad) {
        this.startRoad = startRoad;
    }

    public Direction getEndRoad() {
        return endRoad;
    }

    public void setEndRoad(Direction endRoad) {
        this.endRoad = endRoad;
    }

    public void validate() {
        switch (type) {
            case addVehicle -> {
                if (vehicleId == null || startRoad == null || endRoad == null) {
                    throw new IllegalArgumentException("ADD_VEHICLE requires vehicleId, startRoad, and endRoad.");
                }
                break;
            }
            case step -> {
                break;
            }
            default -> throw new IllegalArgumentException("Command type not supported: " + type);
        }
    }

}
