package se.orjehag.antecedent;

import java.awt.*;

/**
 * Created by erik on 31/03/16.
 */
public class AndGate extends Logical {

    public AndGate(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean[] func(boolean[] inVals) {
        return new boolean[0];
    }

    @Override
    public void draw(Graphics2D g2d) {

    }
}
