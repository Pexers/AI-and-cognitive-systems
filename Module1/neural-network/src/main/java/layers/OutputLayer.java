/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package layers;

import common.Neuron;
import common.Operations;
import common.Specifications;

import java.text.DecimalFormat;

public class OutputLayer implements Layer {

    public Neuron[] outputNeurons;
    public double[] targets;

    public OutputLayer(int numOfNeurons, int numOfEntries) {
        outputNeurons = new Neuron[numOfNeurons];
        for (int i = 0; i < outputNeurons.length; i++) {
            outputNeurons[i] = new Neuron();
            outputNeurons[i].generateWeights(numOfEntries);
        }
    }

    @Override
    public void sendOutputs(Layer dstLayer) {;}

    @Override
    public void calculateOutputs() {
        if (Specifications.IS_BINARY) {
            for (Neuron o : outputNeurons) {
                // Logistic function
                o.actResult = Operations.logisticActivation(o.getActivationInput());
            }
        } else {
            for (Neuron o : outputNeurons) {
                // Tanh function
                o.actResult = Operations.tahnActivation(o.getActivationInput());
            }
        }
    }

    @Override
    public void updateWeights(Layer srcLayer) {
        double beta;
        Neuron o;
        for (int i = 0; i < outputNeurons.length; i++) {
            o = outputNeurons[i];
            beta = o.actResult - targets[i];
            o.updateWeights(beta);
        }
    }

    public void setTargets(double[] targets){
        this.targets = targets.clone();
    }

    public double calculateNeuronsError() {
        DecimalFormat df = new DecimalFormat("0.00");
        double totalError = 0;
        for (int i = 0; i < outputNeurons.length; i++) {
            totalError += Math.pow(targets[i] - outputNeurons[i].actResult, 2);
        }
        return Double.parseDouble(df.format(totalError));
    }
}
