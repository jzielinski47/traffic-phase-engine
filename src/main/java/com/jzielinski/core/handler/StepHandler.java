package com.jzielinski.core.handler;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.SimulationContext;

public class StepHandler extends CommandHandler {
    @Override
    public void handle(Command command, SimulationContext context) {
        context.incrementStep();
    }
}
