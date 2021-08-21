/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package optimization;

import org.junit.jupiter.api.Test;
import problems.Problem;
import problems.State;
import problems.TasksAssignment;

public class TasksAssignmentTest {

    Problem problem = new TasksAssignment();

    // Stochastic Hill Climbing specifications
    final int MAX_TIME = 600;

    // Simulated Annealing specifications
    final int INITIAL_TEMPERATURE = 5000;
    final double MINIMUM_TEMPERATURE = 0.01;
    final double ALPHA = 0.95;

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
        TasksAssignment.StateAssignments lastState;
        long costAverage = 0;
        System.out.println("Cost per execution");
        for (int i = 0; i < 10; i++) {
            hc.runMethod();
            lastState = (TasksAssignment.StateAssignments) hc.current;
            System.out.println((int) lastState.totalCost);
            costAverage += lastState.totalCost;
        }
        System.out.println("Cost average: " + costAverage / 10);
        System.out.println("Total time per execution: " + hc.time);
    }

    @Test
    public void test_10_simulated_annealing_executions_show_average() {
        SimulatedAnnealing sa = new SimulatedAnnealing(problem, INITIAL_TEMPERATURE, MINIMUM_TEMPERATURE, ALPHA);
        TasksAssignment.StateAssignments lastState;
        int costAverage = 0;
        System.out.println("Cost per execution");
        for (int i = 0; i < 10; i++) {
            sa.runMethod();
            lastState = (TasksAssignment.StateAssignments) sa.current;
            System.out.println((int) lastState.totalCost);
            costAverage += lastState.totalCost;
        }
        System.out.println("Cost average: " + costAverage / 10);
        System.out.println("Total time per execution: " + sa.time);
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
        System.out.println();
    }

}
