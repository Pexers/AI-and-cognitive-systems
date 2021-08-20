package genetic;

import org.junit.jupiter.api.Test;
import problems.Problem;
import problems.State;
import problems.TasksAssignment;

public class TasksAssignmentTest {

    Problem problem = new TasksAssignment();

    // Genetic Algorithm specifications
    final int POPULATION_SIZE = 15;
    final double FINISH_CRITERIA = 1 / (345.0 + 1); // Fitness to stop algorithm
    final double MUTATION_PROB = 0.5; // Fitness to stop algorithm
    final int MAX_TIME = 5000;


    @Test
    public void test_10_test_genetic_algorithm_executions() {
        GeneticAlgorithm ga = new GeneticAlgorithm(problem, POPULATION_SIZE, FINISH_CRITERIA, MUTATION_PROB, MAX_TIME);
        TasksAssignment.StateAssignments bestState;
        for (int i = 0; i < 10; i++) {
            ga.runMethod();
            bestState = (TasksAssignment.StateAssignments) ga.bestState;
            printResults(bestState);
            System.out.println("Total time: " + ga.time);
            System.out.println();
        }
    }

    @Test
    public void test_10_genetic_algorithm_executions_show_average() {
        GeneticAlgorithm ga = new GeneticAlgorithm(problem, POPULATION_SIZE, FINISH_CRITERIA, MUTATION_PROB, MAX_TIME);
        TasksAssignment.StateAssignments bestState;
        int costAverage = 0;
        int timeAverage = 0;
        System.out.println("Total cost | Total time");
        for (int i = 0; i < 10; i++) {
            ga.runMethod();
            bestState = (TasksAssignment.StateAssignments) ga.bestState;
            System.out.println((int) bestState.totalCost + " | " + ga.time);
            costAverage += bestState.totalCost;
            timeAverage += ga.time;
        }
        System.out.println("Cost average: " + costAverage / 10);
        System.out.println("Time average: " + timeAverage / 10);
    }

    private void printResults(State current) {
        TasksAssignment.StateAssignments lastCurrent = (TasksAssignment.StateAssignments) current;
        TasksAssignment.Assignment[] assignments = lastCurrent.assignments;
        int[] costsAssigned = lastCurrent.costsAssigned;
        for (int i = 0; i < assignments.length; i++) {
            System.out.println("Worker "
                    + assignments[i].idWorker + " assigned to task "
                    + assignments[i].idTask + ". Cost = " + costsAssigned[i]);
        }
        System.out.println("Total cost: " + (int) lastCurrent.totalCost);
    }

}
