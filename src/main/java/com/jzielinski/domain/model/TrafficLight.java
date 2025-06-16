package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

public class TrafficLight {

    protected Route route;
    protected Signal signal = Signal.off;

    public TrafficLight(Direction origin, Direction destination) {
        this.route = new Route(origin, destination);
    }

    public TrafficLight(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }
}
