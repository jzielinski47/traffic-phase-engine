package com.jzielinski.core;

import com.jzielinski.core.handler.AddVehicleHandler;
import com.jzielinski.core.handler.CommandHandler;
import com.jzielinski.core.handler.StepHandler;
import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.dto.SimulationResult;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.enums.CommandType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimulationEngine {

    private final Map<CommandType, CommandHandler> commandHandlers = new HashMap<>();
    private final SimulationContext context;
    private final SimulationResult result;
    private final ArrayList<Command> commands;

    public SimulationEngine(ArrayList<Command> commands) {
        this.commands = commands;
        this.context = new SimulationContext();
        this.result = new SimulationResult();
        commandHandlers.put(CommandType.addVehicle, new AddVehicleHandler());
        commandHandlers.put(CommandType.step, new StepHandler());
    }

    public void runSimulation() {

        for (Command command : commands) {

            CommandHandler commandService = commandHandlers.get(command.getType());
            if (commandService == null) throw new RuntimeException("Command type not supported: " + command.getType());

            commandService.handle(command, context);

        }

        this.result.setStepStatuses(context.getStepStatuses());

    }

    public SimulationResult getResult() {
        return result;
    }
}
