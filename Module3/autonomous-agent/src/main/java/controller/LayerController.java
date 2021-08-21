/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package controller;

import model.continuous.Vector;
import model.discrete.Agent;
import model.discrete.Direction;
import model.discrete.PositionDiscrete;
import view.Panel;
import view.Space;

public class LayerController {

    public final int speed;

    public LayerController(int speed) {
        this.speed = speed;
    }

    public void startIndividualLayer(Layer layer) throws InterruptedException {
        Panel panel = new Panel(layer);
        while (panel.isShowing()) {
            layer.takeAction();
            if (layer.reachedGoal())
                layer.moveToStartingPosition();
            panel.repaint();
            Thread.sleep(speed);
        }
    }

    public void startReactiveAdaptive(Reactive reactive, Adaptive adaptive) throws InterruptedException {
        Panel panel = new Panel(reactive);
        Agent adaptAgent = adaptive.agent;
        Vector reactAgent = reactive.agentVec;
        PositionDiscrete posToMoveD = new PositionDiscrete(0, 0);
        while (panel.isShowing()) {
            if (adaptive.hasKnowledge()) {
                /** ADAPTIVE ACTION **/
                adaptive.takeAction();
                posToMoveD.x = adaptAgent.position.x;
                posToMoveD.y = adaptAgent.position.y;
                reactive.moveToPosition(posToMoveD);
            } else {
                /** REACTIVE ACTION **/
                reactive.takeAction();
                posToMoveD.x = Space.convertToDiscrete(reactAgent.start.x);
                posToMoveD.y = Space.convertToDiscrete(reactAgent.start.y);
                // Update direction of discrete based on agent vector
                adaptAgent.direction = Direction.convertAngleToDirection(reactAgent.angle);
                // Update if discrete position changes
                if (posToMoveD.x != adaptAgent.position.x || posToMoveD.y != adaptAgent.position.y)
                    adaptive.moveToPosition(posToMoveD);
            }
            if (adaptive.reachedGoal()) {
                reactive.moveToStartingPosition();
                adaptive.moveToStartingPosition();
            }
            panel.repaint();
            Thread.sleep(speed);
        }
    }

    public void startReactiveAdaptiveDeliberative(Reactive reactive, Adaptive adaptive, Deliberative deliberative) throws InterruptedException {
        Panel panel = new Panel(reactive);
        Agent adaptAgent = adaptive.agent, deliAgent = deliberative.agent;
        Vector reactAgent = reactive.agentVec;
        PositionDiscrete posToMoveD = new PositionDiscrete(0, 0);
        while (panel.isShowing()) {
            if (deliberative.hasKnowledge()) {
                /** DELIBERATIVE ACTION **/
                deliberative.takeAction();
                posToMoveD.x = deliAgent.position.x;
                posToMoveD.y = deliAgent.position.y;
                // Update if discrete position changes
                if (posToMoveD.x != adaptAgent.position.x || posToMoveD.y != adaptAgent.position.y) {
                    adaptive.moveToPosition(posToMoveD);
                    reactive.moveToPosition(posToMoveD);
                }
            } else if (adaptive.hasKnowledge()) {
                /** ADAPTIVE ACTION **/
                adaptive.takeAction();
                posToMoveD.x = adaptAgent.position.x;
                posToMoveD.y = adaptAgent.position.y;
                deliberative.moveToPosition(posToMoveD);
                reactive.moveToPosition(posToMoveD);
            } else {
                /** REACTIVE ACTION **/
                reactive.takeAction();
                posToMoveD.x = Space.convertToDiscrete(reactAgent.start.x);
                posToMoveD.y = Space.convertToDiscrete(reactAgent.start.y);
                // Update direction of discrete based on agent vector
                adaptAgent.direction = Direction.convertAngleToDirection(reactAgent.angle);
                // Update if discrete position changes
                if (posToMoveD.x != adaptAgent.position.x || posToMoveD.y != adaptAgent.position.y) {
                    adaptive.moveToPosition(posToMoveD);
                    deliberative.moveToPosition(posToMoveD);
                }
            }
            if (adaptive.reachedGoal()) {
                reactive.moveToStartingPosition();
                adaptive.moveToStartingPosition();
                deliberative.moveToStartingPosition();
            }
            panel.repaint();
            Thread.sleep(speed);
        }
    }
}
