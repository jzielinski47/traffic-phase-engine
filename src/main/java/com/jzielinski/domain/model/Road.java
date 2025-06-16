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
    protected Signal signal = Signal.off;

    public Road(Direction origin) {
        this.origin = origin;

        addTrafficLight(RelativeDirection.STRAIGHT);
        addTrafficLight(RelativeDirection.LEFT);
        addTrafficLight(RelativeDirection.RIGHT);
    }

    private  void addTrafficLight (RelativeDirection dir) {
        Direction destination = relativeTo(this.origin, dir);
        Route route = new Route(this.origin, destination);
        trafficLights.put(route, new TrafficLight(route));
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

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public Signal getTrafficLight(Route route) {
        return trafficLights.get(route).getSignal();
    }

    public void setTrafficLight(Route route, Signal signal) {
        this.trafficLights.get(route).setSignal(signal);
    }

    public void setAllTrafficLights(Signal signal) {
        for(TrafficLight trafficLight : this.trafficLights.values()) {
            trafficLight.setSignal(signal);
        };
    }
}
