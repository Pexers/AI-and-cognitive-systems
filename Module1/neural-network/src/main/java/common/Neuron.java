/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package common;

public class Neuron {
    private double[] entries;
    public double[] weights;
    public double actResult;
    private double bias;
    private double[] oldIncrements; // Only used with momentum
    private double biasOldIncrement = 0; // Only used with momentum

    public void setEntries(double[] entries) {
        this.entries = entries.clone();
    }

    public void generateWeights(int numOfEntries) {
        weights = new double[numOfEntries];
        oldIncrements = new double[numOfEntries];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
        bias = Math.random();
    }

    public double getActivationInput() {
        double input = 0;
        for (int i = 0; i < entries.length; i++) {
            input += entries[i] * weights[i];
        }
        // 1 is the entry of the random bias weight
        input += 1 * bias;
        return input;
    }

    public void updateWeights(double beta) {
        double derivative;
        if (Specifications.IS_BINARY) {
            // Partial derivative of the logistic function
            derivative = actResult * (1 - actResult);
        } else {
            // Partial derivative of the tanh function
            derivative = 1 - actResult * actResult;
        }
        double incDec;
        // Updating weights
        for (int i = 0; i < entries.length; i++) {
            // If no momentum
            if (Specifications.ALPHA_WITH_MOMENTUM == 0) {
                // R * Oi * (Oj * (1-Oj)) * beta(value depends of the layer type)
                incDec = Specifications.LEARNING_RATE * entries[i] * derivative * beta;
                weights[i] -= incDec;
            } else {
                // alpha * oldIncrement - R * Oi * (Oj * (1-Oj)) * beta
                incDec = Specifications.ALPHA_WITH_MOMENTUM * oldIncrements[i] - Specifications.LEARNING_RATE * entries[i] * derivative * beta;
                weights[i] += incDec;
                oldIncrements[i] = incDec;
            }
        }
        if (Specifications.ALPHA_WITH_MOMENTUM == 0) {
            incDec = Specifications.LEARNING_RATE * 1 * derivative * beta;
            bias -= incDec;
        } else {
            incDec = Specifications.ALPHA_WITH_MOMENTUM * biasOldIncrement - Specifications.LEARNING_RATE * 1 * derivative * beta;
            bias += incDec;
            biasOldIncrement = incDec;
        }
    }

}
