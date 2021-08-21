/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package problems;

public interface State {

    double getResult();

    // Used only in Genetic Algorithm
    double getFitness();

}
