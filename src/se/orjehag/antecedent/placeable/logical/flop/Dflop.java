package se.orjehag.antecedent.placeable.logical.flop;

import se.orjehag.antecedent.placeable.logical.Logical;

public class Dflop extends Logical
{
    private boolean prevClock = false;
    private boolean Q = false;

    public Dflop(final int x, final int y) {
	super(x, y);
	addInputs(2);
	addOutputs(2);
    }

    @Override public boolean[] func(final boolean[] in) {
	boolean risingClock = !prevClock && in[1];
	prevClock = in[1];
	if (risingClock) {
	    Q = in[0];
	}
	return new boolean[]{ Q, !Q };
    }

    @Override protected String getLabel() {
	return "D flop";
    }
}
