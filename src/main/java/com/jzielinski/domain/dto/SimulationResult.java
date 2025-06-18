package com.jzielinski.domain.dto;

import java.util.ArrayList;

public class SimulationResult {
    private ArrayList<StepStatus> stepStatuses;

    public SimulationResult() {
        this.stepStatuses = new ArrayList<>();
    }

    public void setStepStatuses(ArrayList<StepStatus> stepStatuses) {
        this.stepStatuses = stepStatuses;
    }
}