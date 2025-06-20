package com.jzielinski.core.commandHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jzielinski.core.service.TrafficService;
import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.SimulationContext;

public class StepHandler extends CommandHandler {

    @Override
    public void handle(Command command, SimulationContext context) {

        TrafficService trafficService = new TrafficService(context);
        trafficService.runStep();

        try {
            System.out.println("Step: " + context.getStep());
            context.printIntersection();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        context.incrementStep();
    }

}
