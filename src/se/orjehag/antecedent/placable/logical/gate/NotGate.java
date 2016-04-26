package se.orjehag.antecedent.placable.logical.gate;

import se.orjehag.antecedent.placable.logical.Logical;

/**
 * NOT gate.
 */
public class NotGate extends Logical {

    public NotGate(int x, int y) {
        super(x, y);
        addInputs(1);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 1;
        return new boolean[]{ !in[0] };
    }

    @Override protected String getLabel() {
        return "NOT";
    }
}
