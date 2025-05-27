package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;
import com.jzielinski.enums.RelativeDirection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RouteConflictMap {

    Map<Route, Set<Route>> compatibleRoutesMap;

    public RouteConflictMap() {
        compatibleRoutesMap = new HashMap<>();
        initCompatibleRoutes();
    }

    public Map<Route, Set<Route>> getCompatibleRoutesMap() {
        return compatibleRoutesMap;
    }

    private final void initCompatibleRoutes() {
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

        Set<Route> uniqueRoutes = Set.of(northEast, northSouth, northWest, eastNorth, eastWest, eastSouth, southEast, southNorth, southWest, westNorth, westEast, westSouth);
        for (Route route : uniqueRoutes) {
            compatibleRoutesMap.put(route, findCompatibleRoutes(route));
        }
    }

    private final Set<Route> findCompatibleRoutes(Route route) {

        Set<Route> compatibleRoutes = new HashSet<>();

        Direction origin = route.getOrigin();
        Direction opposite = relativeTo(origin, RelativeDirection.STRAIGHT);
        Direction left = relativeTo(origin, RelativeDirection.LEFT);
        Direction right = relativeTo(origin, RelativeDirection.RIGHT);

        switch (route.getManeuver()) {
            case STRAIGHT:
                // TODO: Add right and left turn from this direction
                compatibleRoutes.add(createRoute(origin, right));
                compatibleRoutes.add(createRoute(origin, left));

                // TODO: Add straight and right turn from opposite direction
                compatibleRoutes.add(createRoute(opposite, origin));
                compatibleRoutes.add(createRoute(opposite, relativeTo(opposite, RelativeDirection.RIGHT)));

                // TODO: Add right turn from the direction on the left
                Direction fromLeft = left;
                Direction toFromLeft = relativeTo(fromLeft, RelativeDirection.RIGHT);
                compatibleRoutes.add(createRoute(fromLeft, toFromLeft));

                // TODO: (Optional) Add green arrow from the direction on the right
                break;

            case RIGHT_TURN:
                // TODO: Add straight and left turn from this direction
                compatibleRoutes.add(createRoute(origin, opposite));
                compatibleRoutes.add(createRoute(origin, left));

                // TODO: Add straight and right turn from opposite direction
                compatibleRoutes.add(createRoute(opposite, origin));
                compatibleRoutes.add(createRoute(opposite, relativeTo(opposite, RelativeDirection.RIGHT)));

                // TODO: Add right turn from the direction on the left
                compatibleRoutes.add(createRoute(left, relativeTo(left, RelativeDirection.RIGHT)));

                // TODO: Add straight and right turn from the direction on the right
                compatibleRoutes.add(createRoute(right, left));      // STRAIGHT: right → left
                compatibleRoutes.add(createRoute(right, opposite));  // RIGHT TURN: right → opposite
                break;

            case LEFT_TURN:
                // TODO: Add straight and right turn from this direction
                compatibleRoutes.add(createRoute(origin, opposite));
                compatibleRoutes.add(createRoute(origin, right));

                // TODO: Add right turn from right and left directions
                compatibleRoutes.add(createRoute(right, relativeTo(right, RelativeDirection.RIGHT)));
                compatibleRoutes.add(createRoute(right, relativeTo(left, RelativeDirection.RIGHT)));
                break;

            case U_TURN:
                // TODO: Not implemented yet in MVP
                break;

            default:
                break;
        }

        return compatibleRoutes;
    }

    private final Direction relativeTo(Direction direction, RelativeDirection relativeDirection) {

        int move = switch (relativeDirection) {
            case LEFT -> 1;
            case STRAIGHT -> -2;
            case RIGHT -> -1;
            default -> 0;
        };
        Direction[] directions = Direction.values();
        return directions[(direction.ordinal() + move + 4) % 4];
    }

    private Route createRoute(Direction origin, Direction destination) {
        return new Route(origin, destination);
    }

}
