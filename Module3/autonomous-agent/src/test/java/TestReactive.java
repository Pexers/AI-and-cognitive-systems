/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
import controller.LayerController;
import controller.Reactive;
import model.continuous.PositionContinuous;
import org.junit.jupiter.api.Test;
import view.Space;

public class TestReactive {

    private static final Space space = new Space(0);
    private static final PositionContinuous startingPos =
            new PositionContinuous(2 * Space.SQUARE_SIZE, 8 * Space.SQUARE_SIZE);
    private static final int
            DMax_GOAL = 500,
            DMax_OBSTACLE = 120,
            AGENT_SPEED = 50;  // Milliseconds

    @Test
    public void testReactive() throws InterruptedException {
        Reactive reactive = new Reactive(space, startingPos, DMax_GOAL, DMax_OBSTACLE);
        LayerController lc = new LayerController(AGENT_SPEED);
        lc.startIndividualLayer(reactive);
    }

}
