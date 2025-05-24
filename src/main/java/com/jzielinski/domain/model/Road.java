package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;
import com.jzielinski.enums.Signal;

import java.util.ArrayList;

public class Road {

    protected Direction id;
    protected ArrayList<Vehicle> queue = new ArrayList<>();
    protected Signal signal = Signal.off;

    public Road(Direction id) {
        this.id = id;
    }

    public void addVehicle(Vehicle vehicle){
        queue.add(vehicle);
    }

    public Vehicle removeVehicle(){
        Vehicle temp = queue.get(0);
        queue.remove(0);
        return temp;
    }

    public Direction getId() {
        return id;
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
}
