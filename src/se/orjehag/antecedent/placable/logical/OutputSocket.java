package se.orjehag.antecedent.placable.logical;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The CoutputScoket stores signal state in "value".
 * It has a list to the inputs it's connected to in
 * order to ask the inputs to remove the reference
 * to this output when it wants to disconnect.
 *
 * One output can be connected to multiple inputs.
 */
public class OutputSocket extends Socket {

    private boolean value = false;
    private Collection<InputSocket> disconnectFromInputListeners = new ArrayList<>();

    public OutputSocket(Logical owner) {
        super(owner);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void addDisconnectFromInputListener(InputSocket inputSocket) {
        disconnectFromInputListeners.add(inputSocket);
    }

    public void disconnect() {
        disconnectFromInputListeners.forEach(InputSocket::disconnect);
    }
}