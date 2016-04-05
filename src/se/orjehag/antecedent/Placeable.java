package se.orjehag.antecedent;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Placeable {

    protected int x, y;
    private ArrayList<DragListener> dragListeners = new ArrayList<>();

    public Placeable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(Graphics2D g2d);

    public void addDragListener(DragListener listener) {
        dragListeners.add(listener);
    }

    public void removeDragListener(DragListener listener) {
        dragListeners.remove(listener);
    }
}
