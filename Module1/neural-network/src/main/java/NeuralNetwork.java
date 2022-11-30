/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
import common.Specifications;
import layers.HiddenLayer;
import layers.InputLayer;
import layers.OutputLayer;

public class NeuralNetwork {

    private final InputLayer inputLayer;
    private final HiddenLayer hiddenLayer;
    private final OutputLayer outputLayer;
    public int iteration = 0;
    public double currentError;

    public NeuralNetwork(int numOfInputs, int numOfHiddens, int numOfOutputs) {
        inputLayer = new InputLayer();
        hiddenLayer = new HiddenLayer(numOfHiddens, numOfInputs);
        outputLayer = new OutputLayer(numOfOutputs, numOfHiddens);
    }

    public boolean trainNetwork() {
        forwardPass();
        // Verifying if it should continue
        currentError = outputLayer.calculateNeuronsError();
        if (currentError <= Specifications.ERROR_TO_STOP && currentError >= 0 || currentError >= -Specifications.ERROR_TO_STOP && currentError <= 0)
            return false;
        backwardPass();
        return ++iteration < Specifications.MAX_ITERATIONS;
    }

    public double[] testNetwork() {
        forwardPass();
        double[] results = new double[outputLayer.outputNeurons.length];
        for (int i = 0; i < outputLayer.outputNeurons.length; i++) {
            results[i] = outputLayer.outputNeurons[i].actResult;
        }
        return results;
    }

    public void setEntriesAndTargets(double[] entries, double[] targets) {
        inputLayer.setEntries(entries);
        outputLayer.setTargets(targets);
    }

    private void forwardPass() {
        inputLayer.sendOutputs(hiddenLayer);
        hiddenLayer.calculateOutputs();
        hiddenLayer.sendOutputs(outputLayer);
        outputLayer.calculateOutputs();
    }

    private void backwardPass() {
        outputLayer.updateWeights(null);
        hiddenLayer.updateWeights(outputLayer);
    }

}
