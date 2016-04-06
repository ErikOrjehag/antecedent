package se.orjehag.antecedent.logical.input;

import se.orjehag.antecedent.logical.Logical;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by erik on 05/04/16.
 */
public class Low extends Logical {

    public Low(int x, int y) {
        super(x, y);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        return new boolean[]{ false };
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        g2d.drawString("0", position.x, position.y);
    }
}
