package se.orjehag.antecedent.placable;

import java.awt.*;

/**
 * Created by erik on 31/03/16.
 */
public class Text extends Placeable {

    private String text;

    public Text(int x, int y) {
        super(x, y);
        width = 80;
        height = 20;
        text = "Text";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, position.x, position.y);
    }
}
