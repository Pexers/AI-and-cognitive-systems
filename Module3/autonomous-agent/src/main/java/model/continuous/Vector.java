package model.continuous;

import view.Space;

public class Vector {
    public PositionContinuous start;
    public PositionContinuous end;
    public double angle;

    public Vector(double x, double y) {
        start = new PositionContinuous(x + Space.HALF_SQUARE_SIZE, y + Space.HALF_SQUARE_SIZE);
        end = new PositionContinuous(0, 0);
    }

    public void setVectorEnd(double potential, double angle) {
        this.angle = 360 - angle;
        end.x = Space.SQUARE_SIZE * potential * Math.cos(angle * (Math.PI / 180)); // Converting degrees to radians
        end.y = Space.SQUARE_SIZE * potential * Math.sin(angle * (Math.PI / 180)); // Converting degrees to radians
    }

}
