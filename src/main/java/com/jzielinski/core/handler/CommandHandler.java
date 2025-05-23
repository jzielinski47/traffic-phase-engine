package com.jzielinski.core.handler;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.SimulationContext;

public abstract class CommandHandler implements com.jzielinski.interfaces.CommandHandler {

    @Override
    public abstract void handle(Command command, SimulationContext context);

}
