package se.orjehag.antecedent;

import java.awt.*;

/**
 * Created by erik on 05/04/16.
 */
public class Lamp extends Logical {

    public Lamp(int x, int y) {
        super(x, y);
        inputs.add(new Socket());
    }

    @Override
    public boolean[] func(boolean[] inVals) {
        return new boolean[0];
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        boolean isOn = inputs.get(0).connectedTo.get(0).getValue();
        String onOff = isOn ? "ON" : "OFF";
        g2d.setColor(Color.BLACK);
        g2d.drawString(onOff, x + 10, y + 20);
    }
}
