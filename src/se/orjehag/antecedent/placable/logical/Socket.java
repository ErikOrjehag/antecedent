package se.orjehag.antecedent.placable.logical;

import se.orjehag.antecedent.Point;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Socket {

    private Logical owner;

    public Socket(Logical owner) {
        this.owner = owner;
    }

    public Point getPosition() {
        return owner.getPosition().plus(owner.relativeSocketPosition(this));
    }

    public abstract boolean getValue();

    public boolean contains(Point point) {
        return getPosition().distanceTo(point) < 10; // TODO
    }
}
