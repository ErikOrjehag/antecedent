package se.orjehag.antecedent.placable.logical.input;

import se.orjehag.antecedent.placable.logical.Logical;

import java.awt.event.MouseEvent;

/**
 * Created by erik on 06/04/16.
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
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (Math.abs(e.getX() - position.x) < width / 2 && Math.abs(e.getY() - position.y) < width / 2) {
            isOn = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        isOn = false;
    }

    @Override
    protected String getLabel() {
        return "BTN: " + (isOn ? "1" : "0");
    }
}
