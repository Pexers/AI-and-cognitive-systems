/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
import controller.Adaptive;
import controller.LayerController;
import model.discrete.Direction;
import model.discrete.PositionDiscrete;
import org.junit.jupiter.api.Test;
import view.Space;

public class TestAdaptive {

    private static final Space space = new Space(1);
    private static final PositionDiscrete startingPos = new PositionDiscrete(2, 2);
    private static final double
            LEARNING_RATE = 0.3,
            DISCOUNT_FACTOR = 0.3;
    private static final int AGENT_SPEED = 20;  // Milliseconds

    @Test
    public void testAdaptive() throws InterruptedException {
        Adaptive adaptive = new Adaptive(space, startingPos, Direction.EAST, LEARNING_RATE, DISCOUNT_FACTOR);
        LayerController lc = new LayerController(AGENT_SPEED);
        lc.startIndividualLayer(adaptive);
    }

}
