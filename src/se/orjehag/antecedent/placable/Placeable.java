package se.orjehag.antecedent.placable;

import se.orjehag.antecedent.*;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import se.orjehag.antecedent.Point;
import se.orjehag.antecedent.placable.logical.Logical;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;


/**
 * Created by erik on 31/03/16.
 */
public abstract class Placeable implements Serializable
{
    protected Point position;
    protected int width, height;
    private boolean isDragging = false;
    private Point mouseOffset;
    private boolean isSelected = false;

    public Placeable(int x, int y, int width, int height) {
        position = new Point(x, y);
        this.width = width;
        this.height = height;
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

    public Point getPosition() {
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
            Point mousePosition = new Point(e.getX(), e.getY());
            position.set(mousePosition.plus(mouseOffset));
        }
    }

    public void addTo(List<Placeable> placeables, List<Logical> logicals) {
        placeables.add(this);
    }

    public boolean isSelected() {
        return isSelected;
    }
}
