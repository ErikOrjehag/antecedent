package se.orjehag.antecedent;

/**
 * Created by erik on 05/04/16.
 */
public class InputSocket {

    private OutputSocket connectedTo;

    public InputSocket() {

    }

    public getValue() {
        return connectedTo.getValue();
    }
}
