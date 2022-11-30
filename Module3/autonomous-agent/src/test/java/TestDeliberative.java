/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
import controller.LayerController;
import controller.Deliberative;
import model.discrete.Direction;
import model.discrete.PositionDiscrete;
import org.junit.jupiter.api.Test;
import view.Space;

public class TestDeliberative {

    private static final Space space = new Space(1);
    private static final PositionDiscrete startingPos = new PositionDiscrete(2, 2);
    private static final double
            DISCOUNT_FACTOR = 0.3,
            MAX_DIFFERENCE = 0.005;
    private static final int
            GOAL_COUNTDOWN = 5, // Minimum number of times to reach goal
            AGENT_SPEED = 20;  // Milliseconds

    @Test
    public void testDeliberative() throws InterruptedException {
        Deliberative deliberative = new Deliberative(space, startingPos, Direction.EAST, MAX_DIFFERENCE, DISCOUNT_FACTOR, GOAL_COUNTDOWN);
        LayerController lc = new LayerController(AGENT_SPEED);
        lc.startIndividualLayer(deliberative);
    }

}
