package com.jzielinski.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.enums.CommandType;
import com.jzielinski.service.AddVehicleService;
import com.jzielinski.service.CommandService;
import com.jzielinski.service.StepService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimulationController {

    private final Map<CommandType, CommandService> commandServices = new HashMap<>();
    private final SimulationContext context;
    private final ArrayList<Command> commands;

    ObjectMapper om = new ObjectMapper();

    public SimulationController(ArrayList<Command> commands) {
        this.commands = commands;
        this.context = new SimulationContext();
        commandServices.put(CommandType.addVehicle, new AddVehicleService());
        commandServices.put(CommandType.step, new StepService());

    }

    public void runSimulation() {

        for (Command command : commands) {

            CommandService commandService = commandServices.get(command.getType());

            if (commandService == null) throw new RuntimeException("Command type not supported: " + command.getType());
            commandService.handle(command, context);

        }


    }


}
