/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package planning;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Wavefront {
    private final double[][] space;
    private final List<Coordinate> wavefrontList = new LinkedList<>();
    private final int matrixXSize;
    private final int matrixYSize;
    private final int goalValue = 9;
    private final int wallValue = -1;
    private final int emptyValue = 0;

    public Wavefront(int matrixXSize, int matrixYSize, Coordinate[] goals, Coordinate[] obstacles) {
        // summing up 2 to make walls
        this.matrixXSize = matrixXSize + 2;
        this.matrixYSize = matrixYSize + 2;
        space = new double[this.matrixYSize][this.matrixXSize];
        buildSpace(goals, obstacles);
    }

    private void buildSpace(Coordinate[] goals, Coordinate[] obstacles) {
        for (Coordinate c : obstacles)
            space[c.y + 1][c.x + 1] = wallValue;
        Coordinate goal;
        for (Coordinate c : goals) {
            goal = new Coordinate(c.x + 1, c.y + 1);
            space[goal.y][goal.x] = goalValue;
            wavefrontList.add(goal);
            goal.setValue(goalValue);
        }
        // Creating walls
        Arrays.fill(space[0], 0, matrixXSize, wallValue);
        Arrays.fill(space[matrixYSize - 1], 0, matrixXSize, wallValue);
        for (double[] i : space)
            i[0] = i[matrixXSize - 1] = wallValue;
    }

    // Sets values for spaces
    public void runMethod() {
        Coordinate current;
        int idx = 0;
        int currentX;
        int currentY;
        double adjacentVal;
        DecimalFormat df = new DecimalFormat("#.#");
        while (wavefrontList.size() > 0) {
            current = wavefrontList.remove(0);
            // Saving coordinates because they will change in getAdjacentValue()
            currentX = current.x;
            currentY = current.y;
            while (idx < 4) {
                adjacentVal = getAdjacentValue(current, idx);
                if (adjacentVal == emptyValue) {
                    double v = Double.parseDouble(df.format(current.value * 0.95));
                    space[current.y][current.x] = v;
                    Coordinate nCord = new Coordinate(current.x, current.y);
                    nCord.setValue(v);
                    wavefrontList.add(nCord);
                }
                // Resetting changed coordinates
                current.x = currentX;
                current.y = currentY;
                idx++;
            }
            idx = 0;
        }
        drawSpaceValues();
    }

    public void findBestPath(Coordinate start) {
        char[][] bestPath = new char[matrixYSize][matrixXSize];
        double startValue = space[start.y + 1][start.x + 1];
        if (startValue == wallValue)
            System.out.println("This starting point is not allowed.");
        Coordinate current = new Coordinate(start.x + 1, start.y + 1);
        current.setValue(startValue);
        bestPath[start.y + 1][start.x + 1] = PathSymbols.START.getSymbol();
        int idx = 0;
        int currentX;
        int currentY;
        double adjacentValue;
        while (current.value < goalValue) {
            currentX = current.x;
            currentY = current.y;
            while (idx < 4) {
                adjacentValue = getAdjacentValue(current, idx);
                if (adjacentValue == goalValue) {
                    current.setValue(goalValue);
                    break;
                }
                if (adjacentValue > current.value) {
                    bestPath[current.y][current.x] = PathSymbols.PATH.getSymbol();
                    current.setValue(adjacentValue);
                    break;
                }
                // Resetting changed coordinates
                current.x = currentX;
                current.y = currentY;
                idx++;
            }
            idx = 0;
        }
        drawBestPath(bestPath);
    }

    private double getAdjacentValue(Coordinate c, int idx) {
        switch (idx) {
            case 0: // Up
                return space[--c.y][c.x];
            case 1: // Down
                return space[++c.y][c.x];
            case 2: // Right
                return space[c.y][++c.x];
            case 3: // Left
                return space[c.y][--c.x];
        }
        return -1;
    }

    private void drawSpaceValues() {
        for (double[] iArr : space) {
            for (double j : iArr)
                if (j == wallValue)
                    System.out.print(" " + j);
                else System.out.print("  " + j);
            System.out.println();
        }
        System.out.println("----------------");
    }

    private void drawBestPath(char[][] charPath) {
        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[i].length; j++) {
                // Checking if it has no symbols (START or PATH symbol)
                if (charPath[i][j] == 0) {
                    switch ((int) space[i][j]) {
                        case wallValue:
                            charPath[i][j] = PathSymbols.WALL.getSymbol();
                            break;
                        case goalValue:
                            charPath[i][j] = PathSymbols.GOAL.getSymbol();
                            break;
                        default:
                            charPath[i][j] = PathSymbols.EMPTY.getSymbol();
                    }
                }
                System.out.print(charPath[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("----------------");
    }

}
