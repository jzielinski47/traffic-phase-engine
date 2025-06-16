package com.jzielinski.core.service;

import com.jzielinski.domain.model.Road;
import com.jzielinski.domain.model.Route;
import com.jzielinski.domain.model.RouteConflictMap;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

import java.util.Map;
import java.util.Set;

public class TrafficLightService {

    private final SimulationContext context;
    private final RouteConflictMap routeConflictMap;

    public TrafficLightService(SimulationContext context, RouteConflictMap routeConflictMap) {
        this.context = context;
        this.routeConflictMap = routeConflictMap;
    }

    void setAllSignals(Signal signal) {
        Map<Direction, Road> intersection = context.getIntersection();
        for (Map.Entry<Direction, Road> entry : intersection.entrySet()) {
            entry.getValue().setAllSignals(signal);
        }
    }

    void setSignal(Route route, Signal signal) {
        Road road = context.getIntersection().get(route.getOrigin());
        if (road != null)
            road.setSignal(route, signal);
    }

    protected void grantGreenIfCompatible(Route route, Set<Route> activeGreenRoutes) {

        if (activeGreenRoutes.contains(route)) return;
        Set<Route> compatibleRoutes = routeConflictMap
                .getCompatibleRoutesMap()
                .getOrDefault(route, Set.of());

        for (Route r : activeGreenRoutes) {
            if(!compatibleRoutes.contains(r))
                return;
        }

        activeGreenRoutes.add(route);
        setSignal(route, Signal.green);
    }

    void grantGreenIfCompatible(Route _route) {
        setSignal(_route, Signal.green);
    }

}
