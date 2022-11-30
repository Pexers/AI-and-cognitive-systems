/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package controller;

import model.discrete.PositionDiscrete;

import java.awt.*;

public interface Layer {

    void takeAction();

    void moveToPosition(PositionDiscrete posToMove);

    void updateAfterAdvance();

    boolean reachedGoal();

    void moveToStartingPosition();

    void draw(Graphics g);

}
