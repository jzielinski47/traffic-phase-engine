package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NonConflictingRoutes {

    static Map<Route, Set<Route>> nonConflictingRoutes;

    static {
        nonConflictingRoutes = new HashMap<>();

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

        // NATIVE: STRAIGHT
        nonConflictingRoutes.put(southNorth, Set.of(northSouth, southEast, southWest, northWest, westSouth));
        nonConflictingRoutes.put(northSouth, Set.of(southNorth, northWest, southEast, northEast, eastNorth));
        nonConflictingRoutes.put(westEast, Set.of(eastWest, eastNorth, westSouth, westNorth, northWest));
        nonConflictingRoutes.put(eastWest, Set.of(westEast, westSouth, eastNorth, eastSouth, southEast));

        // NATIVE: RIGHT TURN


    }

    private Set<Route> resolveNonConflictingRoutes(Route route) {

        Set<Route> routeHashSet = new HashSet<>();

        switch (route.getManeuver()) {
            case STRAIGHT:
                /* TODO: Make a set of:
                 * - Right and Left turn from this direction
                 * - Straight and Right turn from opposite direction
                 * - Right turn from direction on the left
                 * - Optional: Green Arrow for the direction from the right.
                 * */
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


}
