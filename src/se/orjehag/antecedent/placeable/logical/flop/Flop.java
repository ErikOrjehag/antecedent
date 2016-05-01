package se.orjehag.antecedent.placeable.logical.flop;

import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * Parent class of all flop flops.
 */
public abstract class Flop extends Logical
{
    private boolean prevClock = false;
    private boolean risingClock = false;
    protected boolean Q = false;

    protected Flop(final int x, final int y) {
	super(x, y);
	addInputs(2);
	addOutputs(2);
    }

    @Override public boolean[] func(final boolean[] in) {
    	assert in.length >= 2;

    	boolean clock = in[1];
    	risingClock = !prevClock && clock;
    	prevClock = clock;

    	return new boolean[]{};
    }

    protected boolean isRisingClock() {
	return risingClock;
    }
}
