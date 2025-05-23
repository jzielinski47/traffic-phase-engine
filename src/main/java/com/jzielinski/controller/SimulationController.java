package com.jzielinski.controller;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.Vehicle;
import com.jzielinski.enums.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimulationController {


    Map<Direction, Road> roads = new HashMap<>();
    ArrayList<Command> commands;

    public SimulationController(ArrayList<Command> commands) {
        this.commands = commands;
        roads.put(Direction.north, new Road(Direction.north));
        roads.put(Direction.south, new Road(Direction.south));
        roads.put(Direction.east, new Road(Direction.east));
        roads.put(Direction.west, new Road(Direction.west));
    }

    public void runSimulation() {
        commands.forEach(command -> {
            switch (command.getType()) {
                case addVehicle:
                    Vehicle vehicle = new Vehicle(command.getVehicleId(), command.getStartRoad(), command.getEndRoad());
                    Road road = roads.get(command.getStartRoad());
                    Objects.requireNonNull(road).addVehicle(vehicle);
                    System.out.println("process addVehicle " + vehicle.getId());
                    break;
                case step:
                    System.out.println("process step");
                    break;

            }
        });
    }




}
