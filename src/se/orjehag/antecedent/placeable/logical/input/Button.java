package se.orjehag.antecedent.placeable.logical.input;

import se.orjehag.antecedent.Vec2;
import se.orjehag.antecedent.placeable.logical.Logical;

/**
 * Momentary push button.
 */
public class Button extends Logical {

    private boolean isOn = false;

    public Button(int x, int y) {
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
            isOn = true;
            notifyInteractionListeners();
        }
    }

    @Override
    public void leftMouseReleased(Vec2 mousePos) {
        super.leftMouseReleased(mousePos);
        if (isOn) {
            isOn = false;
            notifyInteractionListeners();
        }
    }

    @Override
    protected String getLabel() {
        return "BTN: " + (isOn ? "1" : "0");
    }
}
