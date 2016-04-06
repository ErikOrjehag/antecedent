package se.orjehag.antecedent;

/**
 * Created by erik on 05/04/16.
 */
public class Point {

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

    public void add(Point point) {
        x += point.x;
        y += point.y;
    }

    public void subtract(Point point) {
        x -= point.x;
        y -= point.y;
    }

    public Point plus(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point minus(Point point) {
        return new Point(x - point.x, y - point.y);
    }
}
