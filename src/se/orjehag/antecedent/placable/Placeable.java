package se.orjehag.antecedent.placable;

import se.orjehag.antecedent.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import se.orjehag.antecedent.Point;
import se.orjehag.antecedent.placable.logical.Logical;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Placeable implements java.io.Serializable {

    protected se.orjehag.antecedent.Point position;
    private ArrayList<DragListener> dragListeners = new ArrayList<>();
    protected int width, height;
    private boolean isDragging = false;
    private se.orjehag.antecedent.Point mouseOffset;
    private boolean isSelected = false;

    public Placeable(int x, int y) {
        position = new Point(x, y);
    }

    public void draw(Graphics2D g2d) {
        if (isSelected) {
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

    public void addDragListener(DragListener listener) {
        dragListeners.add(listener);
    }

    public void removeDragListener(DragListener listener) {
        dragListeners.remove(listener);
    }

    public se.orjehag.antecedent.Point getPosition() {
        return position;
    }

    public void mousePressed(MouseEvent e) {
        isSelected = false;
        if (Math.abs(e.getX() - position.x) < width / 2 && Math.abs(e.getY() - position.y) < height / 2) {
            isDragging = true;
            mouseOffset = position.minus(new Point(e.getX(), e.getY()));
            isSelected = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }

    public void mouseMoved(MouseEvent e) {
        if (isDragging) {
            se.orjehag.antecedent.Point mousePosition = new Point(e.getX(), e.getY());
            position.set(mousePosition.plus(mouseOffset));
        }
    }

    public void addTo(ArrayList<Placeable> placeables, ArrayList<Logical> logicals) {
        placeables.add(this);
    }

    public boolean isSelected() {
        return isSelected;
    }
}
