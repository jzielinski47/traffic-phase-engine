package com.jzielinski.service;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.interfaces.CommandHandler;

public class StepService extends CommandService implements CommandHandler {
    @Override
    public void handle(Command command, SimulationContext context) {
        context.incrementStep();
    }
}
