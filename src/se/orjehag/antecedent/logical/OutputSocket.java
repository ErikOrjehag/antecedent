package se.orjehag.antecedent.logical;

/**
 * Created by erik on 05/04/16.
 */
public class OutputSocket extends Socket {

    private boolean value = false;

    public OutputSocket(Logical owner) {
        super(owner);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}