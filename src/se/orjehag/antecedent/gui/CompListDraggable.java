package se.orjehag.antecedent.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompListDraggable extends JComponent {

    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    private final CompListItem item;
    private BufferedImage plusImage = null;
    private final Logger logger = Logger.getLogger(CompListDraggable.class.getName());

    public CompListDraggable(CompListItem item) {
        this.item = item;

        try {
            URL plusUrl = getClass().getResource("/plus.png");
            if (plusUrl != null) {
                plusImage = ImageIO.read(plusUrl);
            } else {
                throw new FileNotFoundException("The plus icon file was not found!");
            }
        } catch (IOException e) {
            // Its OK to continue, I added a null check before drawing the plusImage.
            logger.log(Level.SEVERE, "Error while trying to load the plus icon.", e);
        }

        setBackground(Color.WHITE);
        setBounds(0, 0, item.size.width, item.size.height);
        setOpaque(true);
    }

    public void mousePressed(MouseEvent e) {
        screenX = e.getXOnScreen();
        screenY = e.getYOnScreen();

        // Location of CompListItem.
        Point p = item.getLocation();

        // The parent JPanel with GridLayout inside the JScrollPane.
        Container parent = item.getParent();

        // Convert the local location of the CompListItem to the global location based on its parent.
        Point pp = SwingUtilities.convertPoint(parent, p, this);

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

        // null check to recover if the image loading failed.
        if (item.isOverTargetArea() && plusImage != null) {
            g2d.drawImage(plusImage, 10, 10, 20, 20, null);
        }

        g2d.setTransform(origTransform);
    }

}