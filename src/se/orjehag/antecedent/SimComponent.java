package se.orjehag.antecedent;

import se.orjehag.antecedent.gui.CompDropTarget;
import se.orjehag.antecedent.placable.Placeable;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.dnd.DropTargetListener;
import java.io.*;

/**
 * Created by erik on 20/03/16.
 */
public class SimComponent extends JComponent implements MouseListener, MouseMotionListener, CompDropTarget {

    private final int GRID_SIZE = 20;
    private Simulation simulation = new Simulation();
    private final static Color BACKGROUND_COLOR = new Color(220, 220, 220);
    private final static Color GRID_COLOR = new Color(160, 160, 160);

    public SimComponent() {

        addMouseListener(this);
        addMouseMotionListener(this);

        Action removeSelected = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulation.removeSelected();
                repaint();
            }
        };

        getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "removeSelected");
        getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "removeSelected");
        getActionMap().put("removeSelected", removeSelected);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        drawGrid(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BufferedImage gridImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        int color = GRID_COLOR.getRGB();
        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
            for (int y = 0; y < getHeight(); y += GRID_SIZE) {
                gridImage.setRGB(x, y, color);
            }
        }
        g2d.drawImage(gridImage, null, null);

        simulation.draw(g2d);
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Simulation getSimulation() {
        return simulation;
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
