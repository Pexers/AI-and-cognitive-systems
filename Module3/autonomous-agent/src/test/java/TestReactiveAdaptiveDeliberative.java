/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
import controller.Adaptive;
import controller.Deliberative;
import controller.LayerController;
import controller.Reactive;
import model.continuous.PositionContinuous;
import model.discrete.Direction;
import model.discrete.PositionDiscrete;
import org.junit.jupiter.api.Test;
import view.Space;

public class TestReactiveAdaptiveDeliberative {

    private static final Space space = new Space(1);
    private static final PositionDiscrete startingPosD = new PositionDiscrete(2, 2);
    private static final PositionContinuous startingPosC = new PositionContinuous(2 * Space.SQUARE_SIZE, 2 * Space.SQUARE_SIZE);
    private static final double
            LEARNING_RATE = 0.3,
            DISCOUNT_FACTOR = 0.3,
            MAX_DIFFERENCE = 0.005;
    private static final int
            DMax_GOAL = 1000,
            DMax_OBSTACLE = 40,
            GOAL_COUNTDOWN = 5, // Minimum number of times to reach goal
            AGENT_SPEED = 20;  // Milliseconds

    @Test
    public void testReactiveAdaptiveDeliberative() throws InterruptedException {
        LayerController lc = new LayerController(AGENT_SPEED);
        lc.startReactiveAdaptiveDeliberative(
                new Reactive(space, startingPosC, DMax_GOAL, DMax_OBSTACLE),
                new Adaptive(space, startingPosD, Direction.EAST, LEARNING_RATE, DISCOUNT_FACTOR),
                new Deliberative(space, startingPosD, Direction.EAST, MAX_DIFFERENCE, DISCOUNT_FACTOR, GOAL_COUNTDOWN)
        );
    }

}
