package se.orjehag.antecedent.placeable.logical.input;

import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * A static high signal.
 */
public class High extends Logical {

    public High(int x, int y) {
        super(x, y);
        addOutputs(1);
        // Propagate the constant high to the output socket.
        step();
        commit();
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 0;
        return new boolean[]{ true };
    }

    @Override protected String getLabel() {
        return "1";
    }
}
