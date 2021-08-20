package controller;

import model.continuous.PositionContinuous;
import model.continuous.Vector;
import model.discrete.PositionDiscrete;
import view.Space;

import java.awt.*;

public class Reactive implements Layer {

    private final Space space;
    public Vector agentVec;
    private Vector goalVec;
    private Vector[] obsVec;
    private final PositionContinuous startingPos;
    private final int DMaxGoal;
    private final int DMaxObstacle;

    public Reactive(Space space, PositionContinuous startingPos, int DMaxGoal, int DMaxObstacle) {
        this.space = space;
        this.startingPos = startingPos;
        this.DMaxGoal = DMaxGoal;
        this.DMaxObstacle = DMaxObstacle;
        createVectors(startingPos);
    }

    private void createVectors(PositionContinuous startingPos) {
        agentVec = new Vector(startingPos.x, startingPos.y);
        goalVec = new Vector(startingPos.x, startingPos.y);
        obsVec = new Vector[space.obstacles.length];
        for (int i = 0; i < obsVec.length; i++)
            obsVec[i] = new Vector(agentVec.start.x, agentVec.start.y);
    }

    private double calcPotential(double DMax, double d) {
        return d > DMax ? 0 : (DMax - d) / DMax;
    }

    private double calcDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    private double calcAngle(double x1, double x2, double y1, double y2) {
        // x1, y1 - target
        double angle = Math.toDegrees(Math.atan2(y1 - y2, x1 - x2));
        if (angle < 0)
            angle += 360;
        return angle;
    }

    private double sumXVectors() {
        double sumObs = 0;
        for (Vector obs : obsVec)
            sumObs += obs.end.x;
        sumObs += goalVec.end.x;
        // Adding randomness if needed
        if (sumObs < 0.5)
            sumObs += Math.random();
        return sumObs;
    }

    private double sumYVectors() {
        double sumObs = 0;
        for (Vector obs : obsVec)
            sumObs += obs.end.y;
        sumObs += goalVec.end.y;
        // Adding randomness if needed
        if (sumObs < 0.5)
            sumObs += Math.random();
        return sumObs;
    }

    @Override
    public void takeAction() {
        agentVec.start.x += agentVec.end.x;
        agentVec.start.y += agentVec.end.y;
        updateAfterAdvance();
    }

    @Override
    public void moveToPosition(PositionDiscrete posToMove) {
        agentVec.start.x = Space.convertToContinuous(posToMove.x);
        agentVec.start.y = Space.convertToContinuous(posToMove.y);
        updateAfterAdvance();
    }

    @Override
    public void updateAfterAdvance() {
        // Resetting goal and obstacles vectors Start position
        goalVec.start.x = agentVec.start.x;
        goalVec.start.y = agentVec.start.y;
        for (Vector o : obsVec) {
            o.start.x = agentVec.start.x;
            o.start.y = agentVec.start.y;
        }
        double goalCenterX = space.goal.x + Space.HALF_SQUARE_SIZE;
        double goalCenterY = space.goal.y + Space.HALF_SQUARE_SIZE;
        // Setting goal End point based current on potential
        goalVec.setVectorEnd(
                calcPotential(DMaxGoal, calcDistance(goalVec.start.x, goalCenterX, goalVec.start.y, goalCenterY)),
                calcAngle(goalCenterX, goalVec.start.x, goalCenterY, goalVec.start.y) // Agent -> Goal
        );
        double obsX, obsY;
        Vector obs;
        // Setting obstacles End point based current on potential
        for (int i = 0; i < obsVec.length; i++) {
            obs = obsVec[i];
            // Getting the center of the obstacles
            obsX = space.obstacles[i].x + Space.HALF_SQUARE_SIZE;
            obsY = space.obstacles[i].y + Space.HALF_SQUARE_SIZE;
            obs.setVectorEnd(
                    calcPotential(DMaxObstacle, calcDistance(obs.start.x, obsX, obs.start.y, obsY)),
                    calcAngle(obs.start.x, obsX, obs.start.y, obsY) // Obstacle -> Agent
            );
        }
        // Calculating agent new End point
        agentVec.end.x = sumXVectors();
        agentVec.end.y = sumYVectors();
        agentVec.angle = 360 - calcAngle(agentVec.end.x + agentVec.start.x, agentVec.start.x, agentVec.end.y + agentVec.start.y, agentVec.start.y);
    }

    @Override
    public boolean reachedGoal() {
        return Space.convertToDiscrete(agentVec.start.x) == Space.convertToDiscrete(space.goal.x)
                && Space.convertToDiscrete(agentVec.start.y) == Space.convertToDiscrete(space.goal.y);
    }

    @Override
    public void moveToStartingPosition() {
        agentVec.start.x = startingPos.x + Space.HALF_SQUARE_SIZE;
        agentVec.start.y = startingPos.y + Space.HALF_SQUARE_SIZE;
        updateAfterAdvance();
    }

    @Override
    public void draw(Graphics g) {
        int squareSize = Space.SQUARE_SIZE;
        PositionContinuous agentVecStart = agentVec.start, agentVecEnd = agentVec.end;
        g.setColor(Color.GREEN);
        g.fillRect(space.goal.x, space.goal.y, squareSize, squareSize);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(
                (int) (agentVecStart.x - Space.HALF_SQUARE_SIZE),
                (int) (agentVecStart.y - Space.HALF_SQUARE_SIZE), squareSize, squareSize);
        g.setColor(Color.GREEN);
        g.drawLine(
                (int) goalVec.start.x, (int) goalVec.start.y,
                (int) (goalVec.start.x + goalVec.end.x),
                (int) (goalVec.start.y + goalVec.end.y));
        g.setColor(Color.RED);
        for (int i = 0; i < space.obstacles.length; i++) {
            Vector obs = obsVec[i];
            PositionDiscrete pObs = space.obstacles[i];
            g.drawRect(pObs.x, pObs.y, squareSize, squareSize);
            g.drawLine(
                    (int) obs.start.x, (int) obs.start.y,
                    (int) (obs.start.x + obs.end.x), (int) (obs.start.y + obs.end.y));
        }
        g.setColor(Color.YELLOW);
        g.drawLine(
                (int) agentVecStart.x, (int) agentVecStart.y,
                (int) (agentVecStart.x + agentVecEnd.x),
                (int) (agentVecStart.y + agentVecEnd.y));
    }


}
