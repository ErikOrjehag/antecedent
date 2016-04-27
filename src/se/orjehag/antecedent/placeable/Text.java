package se.orjehag.antecedent.placeable;

import java.awt.*;

/**
 * Used as comments in the circuit.
 */
public class Text extends Placeable {

    private String text;

    public Text(int x, int y) {
        // 80 is the default width and 20 is the
        // default height of a placeable Text object.
        //noinspection MagicNumber
        super(x, y, 80, 20);
        text = "Text";
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        int stringWidth = g2d.getFontMetrics().stringWidth(text);
        g2d.drawString(text, position.x - stringWidth / 2, position.y);
    }
}