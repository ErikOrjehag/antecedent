package se.orjehag.antecedent.placable.logical.input;

import se.orjehag.antecedent.placable.logical.Logical;

/**
 * A static high signal.
 */
public class High extends Logical {

    public High(int x, int y) {
        super(x, y);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        return new boolean[]{ true };
    }

    @Override protected String getLabel() {
        return "1";
    }
}
