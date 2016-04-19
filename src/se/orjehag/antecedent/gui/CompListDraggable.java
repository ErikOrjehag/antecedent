package se.orjehag.antecedent.gui;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import javax.swing.border.*;

public class CompListDraggable
        extends JComponent {

    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    private CompListItem item;


    public CompListDraggable(CompListItem item) {

        setBorder(new LineBorder(Color.BLUE, 3));
        setBackground(Color.WHITE);
        setBounds(0, 0, item.size.width, item.size.height);
        setOpaque(true);

        this.item = item;
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("pressed");

        screenX = e.getXOnScreen();
        screenY = e.getYOnScreen();

        // Location of CompListItem.
        Point p = item.getLocation();

        // The parent JPanel with GridLayout inside the JScrollPane.
        Container parent = item.getParent();

        // Convert the local location of the CompListItem to the global location based on its parent.
        Point pp = SwingUtilities.convertPoint(parent, p, CompListDraggable.this);

        // TODO: Make this be a point.
        myX = (int) pp.getX();
        myY = (int) pp.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getXOnScreen() - screenX;
        int deltaY = e.getYOnScreen() - screenY;
        setLocation(myX + deltaX, myY + deltaY);
    }

    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        AffineTransform origTransform = g2d.getTransform();
        g2d.translate(item.size.width / 2, item.size.height / 2);
        item.placeable.draw(g2d);
        g2d.setTransform(origTransform);
    }

}