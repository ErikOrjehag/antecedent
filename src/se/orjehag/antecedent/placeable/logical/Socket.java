package se.orjehag.antecedent.placeable.logical;

import se.orjehag.antecedent.Vec2;

import java.io.Serializable;

/**
 * Sockets are used as inputs and outputs on logical components.
 * Because connections between outputs and inputs have a one to
 * many relation the actual value (state) of the socket is stored
 * in the output socket. This is abstracted away using this parent class
 * which makes it possible to call the getValue method on both inputs and
 * outputs even though input sockets only references the value stored
 * in the output socket it's connected to. Also because of the one to many
 * relationship of outputs and inputs it made since to store which output
 * the input socket is connected to because there can only be one, and
 * not the other way around.
 *
 *              *-----(input) [Only references the value (state) stored in the output. Knows which output its connected to]
 *             /
 *    (output)--------(input) [--||--]
 *       |
 *        [
 *          Stores the value (state) in a boolean.
 *          Because it doesn't store the relation (connection) to the input socket it must notify the input socket
 *          if it wants to disconnect and ask it to remove the relation. This is done by storing the input sockets
 *          it's connected to in a list and when the output wants to disconnect itself from the inputs it asks
 *          each of them in turn to remove the relation.
 *        ]
 *
 * This parent class also contains some shared functionality such as figuring out its global position
 * based on the owners (Logical) position plus some offset.
 *
 */
public abstract class Socket implements Serializable
{
    private Logical owner;
    private static final int SNAP_TO_DISTANCE = 10;

    protected Socket(Logical owner) {
        this.owner = owner;
    }

    public Vec2 getPosition() {
        return owner.getPosition().plus(owner.relativeSocketPosition(this));
    }

    public abstract boolean getValue();

    public boolean isNear(Vec2 vec2) {
        return getPosition().distanceTo(vec2) < SNAP_TO_DISTANCE;
    }
}
