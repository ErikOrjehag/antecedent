package se.orjehag.antecedent;

import se.orjehag.antecedent.placable.logical.Placeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseListener;

/**
 * Created by erik on 12/04/16.
 */
public class ComponentDrawerItem extends JComponent implements MouseListener {

    Dimension size = new Dimension(90, 80);
    Placeable placeable;
    ComponentGrabListener grabListener;

    public ComponentDrawerItem(ComponentGrabListener grabListener, Placeable placeable) {
        setPreferredSize(size);
        this.grabListener = grabListener;
        this.placeable = placeable;
        //this.placeable = placeClass.getDeclaredConstructor(new Class[] { int.class, int.class }).newInstance(0, 0);
        addMouseListener(this);
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        //g2d.setColor(new Color(200, 200, 200));
        //g2d.fillRect(0, 0, size.width, size.height);

        AffineTransform origTransform = g2d.getTransform();
        g2d.translate(size.width / 2, size.height / 2);
        placeable.draw(g2d);
        g2d.setTransform(origTransform);
    }

    @Override public void mouseClicked(final MouseEvent e) {

    }

    @Override public void mousePressed(final MouseEvent e) {
        grabListener.grabComponent(this);
    }

    @Override public void mouseReleased(final MouseEvent e) {

    }

    @Override public void mouseEntered(final MouseEvent e) {

    }

    @Override public void mouseExited(final MouseEvent e) {

    }
}
