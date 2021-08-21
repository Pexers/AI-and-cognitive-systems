/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package optimization;

import problems.Operators;
import problems.Problem;
import problems.State;

public class HillClimbing {

    private final Problem problem;
    private final int maxTime;
    public State current;
    public int time;

    public HillClimbing(Problem problem, int maxTime) {
        this.problem = problem;
        this.maxTime = maxTime;
    }

    public void runMethod() {
        current = problem.generateNewState(Operators.RANDOM, null);
        State next;
        time = 0;
        double evaluation;
        while (time < maxTime) {
            next = problem.generateNewState(Operators.NEAR_BY, current);
            evaluation = problem.getEvaluation(current, next);
            if (evaluation > 0)
                current = next;
            time++;
        }
    }

}
