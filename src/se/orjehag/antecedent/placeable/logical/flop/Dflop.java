package se.orjehag.antecedent.placeable.logical.flop;

/**
 * D Flip Flop
 */
public class Dflop extends Flop
{
    public Dflop(final int x, final int y) {
	super(x, y);
    }

    @Override public boolean[] func(final boolean[] in) {
	super.func(in);

	assert in.length == 2;

	boolean D = in[0];

	if (isRisingClock()) {
	    Q = D;
	}

	return new boolean[]{ Q, !Q };
    }

    @Override protected String getLabel() {
	return "D flop";
    }
}
