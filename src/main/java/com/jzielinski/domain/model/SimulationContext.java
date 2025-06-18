package com.jzielinski.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jzielinski.domain.dto.StepStatus;
import com.jzielinski.enums.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.jzielinski.Main.objectMapper;

public class SimulationContext {

    private final Map<Direction, Road> intersection;
    private final ArrayList<StepStatus> stepStatuses;
    private int step;

    public SimulationContext() {
        stepStatuses = new ArrayList<>();
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

    public ArrayList<StepStatus> getStepStatuses() {
        return stepStatuses;
    }

    public int getStep() {
        return step;
    }

    public void addStepStatus(StepStatus status) {
        stepStatuses.add(status);
    }

    public void incrementStep() {
        step++;
    }

    public void printIntersection() throws JsonProcessingException {
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(intersection));
    }


}
