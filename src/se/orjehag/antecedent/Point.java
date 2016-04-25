package se.orjehag.antecedent;

import java.io.Serializable;

/**
 * Created by erik on 05/04/16.
 */
public class Point implements Serializable {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        x = 0;
        y = 0;
    }

    public void set(Point point) {
        x = point.x;
        y = point.y;
    }

    public Point plus(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point minus(Point point) {
        return new Point(x - point.x, y - point.y);
    }

    public float distanceTo(Point point) {
        return minus(point).length();
    }

    public float length() {
        return (float)Math.sqrt(x * x + y * y);
    }
}
