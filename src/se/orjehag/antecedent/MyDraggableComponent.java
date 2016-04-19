package se.orjehag.antecedent;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import javax.swing.border.*;

public class MyDraggableComponent
        extends JComponent {

    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    private ComponentDrawerItem item;


    public MyDraggableComponent(ComponentDrawerItem item) {

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

        Point p = new Point(item.getX(), item.getY());
        Container parent = item.getParent();
        Point pp = SwingUtilities.convertPoint(parent, p, MyDraggableComponent.this);

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