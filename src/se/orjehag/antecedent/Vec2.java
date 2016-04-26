package se.orjehag.antecedent;

import java.io.Serializable;

/**
 * A 2D vector class to do some vector math.
 */
public class Vec2 implements Serializable {

    public int x, y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2() {
        x = 0;
        y = 0;
    }

    public void set(Vec2 other) {
        x = other.x;
        y = other.y;
    }

    public Vec2 plus(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 minus(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    public float distanceTo(Vec2 other) {
        return minus(other).length();
    }

    public float length() {
        return (float)Math.sqrt(x * x + y * y);
    }
}
