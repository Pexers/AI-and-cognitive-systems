/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package layers;

public interface Layer {

    void sendOutputs(Layer dstLayer);

    void calculateOutputs();

    void updateWeights(Layer srcLayer);

}
