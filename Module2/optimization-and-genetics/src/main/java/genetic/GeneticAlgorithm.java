/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package genetic;

import problems.Operators;
import problems.Problem;
import problems.State;

public class GeneticAlgorithm {

    private final Problem problem;
    private final int populationSize;
    private final double finishCriteria;
    private final double mutationProb;
    private final int maxTime;
    public State bestState;
    public int time = 0;

    public GeneticAlgorithm(Problem problem, int populationSize, double finishCriteria, double mutationProb, int maxTime) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.finishCriteria = finishCriteria;
        this.mutationProb = mutationProb;
        this.maxTime = maxTime;
    }

    public void runMethod() {
        Generation generation = createFirstGeneration();
        State[] selectedChromosomes;
        time = 0;
        bestState = null;
        while (!finishRun(generation) && time < maxTime) {
            selectedChromosomes = selection(generation);
            generation = crossover(selectedChromosomes, generation.chromosomes);
            mutation(generation);
            time++;
        }
    }

    private Generation createFirstGeneration() {
        Generation nGeneration = new Generation(populationSize);
        for (int i = 0; i < populationSize; i++)
            nGeneration.chromosomes[i] = problem.generateNewState(Operators.RANDOM, null);
        return nGeneration;
    }

    // Roulette Wheel Selection
    private State[] selection(Generation oldGen) {
        State[] chromosomes = new State[populationSize];
        for (int i = 0; i < populationSize; i++) {
            if (Math.random() <= getSelectionProb(i, oldGen))
                chromosomes[i] = oldGen.chromosomes[i];
            else
                chromosomes[i] = problem.generateNewState(Operators.RANDOM, null);
        }
        return chromosomes;
    }

    private double getSelectionProb(int idx, Generation gen) {
        if (gen.totalFitness == 0)
            gen.calculateChromosomesFitness();
        return gen.fitnessArr[idx] / gen.totalFitness;
    }

    private Generation crossover(State[] selectedChromosomes, State[] oldGenChromosomes) {
        Generation nGeneration = new Generation(populationSize);
        for (int i = 0; i < populationSize; i++)
            nGeneration.chromosomes[i] = problem.doCrossover(selectedChromosomes[i], oldGenChromosomes[i]);
        return nGeneration;
    }

    private void mutation(Generation gen) {
        for (State chromosome : gen.chromosomes) {
            if (Math.random() <= mutationProb)
                problem.doMutation(chromosome);
        }
    }

    private boolean finishRun(Generation gen) {
        if (bestState == null)
            bestState = gen.chromosomes[0];
        for (State s : gen.chromosomes) {
            if (s.getFitness() > bestState.getFitness())
                bestState = s;
        }
        return bestState.getFitness() >= finishCriteria;
    }
}
