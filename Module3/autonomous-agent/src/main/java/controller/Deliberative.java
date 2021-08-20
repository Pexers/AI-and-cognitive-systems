package controller;

import model.discrete.Agent;
import model.discrete.Direction;
import model.discrete.PositionDiscrete;
import view.Space;

import java.awt.*;
import java.util.Arrays;

public class Deliberative implements Layer {

    private final Space space;
    public final Agent agent;
    private final PositionDiscrete startingPos;
    private final Direction startingDir;
    private boolean readyForAction = false;
    private final double discountFactor, maxDifference;
    private int countDown;
    private final double[][] spaceValues = new double[Space.PANEL_SIZE][Space.PANEL_SIZE];

    public Deliberative(Space space, PositionDiscrete startingPos, Direction startingDir, double maxDifference, double discountFactor, int goalCountdown) {
        this.space = space;
        this.startingPos = startingPos;
        this.startingDir = startingDir;
        this.maxDifference = maxDifference;
        this.discountFactor = discountFactor;
        this.countDown = goalCountdown;
        agent = new Agent(startingPos, startingDir);
        assignSpaceValues();
    }

    public boolean hasKnowledge() { // Not very well made...
        return readyForAction && (agent.frontVal > 1 || agent.leftVal > 1 || agent.rightVal > 1);
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
    }


    private void takeRandomAction() {
        double r = Math.random();
        if (r <= 0.5 && agent.frontVal != Space.OBSTACLE_VALUE) {
            agent.advance();
            updateAfterAdvance();
        } else if (r > 0.5 && r <= 0.75) {
            agent.rotateLeft();
            agent.updateSensors(spaceValues);
        } else {
            agent.rotateRight();
            agent.updateSensors(spaceValues);
        }
    }

    private double getTransitionProb(int nextX, int nextY) {
        if (spaceValues[nextY][nextX] == Space.OBSTACLE_VALUE)
            return 0;
        return 1;
    }

    // Value Iteration Algorithm
    private void calcPositionUtility(int x, int y) {
        if (spaceValues[y][x] == Space.OBSTACLE_VALUE)
            return;
        double transitionProb, reward, utility, bestUtility = 0, difference;
        int nextX, nextY;
        // Obtain best utility for all possible moves
        for (Direction dir : Direction.values()) {
            nextX = x + dir.getXMove();
            nextY = y + dir.getYMove();
            transitionProb = getTransitionProb(nextX, nextY);
            reward = spaceValues[nextY][nextX];
            // Us = T * (R + DF * Us')
            utility = transitionProb * (reward + discountFactor * spaceValues[nextY][nextX]);
            if (utility > bestUtility)
                bestUtility = utility;
        }
        difference = Math.abs(spaceValues[y][x] - bestUtility);
        spaceValues[y][x] = bestUtility;
        if (countDown == 0 && difference < maxDifference)
            readyForAction = true;
        agent.updateSensors(spaceValues);
    }

    @Override
    public void takeAction() {  // Applies e-greedy
        if (agent.frontVal == agent.leftVal && agent.leftVal == agent.rightVal) {
            takeRandomAction();
        } else {
            double max = Double.max(agent.frontVal, Double.max(agent.leftVal, agent.rightVal));
            if (max == agent.frontVal) {
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
        agent.position.x = posToMove.x;
        agent.position.y = posToMove.y;
        updateAfterAdvance();
    }

    @Override
    public void updateAfterAdvance() {
        agent.updateSensors(spaceValues);
        calcPositionUtility(agent.position.x, agent.position.y);
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
        if (countDown > 0) countDown--;
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
