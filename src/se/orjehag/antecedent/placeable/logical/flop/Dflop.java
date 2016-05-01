package se.orjehag.antecedent.placeable.logical.flop;

import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * D Flip Flop
 */
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
	assert in.length == 2;

	boolean D = in[0];
	boolean clock = in[1];

	boolean risingClock = !prevClock && clock;
	prevClock = clock;

	if (risingClock) {
	    Q = D;
	}

	return new boolean[]{ Q, !Q };
    }

    @Override protected String getLabel() {
	return "D flop";
    }
}
