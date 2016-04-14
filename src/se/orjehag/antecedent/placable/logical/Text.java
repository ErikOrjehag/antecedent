package se.orjehag.antecedent.placable.logical;

import java.awt.*;

/**
 * Created by erik on 31/03/16.
 */
public class Text extends Placeable {

    private String text;

    public Text(int x, int y, String text) {
        super(x, y, 80, 20);
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, position.x, position.y);
    }
}
