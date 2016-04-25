package se.orjehag.antecedent.placable.logical.gate;

import se.orjehag.antecedent.placable.logical.Logical;

/**
 * Created by erik on 31/03/16.
 */
public class OrGate extends Logical {

    public OrGate(int x, int y) {
        super(x, y);
        addInputs(2);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 2;
        return new boolean[]{ in[0] || in[1] };
    }

    @Override protected String getLabel() {
        return "OR";
    }
}