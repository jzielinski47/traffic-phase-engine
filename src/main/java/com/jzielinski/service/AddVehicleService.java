package com.jzielinski.service;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.domain.model.Vehicle;
import com.jzielinski.interfaces.CommandHandler;

public class AddVehicleService extends CommandService implements CommandHandler {
    @Override
    public void handle(Command command, SimulationContext context) {
        Road road = context.getRoads().get(command.getStartRoad());
        if (road != null) {
            Vehicle vehicle = new Vehicle(command.getVehicleId(), command.getStartRoad(), command.getEndRoad());
            road.addVehicle(vehicle);
        }
        context.incrementStep();
    }
}
