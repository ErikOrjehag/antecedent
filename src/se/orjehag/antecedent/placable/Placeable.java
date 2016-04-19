package se.orjehag.antecedent.placable;

import se.orjehag.antecedent.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import se.orjehag.antecedent.Point;
import se.orjehag.antecedent.placable.logical.Logical;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Placeable {

    protected se.orjehag.antecedent.Point position;
    private ArrayList<DragListener> dragListeners = new ArrayList<>();
    protected int width, height;
    private boolean isDragging = false;
    private se.orjehag.antecedent.Point mouseOffset;

    public Placeable(int x, int y) {
        position = new Point(x, y);
    }

    public abstract void draw(Graphics2D g2d);

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
        if (Math.abs(e.getX() - position.x) < width / 2 && Math.abs(e.getY() - position.y) < height / 2) {
            isDragging = true;
            mouseOffset = position.minus(new Point(e.getX(), e.getY()));
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
}
