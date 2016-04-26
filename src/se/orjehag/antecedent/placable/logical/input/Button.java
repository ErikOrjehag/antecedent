package se.orjehag.antecedent.placable.logical.input;

import se.orjehag.antecedent.Vec2;
import se.orjehag.antecedent.placable.logical.Logical;

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
        return new boolean[]{ isOn };
    }

    @Override
    public void mousePressed(Vec2 mousePos) {
        super.mousePressed(mousePos);
        if (Math.abs(mousePos.x - position.x) < width / 2 && Math.abs(mousePos.y - position.y) < width / 2) {
            isOn = true;
        }
    }

    @Override
    public void mouseReleased(Vec2 mousePos) {
        super.mouseReleased(mousePos);
        isOn = false;
    }

    @Override
    protected String getLabel() {
        return "BTN: " + (isOn ? "1" : "0");
    }
}
