package com.jzielinski.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimulationContext {

    private final Map<Direction, Road> intersection;
    private final ArrayList<Vehicle> departedVehicles;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int step;

    public SimulationContext() {
        departedVehicles = new ArrayList<>();
        intersection = new HashMap<>();
        intersection.put(Direction.north, new Road(Direction.north));
        intersection.put(Direction.south, new Road(Direction.south));
        intersection.put(Direction.east, new Road(Direction.east));
        intersection.put(Direction.west, new Road(Direction.west));
        step = 0;
    }

    public Map<Direction, Road> getIntersection() {
        return intersection;
    }

    public ArrayList<Vehicle> getDepartedVehicles() {
        return departedVehicles;
    }

    public int getStep() {
        return step;
    }

    public void addDepartedVehicle(Vehicle vehicle) {
        departedVehicles.add(vehicle);
    }

    public void incrementStep(){
        step++;
    }

    public void printIntersection() throws JsonProcessingException {
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(intersection));
    }

    public boolean canVehicleMove(Vehicle vehicle) {
        Signal currentSignal = this.getIntersection().get(vehicle.getOrigin()).getSignal();
        return Signal.green.equals(currentSignal);
    }

    public void moveVehicle(Vehicle vehicle) {
        this.addDepartedVehicle(vehicle);
        this.getIntersection().get(vehicle.getOrigin()).getQueue().remove(this);
    }
}
