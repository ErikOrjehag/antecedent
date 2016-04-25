package se.orjehag.antecedent.placable.logical.input;

import se.orjehag.antecedent.placable.logical.Logical;

/**
 * Created by erik on 05/04/16.
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
