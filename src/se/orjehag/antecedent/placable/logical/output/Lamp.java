package se.orjehag.antecedent.placable.logical.output;

import se.orjehag.antecedent.placable.logical.Logical;

/**
 * Created by erik on 05/04/16.
 */
public class Lamp extends Logical {

    public Lamp(int x, int y) {
        super(x, y);
        addInputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        return new boolean[0];
    }

    @Override protected String getLabel() {
        boolean isOn = inputs.get(0).getValue();
        return isOn ? "ON" : "OFF";
    }
}
