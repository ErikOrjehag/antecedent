package se.orjehag.antecedent.gui;

import se.orjehag.antecedent.placable.Placeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by erik on 12/04/16.
 */
public class CompListItem extends JComponent implements MouseListener, MouseMotionListener {

    Dimension size = new Dimension(90, 80);
    Placeable placeable;
    JPanel dragPanel;
    CompListDraggable draggable;
    CompDropTarget dropTarget;
    Class<? extends Placeable> placableClass;

    public CompListItem(JPanel dragPanel, CompDropTarget dropTarget, Class<? extends Placeable> placableClass) {
        setPreferredSize(size);
        this.dragPanel = dragPanel;
        this.dropTarget = dropTarget;
        this.placableClass = placableClass;
        this.placeable = createPlacableInstance(0, 0);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private Placeable createPlacableInstance(int x, int y) {
        try {
            return placableClass.getConstructor(new Class[] { int.class, int.class }).newInstance(x, y);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform origTransform = g2d.getTransform();
        g2d.translate(size.width / 2, size.height / 2);
        placeable.draw(g2d);
        g2d.setTransform(origTransform);
    }


    @Override public void mousePressed(final MouseEvent e) {
        System.out.println("Grabbed item: " + this);
        draggable = new CompListDraggable(this);
        dragPanel.add(draggable);
        draggable.mousePressed(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        draggable.mouseDragged(e);
        Rectangle area = dropTarget.getCompDropArea();
        // System.out.println("x: " + area.getX() + ", y: " + area.getY() + ", w: " + area.getWidth() + ", h: " + area.getHeight());
        Point point = draggable.getLocation();

        if (area.contains(point)) {
            System.out.println("Over!");
        }
    }

    @Override public void mouseReleased(final MouseEvent e) {
        Rectangle area = dropTarget.getCompDropArea();
        Point point = draggable.getLocation();

        if (area.contains(point)) {
            System.out.println("Drop!");
            dropTarget.compDrop(createPlacableInstance((int) (point.getX() + size.width / 2 - dropTarget.getCompDropArea().getX()), (int) (point.getY() + size.height / 2 - dropTarget.getCompDropArea().getY())));
        }

        dragPanel.remove(draggable);
        dragPanel.repaint();
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(final MouseEvent e) {}
    @Override public void mouseEntered(final MouseEvent e) {}
    @Override public void mouseExited(final MouseEvent e) {}
}
