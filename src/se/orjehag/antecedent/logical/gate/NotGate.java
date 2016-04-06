package se.orjehag.antecedent.logical.gate;

import se.orjehag.antecedent.logical.Logical;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by erik on 06/04/16.
 */
public class NotGate extends Logical {

    public NotGate(int x, int y) {
        super(x, y);
        addInputs(1);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 1;
        return new boolean[]{ !in[0] };
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        g2d.drawString("NOT", position.x - 10, position.y);
    }
}
