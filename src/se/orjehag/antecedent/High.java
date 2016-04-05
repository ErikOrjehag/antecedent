package se.orjehag.antecedent;

import java.awt.*;

/**
 * Created by erik on 05/04/16.
 */
public class High extends Logical {

    public High(int x, int y) {
        super(x, y);
        outputs.add(new Socket());
    }

    @Override
    public boolean[] func(boolean[] inVals) {
        return new boolean[]{ true };
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        g2d.drawString("1", x + 10, y + 20);
    }
}
