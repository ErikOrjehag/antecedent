package se.orjehag.antecedent;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by erik on 31/03/16.
 */
public class Label extends Placeable {

    private String text;

    public Label(int x, int y, String text) {
        super(x, y, 80, 20);
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, position.x, position.y);
    }
}
