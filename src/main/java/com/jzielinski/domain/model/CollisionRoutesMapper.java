package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CollisionRoutesMapper {

    private static final Map<Route, Set<Route>> COLLISION_ROUTES;

    static {
        COLLISION_ROUTES = new HashMap<>();

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

        // STRAIGHT MANOEUVRES
        COLLISION_ROUTES.put(southNorth, Set.of(eastWest, westEast, northEast, westNorth, eastNorth, eastSouth)); // eastNorth: optional as greenArrow

    }

    public static Map<Route, Set<Route>> getCollisionRoutes() {
        return COLLISION_ROUTES;
    }


}
