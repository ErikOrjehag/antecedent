package se.orjehag.antecedent.logical.input;

import se.orjehag.antecedent.logical.Logical;

import java.awt.*;
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
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        g2d.drawString("BTN: " + (isOn ? "1" : "0"), position.x - 20, position.y);
    }
}
