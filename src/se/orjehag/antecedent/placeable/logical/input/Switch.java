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
        return new boolean[]{ isOn };
    }

    @Override
    public void mousePressed(Vec2 mousePos) {
        super.mousePressed(mousePos);
        if (Math.abs(mousePos.x - position.x) < width / 2 && Math.abs(mousePos.y - position.y) < width / 2) {
            isOn = !isOn;
        }
    }

    @Override protected String getLabel() {
        return "SW: " + (isOn ? "1" : "0");
    }
}
