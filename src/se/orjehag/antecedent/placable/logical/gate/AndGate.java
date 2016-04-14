package se.orjehag.antecedent.placable.logical.gate;

import se.orjehag.antecedent.placable.logical.Logical;

import java.awt.*;

/**
 * Created by erik on 31/03/16.
 */
public class AndGate extends Logical {

    public AndGate(int x, int y) {
        super(x, y);
        addInputs(2);
        addOutputs(1);
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 2;
        return new boolean[]{ in[0] && in[1] };
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        g2d.drawString("AND", position.x - 10, position.y);
    }
}