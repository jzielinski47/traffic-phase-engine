package com.jzielinski.core.service;

import com.jzielinski.domain.model.Route;
import com.jzielinski.domain.model.RouteConflictMap;
import com.jzielinski.domain.model.SimulationContext;
import com.jzielinski.enums.Signal;

import java.util.HashSet;
import java.util.Set;

public class RouteService {

    private final RouteConflictMap routeConflictMap;
    private final TrafficLightService trafficLightService;

    public RouteService(SimulationContext context) {
        this.routeConflictMap = new RouteConflictMap();
        this.trafficLightService = new TrafficLightService(context, routeConflictMap);
    }

    public void configureRoutes(Route priorityRoute) {
        Set<Route> compatibleRoutes = routeConflictMap
                .getCompatibleRoutesMap()
                .getOrDefault(priorityRoute, Set.of());

        Set<Route> activeGreenRoutes = new HashSet<>();
        trafficLightService.grantGreenIfCompatible(priorityRoute);

        if (!compatibleRoutes.isEmpty()) {
            for(Route route : compatibleRoutes) {
                trafficLightService.grantGreenIfCompatible(route, activeGreenRoutes);
            }
        }
    }

    public void resetRoutes() {
        trafficLightService.setAllSignals(Signal.red);
    }



}
