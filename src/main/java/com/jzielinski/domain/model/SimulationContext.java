package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimulationContext {

    private final Map<Direction, Road> roads;
    private final ArrayList<Vehicle> departedVehicles;
    private int step;

    public SimulationContext() {
        departedVehicles = new ArrayList<>();
        roads = new HashMap<>();
        roads.put(Direction.north, new Road(Direction.north));
        roads.put(Direction.south, new Road(Direction.south));
        roads.put(Direction.east, new Road(Direction.east));
        roads.put(Direction.west, new Road(Direction.west));
        step = 0;
    }

    public Map<Direction, Road> getRoads() {
        return roads;
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
}
