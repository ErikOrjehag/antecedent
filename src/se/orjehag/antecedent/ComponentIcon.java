package se.orjehag.antecedent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by erik on 12/04/16.
 */
public class ComponentIcon extends JComponent {

    Dimension size = new Dimension(90, 80);
    Placeable placeable;

    public ComponentIcon(Placeable placeable) {
        setPreferredSize(size);
        this.placeable = placeable;
        //this.placeable = placeClass.getDeclaredConstructor(new Class[] { int.class, int.class }).newInstance(0, 0);
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

}
