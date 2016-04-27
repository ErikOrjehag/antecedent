package se.orjehag.antecedent.placeable.logical.input;

import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * A static low signal.
 */
public class Low extends Logical {

    public Low(int x, int y) {
        super(x, y);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        return new boolean[]{ false };
    }

    @Override protected String getLabel() {
        return "0";
    }
}
