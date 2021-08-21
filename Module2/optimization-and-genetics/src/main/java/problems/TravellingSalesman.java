/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package problems;

import java.util.*;

public class TravellingSalesman implements Problem {

    public static class StateTraveller implements State {
        public int initialCity;
        public Integer[] route = new Integer[distanceMatrix.length + 1];
        public Long[] distances = new Long[distanceMatrix.length];  // Optional
        public double totalDistance = 0;

        @Override
        public double getResult() {
            return totalDistance;
        }

        @Override
        public double getFitness() {
            // Adding +1 for a nonsense case of totalDistance equals 0..
            return 1 / (totalDistance + 1);
        }

        public void setRoute(Integer[] nRoute) {
            route[0] = route[route.length - 1] = initialCity;
            int city;
            long distance;
            for (int i = 0; i < nRoute.length; i++) {
                city = nRoute[i];
                distance = distanceMatrix[route[i]][city];
                route[i + 1] = city;
                distances[i] = distance;
                totalDistance += distance;
            }
            // Getting distance between last city and initial city
            distance = distanceMatrix[route[route.length - 2]][initialCity];
            totalDistance += distance;
            distances[distanceMatrix.length - 1] = distance;
        }

    }

    @Override
    public State generateNewState(Operators operatorType, State current) {
        StateTraveller cState = (StateTraveller) current;
        StateTraveller nState = new StateTraveller();
        nState.initialCity = (int) (Math.random() * distanceMatrix.length);

        Integer[] route = null;

        // Applying operator
        switch (operatorType) {
            case NEAR_BY:
                route = doNearByOperation(nState, cState);
                break;
            case RANDOM:
                route = doRandomOperation(nState);
                break;
        }

        nState.setRoute(route);
        return nState;
    }

    @Override
    public double getEvaluation(State current, State next) {
        return current.getResult() - next.getResult();
    }

    @Override
    public State doCrossover(State p1, State p2) {
        int numOfCities = distanceMatrix.length;
        int start = (int) (Math.random() * numOfCities);
        int end = (int) (Math.random() * (numOfCities - (start + 1))) + start + 1;

        Integer[] p1RouteSliced = ((StateTraveller) p1).route;
        p1RouteSliced = Arrays.copyOfRange(p1RouteSliced, start, end);
        Integer[] p2Route = ((StateTraveller) p2).route;

        // LinkedHashSets maintain insertion order and have no duplicates
        Set<Integer> nRouteSet = new LinkedHashSet<>(Arrays.asList(p1RouteSliced));
        nRouteSet.addAll(Arrays.asList(p2Route));

        // Removing initial city so that setRoute method works fine
        Integer initialCity = nRouteSet.iterator().next();
        nRouteSet.remove(initialCity);

        StateTraveller nState = new StateTraveller();
        nState.initialCity = initialCity;
        nState.setRoute(nRouteSet.toArray(new Integer[0]));
        return nState;
    }

    @Override
    public void doMutation(State state) {
        StateTraveller mState = (StateTraveller) state;
        int c1 = (int) (Math.random() * (mState.route.length - 1)) + 1; //Ignoring initial/final city
        int c2 = (int) (Math.random() * (mState.route.length - 1)) + 1;
        swap(mState.route, c1, c2);
    }

    private Integer[] doNearByOperation(StateTraveller nState, StateTraveller cState) {
        Integer[] route = Arrays.copyOfRange(cState.route, 1, cState.route.length - 1);
        // Replacing duplicate cities
        if (nState.initialCity != cState.initialCity) {
            for (int i = 0; i < route.length; i++)
                if (route[i] == nState.initialCity) {
                    route[i] = cState.initialCity;
                    break;
                }
        }
        swap(route, (int) (Math.random() * route.length), (int) (Math.random() * route.length));
        swap(route, (int) (Math.random() * route.length), (int) (Math.random() * route.length));
        return route;
    }

    private Integer[] doRandomOperation(StateTraveller nState) {
        Integer[] route = new Integer[distanceMatrix.length - 1];
        for (int i = 0, j = 0; i < route.length; i++, j++) {
            if (i == nState.initialCity)
                route[i] = ++j; // Skipping initial city
            else
                route[i] = j;
        }
        List<Integer> intList = Arrays.asList(route);
        Collections.shuffle(intList);
        intList.toArray(route);
        return route;
    }

    private void swap(Integer[] route, int i, int j) {
        int temp = route[i];
        route[i] = route[j];
        route[j] = temp;
    }

    public static long[][] distanceMatrix = {
            {0, 2451, 713, 1018, 1631, 1374, 2408, 213, 2571, 875, 1420, 2145, 1972},
            {2451, 0, 1745, 1524, 831, 1240, 959, 2596, 403, 1589, 1374, 357, 579},
            {713, 1745, 0, 355, 920, 803, 1737, 851, 1858, 262, 940, 1453, 1260},
            {1018, 1524, 355, 0, 700, 862, 1395, 1123, 1584, 466, 1056, 1280, 987},
            {1631, 831, 920, 700, 0, 663, 1021, 1769, 949, 796, 879, 586, 371},
            {1374, 1240, 803, 862, 663, 0, 1681, 1551, 1765, 547, 225, 887, 999},
            {2408, 959, 1737, 1395, 1021, 1681, 0, 2493, 678, 1724, 1891, 1114, 701},
            {213, 2596, 851, 1123, 1769, 1551, 2493, 0, 2699, 1038, 1605, 2300, 2099},
            {2571, 403, 1858, 1584, 949, 1765, 678, 2699, 0, 1744, 1645, 653, 600},
            {875, 1589, 262, 466, 796, 547, 1724, 1038, 1744, 0, 679, 1272, 1162},
            {1420, 1374, 940, 1056, 879, 225, 1891, 1605, 1645, 679, 0, 1017, 1200},
            {2145, 357, 1453, 1280, 586, 887, 1114, 2300, 653, 1272, 1017, 0, 504},
            {1972, 579, 1260, 987, 371, 999, 701, 2099, 600, 1162, 1200, 504, 0},
    };

}
