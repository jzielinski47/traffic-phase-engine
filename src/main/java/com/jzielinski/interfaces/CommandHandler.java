package com.jzielinski.interfaces;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.SimulationContext;

public interface CommandHandler {
    void handle(Command command, SimulationContext context);
}
