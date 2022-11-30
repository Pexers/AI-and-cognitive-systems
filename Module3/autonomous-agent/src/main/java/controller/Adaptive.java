/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package controller;

import model.discrete.Agent;
import model.discrete.Direction;
import view.Space;
import model.discrete.PositionDiscrete;

import java.awt.*;
import java.util.Arrays;

public class Adaptive implements Layer {

    private final Space space;
    public final Agent agent, agentClone;  // Clone is one step behind the Real agent
    private final PositionDiscrete startingPos;
    private final Direction startingDir;
    private final double learningRate, discountFactor, emptyValue = 0.1;
    private final double[][] spaceValues = new double[Space.PANEL_SIZE][Space.PANEL_SIZE];

    public Adaptive(Space space, PositionDiscrete startingPos, Direction startingDir, double learningRate, double discountFactor) {
        this.space = space;
        this.startingPos = startingPos;
        this.startingDir = startingDir;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        agent = new Agent(startingPos, startingDir);
        agentClone = new Agent(startingPos, startingDir);
        assignSpaceValues();
        agent.updateSensors(spaceValues);
    }

    public boolean hasKnowledge() {
        return agent.frontVal != emptyValue
                || agent.leftVal != emptyValue
                || agent.rightVal != emptyValue;
    }

    private void assignSpaceValues() {
        for (PositionDiscrete o : space.obstacles)
            spaceValues[o.y / Space.SQUARE_SIZE][o.x / Space.SQUARE_SIZE] = Space.OBSTACLE_VALUE;
        spaceValues[space.goal.y / Space.SQUARE_SIZE][space.goal.x / Space.SQUARE_SIZE] = Space.GOAL_VALUE;

        // Assigning values to space borders
        int size = spaceValues[0].length;
        Arrays.fill(spaceValues[0], 0, size, Space.OBSTACLE_VALUE);
        Arrays.fill(spaceValues[size - 1], 0, size, Space.OBSTACLE_VALUE);
        for (double[] i : spaceValues)
            i[0] = i[size - 1] = Space.OBSTACLE_VALUE;

        for (double[] i : spaceValues) {
            for (int j = 0; j < i.length; j++) {
                if (i[j] == 0)
                    i[j] = emptyValue;
            }
        }
    }

    private void saveAgentClone() {
        agentClone.position.x = agent.position.x;
        agentClone.position.y = agent.position.y;
        agentClone.currentVal = agent.currentVal;
    }

    public void takeRandomAction() {
        int r = (int) (Math.random() * 3);
        switch (r) {
            case 0:
                if (agent.frontVal != Space.OBSTACLE_VALUE) {
                    saveAgentClone();
                    agent.advance();
                    updateAfterAdvance();
                    break;
                }
            case 1:
                agent.rotateLeft();
                agent.updateSensors(spaceValues);
                break;
            case 2:
                agent.rotateRight();
                agent.updateSensors(spaceValues);
                break;
        }
    }

    @Override
    public void takeAction() {  // Applies e-greedy
        if (agent.frontVal == agent.leftVal && agent.leftVal == agent.rightVal) {
            takeRandomAction();
        } else {
            double max = Double.max(agent.frontVal, Double.max(agent.leftVal, agent.rightVal));
            if (max == agent.frontVal) {
                saveAgentClone();
                agent.advance();
                updateAfterAdvance();
            } else if (max == agent.leftVal) {
                agent.rotateLeft();
                agent.updateSensors(spaceValues);
            } else if (max == agent.rightVal) {
                agent.rotateRight();
                agent.updateSensors(spaceValues);
            }
        }
    }

    @Override
    public void moveToPosition(PositionDiscrete posToMove) {
        saveAgentClone();
        agent.position.x = posToMove.x;
        agent.position.y = posToMove.y;
        updateAfterAdvance();
    }

    // Sarsa Algorithm
    @Override
    public void updateAfterAdvance() {
        agent.updateSensors(spaceValues);
        double lastVal = agentClone.currentVal, nextVal = agent.currentVal;
        double reward = nextVal - lastVal;
        // Q(s,a) = Q(s,a) + learningRate * (reward + discountFactor * Q(s',a') - Q(s,a))
        spaceValues[agentClone.position.y][agentClone.position.x] =
                lastVal + learningRate * (reward + discountFactor * nextVal - lastVal);
        agent.updateSensors(spaceValues);
    }

    @Override
    public boolean reachedGoal() {
        return agent.position.x == Space.convertToDiscrete(space.goal.x)
                && agent.position.y == Space.convertToDiscrete(space.goal.y);
    }

    @Override
    public void moveToStartingPosition() {
        agent.position.x = startingPos.x;
        agent.position.y = startingPos.y;
        agent.direction = startingDir;
        agent.updateSensors(spaceValues);
        saveAgentClone();
    }

    @Override
    public void draw(Graphics g) {
        int squareSize = Space.SQUARE_SIZE;
        g.setColor(Color.GREEN);
        g.fillRect(space.goal.x, space.goal.y, squareSize, squareSize);
        g.setColor(Color.DARK_GRAY);
        for (PositionDiscrete p : space.obstacles)
            g.drawRect(p.x, p.y, squareSize, squareSize);
        PositionDiscrete agentPos = agent.position;
        Direction agentDir = agent.direction;
        g.fillRect(agentPos.x * squareSize, agentPos.y * squareSize, squareSize, squareSize);
        switch (agentDir) {
            case NORTH:
            case SOUTH:
                g.fillRect(
                        (agentPos.x + agentDir.getXMove()) * squareSize + squareSize / 2 - (squareSize / 5) / 2,
                        (agentPos.y + agentDir.getYMove()) * squareSize,
                        squareSize / 5,
                        squareSize);
                break;
            default:
                g.fillRect(
                        (agentPos.x + agentDir.getXMove()) * squareSize,
                        (agentPos.y + agentDir.getYMove()) * squareSize + squareSize / 2 - (squareSize / 5) / 2,
                        squareSize,
                        squareSize / 5);
        }
    }

}
