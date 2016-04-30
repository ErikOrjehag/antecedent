package se.orjehag.antecedent.placeable;

import javax.swing.*;
import java.awt.*;

/**
 * Used as comments in the circuit.
 */
public class Text extends Placeable {

    private String text;

    public Text(int x, int y) {
        // Magic number is default height of a placeable Text object.
        super(x, y, 80, 20);
        setText("Text");
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        g2d.setColor(Color.BLACK);
        int stringWidth = g2d.getFontMetrics().stringWidth(text);
        width = stringWidth + 20;
        g2d.drawString(text, position.x - stringWidth / 2, position.y);
    }

    @Override
    public void showPropertiesDialog() {
        String newText = JOptionPane.showInputDialog("Enter text:");
        if (newText != null && !newText.equals("")) {
            setText(newText);
        }
    }
}