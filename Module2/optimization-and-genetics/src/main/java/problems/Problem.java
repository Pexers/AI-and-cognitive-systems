package problems;

public interface Problem {

    State generateNewState(Operators operatorType, State current);

    double getEvaluation(State current, State next);

    /*--- Only used in Genetic Algorithm ---*/

    State doCrossover(State p1, State p2);

    void doMutation(State state);

}
