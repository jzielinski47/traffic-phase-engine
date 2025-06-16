package com.jzielinski.core.handler;

import com.jzielinski.domain.dto.Command;
import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.domain.model.Vehicle;

public class AddVehicleHandler extends CommandHandler {
    @Override
    public void handle(Command command, SimulationContext context) {
        Road road = context.getIntersection().get(command.getStartRoad());
        if (road != null) {
            Vehicle vehicle = new Vehicle(command.getVehicleId(), command.getStartRoad(), command.getEndRoad(), context);
            road.addVehicle(vehicle);
            System.out.println("A new vehicle: " + command.getVehicleId() + " has been added to road: " + context.getIntersection().get(command.getStartRoad()).getOrigin());
        }
        context.incrementStep();
    }
}
