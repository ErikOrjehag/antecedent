package se.orjehag.antecedent.placable;

import java.awt.*;

/**
 * Created by erik on 31/03/16.
 */
public class Text extends Placeable {

    private String text;

    public Text(int x, int y) {
        // 80 is the default width and 20 is the
        // default height of a placable Text object.
        //noinspection MagicNumber
        super(x, y, 80, 20);
        text = "Text";
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        int stringWidth = g2d.getFontMetrics().stringWidth(text);
        g2d.drawString(text, position.x - stringWidth / 2, position.y);
    }
}