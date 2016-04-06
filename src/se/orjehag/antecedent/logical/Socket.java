package se.orjehag.antecedent.logical;

import se.orjehag.antecedent.Point;
import se.orjehag.antecedent.logical.Logical;

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
}
