/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package model.discrete;

public class Agent {

    public PositionDiscrete position;
    public Direction direction;
    public double currentVal;
    public double frontVal;
    public double leftVal;
    public double rightVal;

    public Agent(PositionDiscrete position, Direction direction) {
        this.position = new PositionDiscrete(position.x, position.y);
        this.direction = direction;
    }

    public void updateSensors(double[][] spaceValues) {
        int x = position.x;
        int y = position.y;
        currentVal = spaceValues[y][x];
        Direction dir = direction;
        frontVal = spaceValues[y + dir.getYMove()][x + dir.getXMove()];
        dir = direction.getLeftDirection();
        leftVal = spaceValues[y + dir.getYMove()][x + dir.getXMove()];
        dir = direction.getRightDirection();
        rightVal = spaceValues[y + dir.getYMove()][x + dir.getXMove()];
    }

    public void advance() {
        position.x += direction.getXMove();
        position.y += direction.getYMove();
    }

    public void rotateRight() {
        direction = direction.getRightDirection();
    }

    public void rotateLeft() {
        direction = direction.getLeftDirection();
    }

}
