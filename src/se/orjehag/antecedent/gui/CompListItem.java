package se.orjehag.antecedent.gui;

import se.orjehag.antecedent.SimComponent;
import se.orjehag.antecedent.placable.Placeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseListener;

/**
 * Created by erik on 12/04/16.
 */
public class CompListItem extends JComponent implements MouseListener, MouseMotionListener {

    Dimension size = new Dimension(90, 80);
    Placeable placeable;
    JPanel dragPanel;
    CompListDraggable draggable;
    SimComponent dropTarget;

    public CompListItem(JPanel dragPanel, SimComponent dropTarget, Placeable placeable) {
        setPreferredSize(size);
        this.dragPanel = dragPanel;
        this.dropTarget = dropTarget;
        this.placeable = placeable;
        addMouseListener(this);
        addMouseMotionListener(this);
        //this.placeable = placeClass.getDeclaredConstructor(new Class[] { int.class, int.class }).newInstance(0, 0);
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
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseReleased(final MouseEvent e) {}
    @Override public void mouseClicked(final MouseEvent e) {}
    @Override public void mouseEntered(final MouseEvent e) {}
    @Override public void mouseExited(final MouseEvent e) {}
}
