/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package genetic;

import org.junit.jupiter.api.Test;
import problems.Problem;
import problems.State;
import problems.TravellingSalesman;

import java.util.Arrays;

public class TravellingSalesmanTest {

    Problem problem = new TravellingSalesman();

    // Genetic Algorithm specifications
    final int POPULATION_SIZE = 15;
    final double FINISH_CRITERIA = 1 / (8000.0 + 1); // Fitness to stop algorithm
    final double MUTATION_PROB = 0.5; // Fitness to stop algorithm
    final int MAX_TIME = 10000;


    @Test
    public void test_10_genetic_algorithm_executions() {
        GeneticAlgorithm ga = new GeneticAlgorithm(problem, POPULATION_SIZE, FINISH_CRITERIA, MUTATION_PROB, MAX_TIME);
        TravellingSalesman.StateTraveller bestState;
        for (int i = 0; i < 10; i++) {
            ga.runMethod();
            bestState = (TravellingSalesman.StateTraveller) ga.bestState;
            printResults(bestState);
            System.out.println("Total time: " + ga.time);
            System.out.println();
        }
    }

    @Test
    public void test_10_genetic_algorithm_executions_show_average() {
        GeneticAlgorithm ga = new GeneticAlgorithm(problem, POPULATION_SIZE, FINISH_CRITERIA, MUTATION_PROB, MAX_TIME);
        TravellingSalesman.StateTraveller bestState;
        long distanceAverage = 0;
        int timeAverage = 0;
        System.out.println("Total distance | Total time");
        for (int i = 0; i < 10; i++) {
            ga.runMethod();
            bestState = (TravellingSalesman.StateTraveller) ga.bestState;
            System.out.println((long) bestState.totalDistance + " | "+ga.time);
            distanceAverage += bestState.totalDistance;
            timeAverage += ga.time;
        }
        System.out.println("Distance average: " + distanceAverage / 10);
        System.out.println("Time average: " + timeAverage / 10);
    }

    private void printResults(State bestState) {
        TravellingSalesman.StateTraveller bState = (TravellingSalesman.StateTraveller) bestState;
        Integer[] finalRoute = bState.route;
        System.out.print("Route: ");
        for (int i = 0; i < finalRoute.length - 1; i++)
            System.out.print(finalRoute[i] + "->");
        System.out.println(finalRoute[finalRoute.length - 1]);
        System.out.println("Distances: " + Arrays.toString(bState.distances));
        System.out.println("Total distance: " + (long) bState.totalDistance);
    }

}
