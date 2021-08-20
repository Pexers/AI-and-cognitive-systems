
import common.Specifications;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Arrays;

public class NetworkTestingImages {

    private NeuralNetwork net;
    // Network specifications
    final int NUMBER_OF_INPUT_NEURONS = 16;
    final int NUMBER_OF_HIDDEN_NEURONS = 2;
    final int NUMBER_OF_OUTPUT_NEURONS = 2;
    final double LEARNING_RATE = 0.5;
    final double ERROR_TO_STOP = 0.1;
    final int MAX_ITERATIONS = 10000;
    final double ALPHA_WITH_MOMENTUM = 0;  // Zero if no momentum
    final boolean IS_BINARY = true;

    private static class EntryTarget {
        double[] entries;
        double[] targets;

        public EntryTarget(double[] e, double[] t) {
            entries = e;
            targets = t;
        }
    }

    @Test
    public void test_network() {
        doTraining();
        EntryTarget test1 = new EntryTarget(new double[]{
                1, 1, 1, 1,
                1, 0, 0, 1,
                0, 0, 0, 0,
                0, 0, 0, 0}, new double[]{1, 0});
        EntryTarget test2 = new EntryTarget(new double[]{
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 1, 1, 0,
                1, 0, 0, 1}, new double[]{0, 1});
        EntryTarget test3 = new EntryTarget(new double[]{
                0, 1, 1, 0,
                0, 0, 1, 0,
                0, 0, 1, 0,
                0, 0, 0, 0}, new double[]{1, 1});
        EntryTarget test4 = new EntryTarget(new double[]{
                1, 1, 0, 0,
                1, 0, 0, 0,
                1, 0, 0, 0,
                1, 1, 0, 0}, new double[]{1, 0});
        EntryTarget test5 = new EntryTarget(new double[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1}, new double[]{0, 1});
        EntryTarget test6 = new EntryTarget(new double[]{
                0, 0, 0, 0,
                0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 1, 0}, new double[]{1, 1});

        EntryTarget[] testers = new EntryTarget[]{test1, test2, test3, test4, test5, test6};
        System.out.println("-----------Testing-----------");
        for (EntryTarget et : testers)
            doTesting(et.entries, et.targets);
        System.out.println();
    }

    @Test
    public void calculate_averages_10_trainings() {
        double errorAverage = 0;
        int iterationAverage = 0;
        for (int i = 0; i < 10; i++) {
            doTraining();
            double error = net.currentError;
            int iteration = net.iteration;
            errorAverage += error;
            iterationAverage += iteration;
        }
        DecimalFormat df = new DecimalFormat("0.000");
        System.out.println("Error average: " + df.format(errorAverage / 10));
        System.out.println("Iteration average: " + iterationAverage / 10);
        System.out.println();
    }

    private void doTraining() {

        net = new NeuralNetwork(NUMBER_OF_INPUT_NEURONS, NUMBER_OF_HIDDEN_NEURONS, NUMBER_OF_OUTPUT_NEURONS);
        Specifications.LEARNING_RATE = LEARNING_RATE;
        Specifications.ERROR_TO_STOP = ERROR_TO_STOP;
        Specifications.ALPHA_WITH_MOMENTUM = ALPHA_WITH_MOMENTUM;
        Specifications.IS_BINARY = IS_BINARY;
        Specifications.MAX_ITERATIONS = MAX_ITERATIONS;

        boolean one, two, three;
        one = two = three = true;
        DecimalFormat df = new DecimalFormat("0.00");

        EntryTarget train1 = new EntryTarget(new double[]{
                1, 1, 1, 1,
                1, 0, 0, 1,
                1, 0, 0, 1,
                1, 1, 1, 1}, new double[]{1, 0});
        EntryTarget train2 = new EntryTarget(new double[]{
                1, 0, 0, 1,
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 0, 0, 1}, new double[]{0, 1});
        EntryTarget train3 = new EntryTarget(new double[]{
                0, 1, 1, 0,
                0, 1, 1, 0,
                0, 1, 1, 0,
                0, 1, 1, 0}, new double[]{1, 1});

        System.out.println("Training ...");
        while (one || two || three) {
            net.setEntriesAndTargets(train1.entries, train1.targets);
            one = net.trainNetwork();
            net.setEntriesAndTargets(train2.entries, train2.targets);
            two = net.trainNetwork();
            net.setEntriesAndTargets(train3.entries, train3.targets);
            three = net.trainNetwork();
        }
        System.out.println("Total Error: " + df.format(net.currentError) + " | " + "Total Iterations: " + net.iteration);
    }

    private void doTesting(double[] newEntries, double[] newTargets) {
        System.out.println("Entries: " + Arrays.toString(newEntries) + " | Targets: " + Arrays.toString(newTargets));
        net.setEntriesAndTargets(newEntries, newTargets);
        String results = Arrays.toString(net.testNetwork());
        System.out.println("Results: " + results);
        System.out.println("----------------------");
    }

}
