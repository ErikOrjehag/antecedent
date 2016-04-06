package se.orjehag.antecedent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Placeable {

    protected Point position;
    private ArrayList<DragListener> dragListeners = new ArrayList<>();
    protected int width, height;
    private boolean isDragging = false;
    private Point mouseOffset;

    public Placeable(int x, int y, int width, int height) {
        position = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    public abstract void draw(Graphics2D g2d);

    public void addDragListener(DragListener listener) {
        dragListeners.add(listener);
    }

    public void removeDragListener(DragListener listener) {
        dragListeners.remove(listener);
    }

    public Point getPosition() {
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
            Point mousePosition = new Point(e.getX(), e.getY());
            position.set(mousePosition.plus(mouseOffset));
        }
    }
}
