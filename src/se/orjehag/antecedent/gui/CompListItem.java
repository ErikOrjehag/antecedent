package se.orjehag.antecedent.gui;

import se.orjehag.antecedent.placeable.Placeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Item of the scroll pane to the left.
 */
public class CompListItem extends JComponent implements MouseListener, MouseMotionListener {

    public Dimension size = new Dimension(90, 80);
    public Placeable placeable;
    private CompDropTarget dropTarget;
    private JPanel dragPanel;
    private Class<? extends Placeable> placableClass;
    private final Logger logger = Logger.getLogger(CompListItem.class.getName());

    // It is true that this variable may not be initialized
    // but if that is the case we will handle it by catching
    // the exception in the createInstance method and
    // then shutdown the application gracefully.
    private CompListDraggable draggable;


    public CompListItem(JPanel dragPanel, CompDropTarget dropTarget, Class<? extends Placeable> placableClass) {
        setPreferredSize(size);
        this.dragPanel = dragPanel;
        this.dropTarget = dropTarget;
        this.placableClass = placableClass;
        this.placeable = createInstance(0, 0);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private Placeable createInstance(int x, int y) {
        try {
            // All children of Placeable should have a constructor that takes to integers (x and y).
            // If not, that is a programming error that we should fix immediately. That's why I shut
            // down the program in the catch block.
            return placableClass.getConstructor(new Class<?>[] { int.class, int.class }).newInstance(x, y);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.SEVERE, "Error while trying to create a new instance of: " + placableClass.getName(), e);
            JOptionPane.showMessageDialog(this, "A program error occurred, could not recover! Bye Bye!", "Error", JOptionPane.ERROR_MESSAGE);
            // It does not make much sense to recover from this. Better to crash and fix the error.
            System.exit(1);
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
        draggable = new CompListDraggable(this);
        dragPanel.add(draggable);
        draggable.mousePressed(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        draggable.mouseDragged(e);
    }

    @Override public void mouseReleased(final MouseEvent e) {
        if (isOverTargetArea()) {
            Point point = draggable.getLocation();
            int x = (int) (point.getX() + size.width / 2 - dropTarget.getCompDropArea().getX());
            int y = (int) (point.getY() + size.height / 2 - dropTarget.getCompDropArea().getY());
            dropTarget.compDrop(createInstance(x, y));
        }
        dragPanel.remove(draggable);
        dragPanel.repaint();
    }

    public boolean isOverTargetArea() {
        Rectangle area = dropTarget.getCompDropArea();
        Point point = draggable.getLocation();
        point.translate(draggable.getWidth() / 2, draggable.getHeight() / 2);
        return area.contains(point);
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(final MouseEvent e) {}
    @Override public void mouseEntered(final MouseEvent e) {}
    @Override public void mouseExited(final MouseEvent e) {}

    @Override public Dimension getSize() {
        return size;
    }
}