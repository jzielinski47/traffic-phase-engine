package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NonConflictingRoutes {

    static Map<Route, HashSet<Route>> nonConflictingRoutes;

    static {

        Route northEast = new Route(Direction.north, Direction.east);
        Route northSouth = new Route(Direction.north, Direction.south);
        Route northWest = new Route(Direction.north, Direction.west);
        Route eastNorth = new Route(Direction.east, Direction.north);
        Route eastWest = new Route(Direction.east, Direction.west);
        Route eastSouth = new Route(Direction.east, Direction.south);
        Route southEast = new Route(Direction.south, Direction.east);
        Route southNorth = new Route(Direction.south, Direction.north);
        Route southWest = new Route(Direction.south, Direction.west);
        Route westNorth = new Route(Direction.west, Direction.north);
        Route westEast = new Route(Direction.west, Direction.east);
        Route westSouth = new Route(Direction.west, Direction.south);


    }

    private Set<Route> resolveNonConflictingRoutes(Route route) {

        Set<Route> routeHashSet = new HashSet<>();

        Direction oppositeDirection = resolveRelativeDirection(route.getOrigin(), RelativeDirection.STRAIGHT);
        Direction directionLeft = resolveRelativeDirection(route.getOrigin(), RelativeDirection.LEFT);
        Direction directionRight = resolveRelativeDirection(route.getOrigin(), RelativeDirection.RIGHT);

        switch (route.getManeuver()) {
            case STRAIGHT:

                // TODO: Right and Left turn from this direction
                Route rightTurn = new Route(route.getOrigin(), directionRight);
                Route leftTurn = new Route(route.getOrigin(), directionLeft);

                // TODO: Straight and Right turn from opposite direction
                Route oppositeStraight = new Route(oppositeDirection, route.getOrigin());
                Route oppositeRightTurn = new Route(oppositeDirection, resolveRelativeDirection(oppositeDirection, RelativeDirection.RIGHT));

                // TODO: Right turn from direction on the left
                Route leftRightTurn = new Route(directionLeft, resolveRelativeDirection(directionLeft, RelativeDirection.RIGHT));

                // TODO: Optional: Green Arrow for the direction on the right

                routeHashSet.add(rightTurn);
                routeHashSet.add(leftTurn);
                routeHashSet.add(oppositeStraight);
                routeHashSet.add(oppositeRightTurn);
                routeHashSet.add(leftRightTurn);

                break;
            case RIGHT_TURN:
                /* TODO: Make a set of:
                 * - STRAIGHT and Left turn from this direction
                 * - Straight and Right turn from opposite direction
                 * - Right turn from direction on the left
                 * - STRAIGHT and Right turn from direction on the right
                 * - as it only collides with the crossing direction taking the right lane
                 *
                 * so to conclude all except for the straight from the left direction's STRAIGHT
                 * */


                break;
            case LEFT_TURN:
                /* TODO: Make a set of:
                 * - STRAIGHT and Right from this direction
                 * - RIGHT turn from right and left direction
                 * */
                break;
            case U_TURN:
                /* TODO: block all traffic except for :
                 - the left, right, and straight turn from this direction
                 - optional: right turn from right direction
                 - optional: u-turn from opposite direction:
                 this case is additional. Not to be implemented in MVP.
                 */
                break;
            default:
                break;
        }

        return routeHashSet;

    }

    private enum RelativeDirection {
        LEFT, STRAIGHT, RIGHT
    }

    private final Direction resolveRelativeDirection(Direction direction, RelativeDirection relativeDirection) {

        int move = switch (relativeDirection) {
            case LEFT -> 1;
            case STRAIGHT -> -2;
            case RIGHT -> -1;
            default -> 0;
        };

        return Direction.values()[(direction.ordinal() + move + 4) % 4];
    }


}
