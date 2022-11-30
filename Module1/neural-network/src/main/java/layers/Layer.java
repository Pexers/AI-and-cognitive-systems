/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package layers;

public interface Layer {

    void sendOutputs(Layer dstLayer);

    void calculateOutputs();

    void updateWeights(Layer srcLayer);

}
