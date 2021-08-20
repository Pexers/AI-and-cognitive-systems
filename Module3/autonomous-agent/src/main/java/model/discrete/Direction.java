package model.discrete;

public enum Direction {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);

    private final int xMove;
    private final int yMove;

    Direction(int xMove, int yMove) {
        this.xMove = xMove;
        this.yMove = yMove;
    }

    public int getXMove() {
        return this.xMove;
    }

    public int getYMove() {
        return this.yMove;
    }

    public static Direction convertAngleToDirection(double angle) {
        if (angle >= 315 || angle < 45)
            return EAST;
        if (angle >= 45 || angle < 135)
            return NORTH;
        if (angle >= 135 || angle < 225)
            return WEST;
        return SOUTH;
    }

    public Direction getRightDirection() {
        switch (this) {
            case NORTH:
                return EAST;
            case SOUTH:
                return WEST;
            case EAST:
                return SOUTH;
            default:    // WEST
                return NORTH;
        }
    }

    public Direction getLeftDirection() {
        switch (this) {
            case NORTH:
                return WEST;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            default:    // WEST
                return SOUTH;
        }
    }
}
