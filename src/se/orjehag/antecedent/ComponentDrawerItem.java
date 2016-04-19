package se.orjehag.antecedent;

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
public class ComponentDrawerItem extends JComponent implements MouseListener, MouseMotionListener {

    Dimension size = new Dimension(90, 80);
    Placeable placeable;
    ComponentGrabListener grabListener;
    JPanel parentPanel;
    JScrollPane scrollPane;

    public ComponentDrawerItem(JScrollPane scrollPane, JPanel parentPanel, JPanel dragPanel, SimComponent dropTarget, ComponentGrabListener grabListener, Placeable placeable) {
        setPreferredSize(size);
        this.grabListener = grabListener;
        this.placeable = placeable;
        this.parentPanel = parentPanel;
        this.scrollPane = scrollPane;
        //this.placeable = placeClass.getDeclaredConstructor(new Class[] { int.class, int.class }).newInstance(0, 0);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform origTransform = g2d.getTransform();
        g2d.translate(size.width / 2, size.height / 2);
        placeable.draw(g2d);
        g2d.setTransform(origTransform);
    }


    @Override public void mousePressed(final MouseEvent e) {
        grabListener.grabComponent(this);
        grabListener.mousePressed(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        grabListener.mouseDragged(e);
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseReleased(final MouseEvent e) {}
    @Override public void mouseClicked(final MouseEvent e) {}
    @Override public void mouseEntered(final MouseEvent e) {}
    @Override public void mouseExited(final MouseEvent e) {}
}
