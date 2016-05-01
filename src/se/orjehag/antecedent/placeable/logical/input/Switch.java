package se.orjehag.antecedent.placeable.logical.input;

import se.orjehag.antecedent.Vec2;
import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * Input toggle switch.
 */
public class Switch extends Logical {

    private boolean isOn = false;

    public Switch(int x, int y) {
        super(x, y);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 0;
        return new boolean[]{ isOn };
    }

    @Override
    public void leftMousePressed(Vec2 mousePos) {
        super.leftMousePressed(mousePos);
        if (contains(mousePos)) {
            isOn = !isOn;
            notifyInteractionListeners();
        }
    }

    @Override protected String getLabel() {
        return "SW: " + (isOn ? "1" : "0");
    }
}
