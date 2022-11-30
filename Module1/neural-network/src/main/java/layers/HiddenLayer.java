/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package layers;

import common.Neuron;
import common.Operations;
import common.Specifications;

public class HiddenLayer implements Layer {

    public Neuron[] hiddenNeurons;

    public HiddenLayer(int numOfNeurons, int numOfEntries) {
        hiddenNeurons = new Neuron[numOfNeurons];
        for (int i = 0; i < hiddenNeurons.length; i++) {
            hiddenNeurons[i] = new Neuron();
            hiddenNeurons[i].generateWeights(numOfEntries);
        }
    }

    @Override
    public void sendOutputs(Layer dstLayer) {
        OutputLayer outputLayer = (OutputLayer) dstLayer;
        double[] actResults = new double[hiddenNeurons.length];
        for (int i = 0; i < hiddenNeurons.length; i++) {
            actResults[i] = hiddenNeurons[i].actResult;
        }
        for (Neuron o : outputLayer.outputNeurons) {
            o.setEntries(actResults);
        }
    }

    @Override
    public void calculateOutputs() {
        if (Specifications.IS_BINARY) {
            for (Neuron h : hiddenNeurons) {
                // Logistic function
                h.actResult = Operations.logisticActivation(h.getActivationInput());
            }
        } else {
            for (Neuron h : hiddenNeurons) {
                // Tanh function
                h.actResult = Operations.tahnActivation(h.getActivationInput());
            }
        }
    }

    @Override
    public void updateWeights(Layer srcLayer) {
        OutputLayer outputLayer = (OutputLayer) srcLayer;
        double beta, outputBeta, derivative;
        Neuron o;
        for (int i = 0; i < hiddenNeurons.length; i++) {
            // We have to take into account that the output of each HiddenNeuron
            // contributes to the output (and therefore error) of multiple OutputNeurons
            beta = 0;
            for (int j = 0; j < outputLayer.outputNeurons.length; j++) {
                o = outputLayer.outputNeurons[j];
                outputBeta = o.actResult - outputLayer.targets[j];
                if (Specifications.IS_BINARY) {
                    // Partial derivative of the logistic function
                    derivative = o.actResult * (1 - o.actResult);
                } else {
                    // Partial derivative of the tanh function
                    derivative = 1 - o.actResult * o.actResult;
                }
                beta += o.weights[i] * derivative * outputBeta;
            }
            hiddenNeurons[i].updateWeights(beta);
        }
    }

}
