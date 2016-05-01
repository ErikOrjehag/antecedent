package se.orjehag.antecedent.placeable.logical.flop;

import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * T Flop Flop
 */
public class Tflop extends Logical
{
    private boolean prevClock = false;
    private boolean Q = false;

    public Tflop(final int x, final int y) {
	super(x, y);
	addInputs(2);
	addOutputs(2);
    }

    @Override public boolean[] func(final boolean[] in) {
	assert in.length == 2;

	boolean T = in[0];
	boolean clock = in[1];

	boolean risingClock = !prevClock && clock;
	prevClock = clock;

	if (risingClock && T) {
	    Q = !Q;
	}

	return new boolean[]{ Q, !Q };
    }

    @Override protected String getLabel() {
	return "T flop";
    }
}
