package optimization;

import org.junit.jupiter.api.Test;
import problems.Problem;
import problems.State;
import problems.TravellingSalesman;

import java.util.Arrays;

public class TravellingSalesmanTest {

    Problem problem = new TravellingSalesman();

    // Stochastic Hill Climbing specifications
    final int MAX_TIME = 600;

    // Simulated Annealing specifications
    final int INITIAL_TEMPERATURE = 5000;
    final double MINIMUM_TEMPERATURE = 0.01;
    final double ALPHA = 0.98;

    @Test
    public void test_10_hill_climbing_executions() {
        HillClimbing hc = new HillClimbing(problem, MAX_TIME);
        for (int i = 0; i < 10; i++) {
            hc.runMethod();
            printResults(hc.current);
        }
        System.out.println("Total time per execution: " + hc.time);
    }

    @Test
    public void test_10_simulated_annealing_executions() {
        SimulatedAnnealing sa = new SimulatedAnnealing(problem, INITIAL_TEMPERATURE, MINIMUM_TEMPERATURE, ALPHA);
        for (int i = 0; i < 10; i++) {
            sa.runMethod();
            printResults(sa.current);
        }
        System.out.println("Total time per execution: " + sa.time);
    }

    @Test
    public void test_10_hill_climbing_executions_show_average() {
        HillClimbing hc = new HillClimbing(problem, MAX_TIME);
        TravellingSalesman.StateTraveller lastState;
        long distanceAverage = 0;
        System.out.println("Distance per execution");
        for (int i = 0; i < 10; i++) {
            hc.runMethod();
            lastState = (TravellingSalesman.StateTraveller) hc.current;
            System.out.println((int) lastState.totalDistance);
            distanceAverage += lastState.totalDistance;
        }
        System.out.println("Distance average: " + distanceAverage / 10);
        System.out.println("Total time per execution: " + hc.time);
    }

    @Test
    public void test_10_simulated_annealing_executions_show_average() {
        SimulatedAnnealing sa = new SimulatedAnnealing(problem, INITIAL_TEMPERATURE, MINIMUM_TEMPERATURE, ALPHA);
        TravellingSalesman.StateTraveller lastState;
        long distanceAverage = 0;
        System.out.println("Distance per execution");
        for (int i = 0; i < 10; i++) {
            sa.runMethod();
            lastState = (TravellingSalesman.StateTraveller) sa.current;
            System.out.println((int) lastState.totalDistance);
            distanceAverage += lastState.totalDistance;
        }
        System.out.println("Distance average: " + distanceAverage / 10);
        System.out.println("Total time per execution: " + sa.time);
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
        System.out.println();
    }

}
