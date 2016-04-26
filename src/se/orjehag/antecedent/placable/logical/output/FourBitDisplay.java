package se.orjehag.antecedent.placable.logical.output;

import se.orjehag.antecedent.placable.logical.Logical;

/**
 * Displays a four bit number (0-15) based on the 4 input signals.
 */
public class FourBitDisplay extends Logical {

    public FourBitDisplay(int x, int y) {
        // Magic number 70 means the height is 70,
        // -1 means that we use the Logic default width.
        //noinspection MagicNumber
        super(x, y, -1,  70);
        addInputs(4);
    }

    @Override
    public boolean[] func(boolean[] in) {
        return new boolean[0];
    }

    @Override protected String getLabel() {
        int n = (inputs.get(0).getValue() ? 1 : 0) +
            2 * (inputs.get(1).getValue() ? 1 : 0) +
            4 * (inputs.get(2).getValue() ? 1 : 0) +
            8 * (inputs.get(3).getValue() ? 1 : 0);
        return Integer.toString(n);
    }
}
