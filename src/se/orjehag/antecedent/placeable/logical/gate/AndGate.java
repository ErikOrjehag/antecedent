package se.orjehag.antecedent.placeable.logical.gate;

import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * AND gate.
 */
public class AndGate extends Logical {

    public AndGate(int x, int y) {
        super(x, y);
        addInputs(2);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 2;
        return new boolean[]{ in[0] && in[1] };
    }

    @Override protected String getLabel() {
        return "AND";
    }
}