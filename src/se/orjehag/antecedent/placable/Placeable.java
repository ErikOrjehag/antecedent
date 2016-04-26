package se.orjehag.antecedent.placable;

import se.orjehag.antecedent.*;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.List;
import se.orjehag.antecedent.placable.logical.Logical;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;


// I still want this class to be abstract to prevent someone
// from creating an instance of this class because it does not
// make any sense.
@SuppressWarnings("AbstractClassWithoutAbstractMethods")
public abstract class Placeable implements Serializable
{
    protected Vec2 position;
    protected int width, height;
    private boolean isDragging = false;
    private Vec2 mouseOffset = null;
    private boolean selected = false;

    private final Logger logger = Logger.getLogger(Placeable.class.getName());

    protected Placeable(int x, int y, int width, int height) {
        position = new Vec2(x, y);
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2d) {
        if (selected) {
            AffineTransform oldTransform = g2d.getTransform();
            Stroke oldStroke = g2d.getStroke();

            g2d.translate(position.x, position.y);
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] {2}, 0));
            g2d.setColor(Color.BLACK);
            g2d.drawRect(-width / 2 - 5, -height / 2 - 5, width + 10, height + 10);

            g2d.setTransform(oldTransform);
            g2d.setStroke(oldStroke);
        }
    }

    public Vec2 getPosition() {
        return position;
    }

    public void mousePressed(Vec2 mousePos) {
        selected = false;
        if (Math.abs(mousePos.x - position.x) < width / 2 && Math.abs(mousePos.y - position.y) < height / 2) {
            isDragging = true;
            mouseOffset = position.minus(new Vec2(mousePos.x, mousePos.y));
            selected = true;
        }
    }

    public void mouseReleased(Vec2 mousePos) {
        isDragging = false;
    }

    public void mouseMoved(Vec2 mousePos) {
        if (isDragging) {
            position.set(mousePos.plus(mouseOffset));
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void addTo(List<Placeable> placables, List<Logical> logicals) {
        logger.log(Level.INFO, "Adding placable.");
        placables.add(this);
        // Don't add to logicals.
    }
}
