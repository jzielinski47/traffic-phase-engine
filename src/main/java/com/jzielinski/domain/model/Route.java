package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;
import com.jzielinski.enums.RelativeDirection;

public class Route {

    private final Direction origin;
    private final Direction destination;
    private final Maneuver maneuver;

    public Route(Direction origin, Direction destination) {
        this.origin = origin;
        this.destination = destination;
        this.maneuver = resolveManeuver(origin, destination);
    }

    private Maneuver resolveManeuver(Direction from, Direction to) {

        if (from == to) return Maneuver.U_TURN;

        return switch (from) {
            case north -> switch (to) {
                case south -> Maneuver.STRAIGHT;
                case east -> Maneuver.LEFT_TURN;
                case west -> Maneuver.RIGHT_TURN;
                default -> throw new IllegalArgumentException("Invalid direction");
            };
            case south -> switch (to) {
                case north -> Maneuver.STRAIGHT;
                case east -> Maneuver.RIGHT_TURN;
                case west -> Maneuver.LEFT_TURN;
                default -> throw new IllegalArgumentException("Invalid direction");
            };
            case east -> switch (to) {
                case west -> Maneuver.STRAIGHT;
                case north -> Maneuver.RIGHT_TURN;
                case south -> Maneuver.LEFT_TURN;
                default -> throw new IllegalArgumentException("Invalid direction");
            };
            case west -> switch (to) {
                case east -> Maneuver.STRAIGHT;
                case north -> Maneuver.LEFT_TURN;
                case south -> Maneuver.RIGHT_TURN;
                default -> throw new IllegalArgumentException("Invalid direction");
            };
        };
    }

    public Direction getOrigin() {
        return origin;
    }

    public Direction getDestination() {
        return destination;
    }

    public Maneuver getManeuver() {
        return maneuver;
    }

    enum Maneuver {
        U_TURN, STRAIGHT, RIGHT_TURN, LEFT_TURN,
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Route other)) return false;
        return origin == other.origin && destination == other.destination;
    }

    @Override
    public int hashCode() {
        return 31 * origin.hashCode() + destination.hashCode();
    }
}
