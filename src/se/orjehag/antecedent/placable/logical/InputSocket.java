package se.orjehag.antecedent.placable.logical;

/**
 * Created by erik on 05/04/16.
 */
public class InputSocket extends Socket {

    private OutputSocket connectedTo;

    public InputSocket(Logical owner) {
        super(owner);
    }

    public boolean getValue() {
        return connectedTo != null && connectedTo.getValue();
    }

    public void connectTo(OutputSocket outputSocket) {
        connectedTo = outputSocket;
    }

    public OutputSocket connectedTo() {
        return connectedTo;
    }
}
