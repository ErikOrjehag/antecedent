package se.orjehag.antecedent.placable.logical;

/**
 * Stores which output socket this input is connected to.
 * An input socket can only be connected to one output.
 */
public class InputSocket extends Socket {

    private OutputSocket connectedTo = null;

    public InputSocket(Logical owner) {
        super(owner);
    }

    public boolean getValue() {
        return connectedTo != null && connectedTo.getValue();
    }

    public void connectTo(OutputSocket outputSocket) {
        connectedTo = outputSocket;
        outputSocket.addDisconnectFromInputListener(this);
    }

    public OutputSocket getConnectedTo() {
        return connectedTo;
    }

    public boolean isConnected() {
        return connectedTo != null;
    }

    public void disconnect() {
        connectedTo = null;
    }
}
