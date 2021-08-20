package view;

import model.discrete.PositionDiscrete;

public class Space {

    public PositionDiscrete goal;
    public PositionDiscrete[] obstacles;

    public static final int PANEL_SIZE = 20;
    public static final int SQUARE_SIZE = 20;

    public static final double HALF_SQUARE_SIZE = (double) SQUARE_SIZE / 2;

    public static final double GOAL_VALUE = 1;
    public static final double OBSTACLE_VALUE = -1;

    public Space(int mapID) {
        buildMap(mapID);
        multiplyCoordinates();
    }


    public static int convertToDiscrete(double coordinate) {
        return (int) (coordinate / Space.SQUARE_SIZE);
    }

    public static int convertToContinuous(int coordinate) {
        return (int) ((coordinate * Space.SQUARE_SIZE) + Space.HALF_SQUARE_SIZE);
    }

    private void multiplyCoordinates() {
        goal.x *= SQUARE_SIZE;
        goal.y *= SQUARE_SIZE;
        for (PositionDiscrete obs : obstacles) {
            obs.x *= SQUARE_SIZE;
            obs.y *= SQUARE_SIZE;
        }
    }

    private void buildMap(int mapID) {
        switch (mapID) {
            case 0:
                goal = new PositionDiscrete(15, 8);
                obstacles = new PositionDiscrete[]{
                        new PositionDiscrete(9, 8),
                        new PositionDiscrete(9, 6),
                        new PositionDiscrete(9, 10),
                };
                break;
            case 1:
                goal = new PositionDiscrete(15, 11);
                obstacles = new PositionDiscrete[]{
                        new PositionDiscrete(0, 5),
                        new PositionDiscrete(1, 5),
                        new PositionDiscrete(2, 5),
                        new PositionDiscrete(3, 5),
                        new PositionDiscrete(4, 5),
                        new PositionDiscrete(5, 5),
                        new PositionDiscrete(6, 5),

                        new PositionDiscrete(9, 0),
                        new PositionDiscrete(9, 1),
                        new PositionDiscrete(9, 2),
                        new PositionDiscrete(9, 3),
                        new PositionDiscrete(9, 4),
                        new PositionDiscrete(9, 5),
                        new PositionDiscrete(9, 6),
                        new PositionDiscrete(9, 7),
                        new PositionDiscrete(9, 8),
                        new PositionDiscrete(10, 8),
                        new PositionDiscrete(11, 8),
                        new PositionDiscrete(12, 8),
                        new PositionDiscrete(13, 8),
                        new PositionDiscrete(14, 8),

                        new PositionDiscrete(14, 8),
                        new PositionDiscrete(14, 7),
                        new PositionDiscrete(15, 7),
                        new PositionDiscrete(16, 7),
                        new PositionDiscrete(17, 7),
                        new PositionDiscrete(18, 7),
                        new PositionDiscrete(19, 7),

                        new PositionDiscrete(16, 12),
                        new PositionDiscrete(15, 12),
                        new PositionDiscrete(14, 12),
                        new PositionDiscrete(14, 11),
                        new PositionDiscrete(14, 10),
                        new PositionDiscrete(15, 10),
                        new PositionDiscrete(16, 10),

                        new PositionDiscrete(15, 19),
                        new PositionDiscrete(15, 18),
                        new PositionDiscrete(15, 17),
                        new PositionDiscrete(15, 16),
                        new PositionDiscrete(15, 15),
                        new PositionDiscrete(16, 15),
                        new PositionDiscrete(17, 15),
                        new PositionDiscrete(18, 15),
                        new PositionDiscrete(19, 15),

                        new PositionDiscrete(0, 16),
                        new PositionDiscrete(1, 16),
                        new PositionDiscrete(2, 16),
                        new PositionDiscrete(3, 16),
                        new PositionDiscrete(4, 16),
                        new PositionDiscrete(5, 16),
                        new PositionDiscrete(6, 16),
                        new PositionDiscrete(7, 16),
                        new PositionDiscrete(7, 17),
                        new PositionDiscrete(7, 18),
                        new PositionDiscrete(7, 19)
                };
                break;
        }

    }

}
