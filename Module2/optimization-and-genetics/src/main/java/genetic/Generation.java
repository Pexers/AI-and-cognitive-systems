/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package genetic;

import problems.State;

public class Generation {
    public final State[] chromosomes;
    public final double[] fitnessArr;
    public double totalFitness = 0;

    public Generation(int populationSize) {
        chromosomes = new State[populationSize];
        fitnessArr = new double[populationSize];
    }

    public void calculateChromosomesFitness() {
        double fitness;
        for (int i = 0; i < chromosomes.length; i++) {
            fitness = chromosomes[i].getFitness();
            fitnessArr[i] = fitness;
            totalFitness += fitness;
        }
    }

}
