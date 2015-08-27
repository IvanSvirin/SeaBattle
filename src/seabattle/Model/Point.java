package seabattle.Model;

import java.util.Random;

/**
 * Created by ivan on 26.05.2015.
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point getRandomPoint() {
        return new Point(new Random().nextInt(10), new Random().nextInt(10));
    }
}
