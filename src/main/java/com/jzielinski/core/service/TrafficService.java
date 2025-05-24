package com.jzielinski.core.service;

import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

import java.util.Map;

public class TrafficService {

    private final SimulationContext context;

    public TrafficService(SimulationContext context) {
        this.context = context;

        setAllSignals(Signal.red);
    }

    private void setAllSignals(Signal signal) {
        Map<Direction, Road> intersection = context.getIntersection();
        for(Map.Entry<Direction, Road> entry : intersection.entrySet()) {
            entry.getValue().setSignal(signal);
        }
    }

    private void resolveSignal() {

    }

    private void resolvePriority() {

     }

}
