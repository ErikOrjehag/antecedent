package se.orjehag.antecedent.placable.logical;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by erik on 05/04/16.
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