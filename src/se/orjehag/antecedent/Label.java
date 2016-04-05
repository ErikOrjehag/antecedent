package se.orjehag.antecedent;

import java.awt.*;

/**
 * Created by erik on 31/03/16.
 */
public class Label extends Placeable {

    private String text;

    public Label(int x, int y, String text) {
        super(x, y);
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x, y);
    }
}
