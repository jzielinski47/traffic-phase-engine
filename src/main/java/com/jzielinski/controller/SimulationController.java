package com.jzielinski.controller;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.Vehicle;
import com.jzielinski.enums.Direction;

import java.util.ArrayList;
import java.util.Objects;

public class SimulationController {

    protected Road northRoad = new Road(Direction.north) {
    };
    protected Road eastRoad = new Road(Direction.east) {
    };
    protected Road southRoad = new Road(Direction.south) {
    };
    protected Road westRoad = new Road(Direction.west) {
    };

    ArrayList<Command> commands;

    public SimulationController(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void runSimulation() {
        commands.forEach(command -> {
            switch (command.getType()) {
                case addVehicle:
                    Road road = resolveRoad(command.getStartRoad());
                    Vehicle vehicle = new Vehicle(command.getVehicleId(), command.getStartRoad(), command.getEndRoad());
                    Objects.requireNonNull(road).addVehicle(vehicle);
                    System.out.println("process addVehicle " + vehicle.getId());
                    break;
                case step:
                    System.out.println("process step");
                    break;

            }
        });
    }

    private Road resolveRoad(Direction dir) {
        switch (dir) {
            case north:
                return northRoad;
            case east:
                return eastRoad;
            case south:
                return southRoad;
            case west:
                return westRoad;
            default:
                return null;
        }
    }


}
