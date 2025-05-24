package com.jzielinski.domain.dto;

import java.util.ArrayList;

public class StepStatus {
    private ArrayList<String> leftVehicles;

    public StepStatus(ArrayList<String> leftVehicles) {
        this.leftVehicles = leftVehicles;
    }

    public ArrayList<String> getLeftVehicles() {
        return leftVehicles;
    }

    public void setLeftVehicles(ArrayList<String> leftVehicles) {
        this.leftVehicles = leftVehicles;
    }
}
