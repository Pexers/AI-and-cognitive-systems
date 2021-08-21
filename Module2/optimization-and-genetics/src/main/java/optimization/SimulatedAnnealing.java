/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package optimization;

import problems.Operators;
import problems.Problem;
import problems.State;

public class SimulatedAnnealing {

    private final Problem problem;
    private final double initialTemperature;
    private final double minimumTemperature;
    private final double alpha;
    public State current;
    public int time;

    public SimulatedAnnealing(Problem problem, double initialTemperature, double minimumTemperature, double alpha) {
        this.problem = problem;
        this.initialTemperature = initialTemperature;
        this.minimumTemperature = minimumTemperature;
        this.alpha = alpha;
    }

    public void runMethod() {
        current = problem.generateNewState(Operators.RANDOM, null);
        State next;
        time = 0;
        double temperature;
        double evaluation;
        while (true) {
            temperature = coolingSchedule(time);
            if (temperature <= minimumTemperature)
                return;
            next = problem.generateNewState(Operators.NEAR_BY, current);
            evaluation = problem.getEvaluation(current, next);
            if (evaluation > 0)
                current = next;
            else if (Math.exp(evaluation / temperature) > Math.random())
                current = next;
            time++;
        }
    }


    // In this type of schedule (geometrical), alpha must be close to 1. The most typical values of alpha
    // are between 0.8 and 0.99, smaller values can result in a excessively fast cooling.
    private double coolingSchedule(int currentTime) {
        return initialTemperature * Math.pow(alpha, currentTime);
    }


}
