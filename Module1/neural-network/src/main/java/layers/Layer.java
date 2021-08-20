package layers;

public interface Layer {

    void sendOutputs(Layer dstLayer);

    void calculateOutputs();

    void updateWeights(Layer srcLayer);

}
