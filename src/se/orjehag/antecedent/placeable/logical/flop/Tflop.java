package se.orjehag.antecedent.placeable.logical.flop;

/**
 * T Flop Flop
 */
public class Tflop extends Flop
{
    public Tflop(final int x, final int y) {
	super(x, y);
    }

    @Override public boolean[] func(final boolean[] in) {
	super.func(in);

	assert in.length == 2;

	boolean T = in[0];

	if (isRisingClock() && T) {
	    Q = !Q;
	}

	return new boolean[]{ Q, !Q };
    }

    @Override protected String getLabel() {
	return "T flop";
    }
}
