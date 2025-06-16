package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;
import com.jzielinski.enums.RelativeDirection;
import com.jzielinski.enums.Signal;

import java.util.ArrayList;
import java.util.HashMap;

import static com.jzielinski.utils.DirectionUtils.relativeTo;

public class Road {

    protected Direction origin;
    protected ArrayList<Vehicle> queue = new ArrayList<>();
    protected HashMap<Route, TrafficLight> trafficLights;

    public Road(Direction origin) {
        this.origin = origin;

        trafficLights = new HashMap<>();
        addTrafficLight(RelativeDirection.STRAIGHT);
        addTrafficLight(RelativeDirection.LEFT);
        addTrafficLight(RelativeDirection.RIGHT);

    }

    private void addTrafficLight(RelativeDirection dir) {
        Direction destination = relativeTo(this.origin, dir);
        Route route = new Route(this.origin, destination);
        TrafficLight trafficLight = new TrafficLight(route);
        trafficLight.setSignal(Signal.off);
        trafficLights.put(route, trafficLight);
    }

    public void addVehicle(Vehicle vehicle) {
        queue.add(vehicle);
    }

    public Vehicle removeVehicle() {
        Vehicle temp = queue.get(0);
        queue.remove(0);
        return temp;
    }

    public Direction getOrigin() {
        return origin;
    }

    public ArrayList<Vehicle> getQueue() {
        return queue;
    }

    public Signal getSignal(Route route) {
        TrafficLight trafficLight = trafficLights.get(route);

        if (trafficLight == null)
            throw new IllegalArgumentException("\n\nRoad " + this.origin + ", No traffic light for route: " + route.getOrigin() + " " + route.getDestination());

        return trafficLight.getSignal();
    }

    public void setSignal(Route route, Signal _signal) {
        TrafficLight signal = this.trafficLights.get(route);
        if (signal != null) signal.setSignal(_signal);
    }

    public void setAllSignals(Signal signal) {
        for (TrafficLight trafficLight : this.trafficLights.values()) {
            trafficLight.setSignal(signal);
        }
    }
}
