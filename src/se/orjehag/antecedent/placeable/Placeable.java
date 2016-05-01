package se.orjehag.antecedent.placeable;

import se.orjehag.antecedent.*;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;


// AbstractClassWithoutAbstractMethods: I still want this class to be abstract
// to prevent someone from creating an instance of this class because it
// does not make any sense.
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

    // A warning tells me that this should be abstract but that is not correct.
    // I want to be able to call this method on all placables but I want this
    // to be a noop method in most cases. If I make this abstract I have to
    // implement the body in all subclasses and I dont want to do that.
    public void onAdd() {
        // This method is called when the placable is added to the simulation.
    }

    public void onRemove() {
        // THis method is called when the placable is removed from the simulation.
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

    public void leftMousePressed(Vec2 mousePos) {
        selected = false;
        if (contains(mousePos)) {
            isDragging = true;
            mouseOffset = position.minus(new Vec2(mousePos.x, mousePos.y));
            selected = true;
        }
    }

    public void leftMouseReleased(Vec2 mousePos) {
        isDragging = false;
    }

    public void rightMousePressed(Vec2 mousePos) {
        if (contains(mousePos)) {
            showPropertiesDialog();
        }
    }

    // I dont want this to be abstract. I want it to be noop in most cases.
    public void rightMouseReleased(Vec2 mousePos) {

    }

    public void mouseMoved(Vec2 mousePos) {
        if (isDragging) {
            position.set(mousePos.plus(mouseOffset));
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void addTo(Simulation simulation) {
        logger.log(Level.INFO, "Adding placeable.");
        simulation.addPlacable(this);
    }

    public void removeFrom(Simulation simulation) {
        logger.log(Level.INFO, "Removing placeable.");
        simulation.removePlacable(this);
    }

    public boolean contains(Vec2 vec) {
        return Math.abs(vec.x - position.x) < width / 2 && Math.abs(vec.y - position.y) < height / 2;
    }

    // I dont want this to be abstract. I want it to be noop in most cases.
    public void showPropertiesDialog() {
        // Placables that have properties that they
        // want changed can override this method.
    }
}
