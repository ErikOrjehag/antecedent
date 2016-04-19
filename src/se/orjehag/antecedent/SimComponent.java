package se.orjehag.antecedent;

import se.orjehag.antecedent.gui.CompDropTarget;
import se.orjehag.antecedent.placable.Placeable;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.dnd.DropTargetListener;

/**
 * Created by erik on 20/03/16.
 */
public class SimComponent extends JComponent implements MouseListener, MouseMotionListener, CompDropTarget {

    private final int GRID_SIZE = 20;
    private Simulation simulation;

    public SimComponent() {
        simulation = new Simulation();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(220, 220, 220));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        drawGrid(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BufferedImage gridImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        int color = new Color(160, 160, 160).getRGB();
        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
            for (int y = 0; y < getHeight(); y += GRID_SIZE) {
                gridImage.setRGB(x, y, color);
            }
        }
        g2d.drawImage(gridImage, null, null);

        simulation.draw(g2d);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        simulation.mouseMoved(e);
        repaint(); // TODO
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        simulation.mouseMoved(e);
        repaint(); // TODO
    }

    @Override
    public void mousePressed(MouseEvent e) {
        simulation.mousePressed(e);
        repaint(); // TODO
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        simulation.mouseReleased(e);
        repaint(); // TODO
    }

    @Override
    public Rectangle getCompDropArea() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void compDrop(Placeable placable) {
        simulation.add(placable);
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
