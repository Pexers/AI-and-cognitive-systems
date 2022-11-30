/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package problems;

public interface Problem {

    State generateNewState(Operators operatorType, State current);

    double getEvaluation(State current, State next);

    State doCrossover(State p1, State p2);  // Only used in Genetic Algorithm

    void doMutation(State state);

}
