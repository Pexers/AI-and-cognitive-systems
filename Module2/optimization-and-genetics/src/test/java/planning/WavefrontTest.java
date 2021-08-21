/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package planning;

import org.junit.jupiter.api.Test;

public class WavefrontTest {

    Wavefront wf;

    @Test
    public void test_find_best_paths() {
        buildSpace();
        wf.findBestPath(new Coordinate(0, 0));
        wf.findBestPath(new Coordinate(0, 5));
    }

    public void buildSpace() {
        Coordinate[] goals = new Coordinate[]{
                new Coordinate(6, 2),
                new Coordinate(6, 3)};
        Coordinate[] obstacles = new Coordinate[]{
                new Coordinate(2, 1),
                new Coordinate(2, 2),
                new Coordinate(2, 3),
                new Coordinate(7, 0),
                new Coordinate(7, 1),
                new Coordinate(7, 2),
                new Coordinate(5, 4),
        };
        wf = new Wavefront(9, 6, goals, obstacles);
        wf.runMethod();
    }

}
