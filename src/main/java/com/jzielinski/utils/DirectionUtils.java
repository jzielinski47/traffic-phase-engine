package com.jzielinski.utils;

import com.jzielinski.enums.Direction;
import com.jzielinski.enums.RelativeDirection;

public class DirectionUtils {

    public static Direction relativeTo(Direction direction, RelativeDirection relativeDirection) {
        int move = switch (relativeDirection) {
            case LEFT -> 1;
            case STRAIGHT -> -2;
            case RIGHT -> -1;
            default -> 0;
        };
        Direction[] directions = Direction.values();
        return directions[(direction.ordinal() + move + 4) % 4];
    }

}
