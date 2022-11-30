/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
import controller.LayerController;
import controller.Reactive;
import controller.Adaptive;
import model.continuous.PositionContinuous;
import model.discrete.Direction;
import model.discrete.PositionDiscrete;
import org.junit.jupiter.api.Test;
import view.Space;

public class TestReactiveAdaptive {

    private static final Space space = new Space(1);
    private static final PositionContinuous startingPosC =
            new PositionContinuous(2 * Space.SQUARE_SIZE, 2 * Space.SQUARE_SIZE);
    private static final PositionDiscrete startingPosD = new PositionDiscrete(2, 2);
    private static final double
            LEARNING_RATE = 0.3,
            DISCOUNT_FACTOR = 0.3;
    private static final int
            DMax_GOAL = 1000,
            DMax_OBSTACLE = 40,
            AGENT_SPEED = 20;  // Milliseconds

    @Test
    public void testReactiveAdaptive() throws InterruptedException {
        LayerController lc = new LayerController(AGENT_SPEED);
        lc.startReactiveAdaptive(
                new Reactive(space, startingPosC, DMax_GOAL, DMax_OBSTACLE),
                new Adaptive(space, startingPosD, Direction.EAST, LEARNING_RATE, DISCOUNT_FACTOR));
    }

}
