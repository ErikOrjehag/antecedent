package se.orjehag.antecedent.logical.output;

import se.orjehag.antecedent.logical.Logical;

import java.awt.*;

/**
 * Created by erik on 06/04/16.
 */
public class FourBitDisplay extends Logical {

    public FourBitDisplay(int x, int y) {
        super(x, y);
        addInputs(4);
        height = 80;
    }

    @Override
    public boolean[] func(boolean[] in) {
        return new boolean[0];
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        int n =
                1 * (inputs.get(0).getValue() ? 1 : 0) +
                2 * (inputs.get(1).getValue() ? 1 : 0) +
                4 * (inputs.get(2).getValue() ? 1 : 0) +
                8 * (inputs.get(3).getValue() ? 1 : 0);
        g2d.drawString(Integer.toString(n), position.x - 10, position.y);
    }
}
