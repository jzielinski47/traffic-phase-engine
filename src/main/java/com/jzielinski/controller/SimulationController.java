package com.jzielinski.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    ObjectMapper om = new ObjectMapper();


    public SimulationController(ArrayList<Command> commands) {
        this.commands = commands;
        roads.put(Direction.north, new Road(Direction.north));
        roads.put(Direction.south, new Road(Direction.south));
        roads.put(Direction.east, new Road(Direction.east));
        roads.put(Direction.west, new Road(Direction.west));
    }

    public void runSimulation() {

        commands.forEach(command -> {

            try {
                System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(command));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            switch (command.getType()) {
                case addVehicle:
                    Vehicle vehicle = new Vehicle(command.getVehicleId(), command.getStartRoad(), command.getEndRoad());
                    Road road = roads.get(command.getStartRoad());
                    Objects.requireNonNull(road).addVehicle(vehicle);

                    break;
                case step:
                    System.out.println("process step");
                    break;

            }
        });
    }




}
