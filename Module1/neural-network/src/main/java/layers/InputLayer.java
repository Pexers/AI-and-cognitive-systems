/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package layers;

import common.Neuron;

public class InputLayer implements Layer {
    public double[] entries;

    public void setEntries(double[] entries) {
        this.entries = entries.clone();
    }

    @Override
    public void sendOutputs(Layer dstLayer) {
        HiddenLayer hiddenLayer = (HiddenLayer) dstLayer;
        for (Neuron h : hiddenLayer.hiddenNeurons) {
            h.setEntries(entries);
        }
    }

    @Override
    public void calculateOutputs() {
        ;
    }

    @Override
    public void updateWeights(Layer srcLayer) {
        ;
    }

}
