package se.orjehag.antecedent;

import se.orjehag.antecedent.gui.CompDropTarget;
import se.orjehag.antecedent.placeable.Placeable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * The JComponent that contains the simulation. Responsible for
 * mouse input and drawing the background. Also implements the
 * drop interface for the drag and drop functionality. Holds
 * an instance of Simulation which can be set with a setter
 * to show a different circuit.
 */
public class SimComponent extends JComponent implements MouseListener, MouseMotionListener, CompDropTarget, StepListener {

    private Simulation simulation = null;
    private final static Color BACKGROUND_COLOR = new Color(220, 220, 220);
    private final static Color GRID_COLOR = new Color(160, 160, 160);
    private final static int GRID_SIZE = 20;

    public SimComponent() {
        addMouseListener(this);
        addMouseMotionListener(this);
        setSimulation(new Simulation());
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

    public void setSimulation(Simulation sim) {
        if (simulation != null) {
            simulation.removeAll();
        }
        simulation = sim;
        simulation.addStepListener(this);
        simulation.step();
    }

    public Simulation getSimulation() {
        return simulation;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        simulation.mouseMoved(new Vec2(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        simulation.mouseMoved(new Vec2(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            simulation.leftMousePressed(new Vec2(e.getX(), e.getY()));
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            simulation.rightMousePressed(new Vec2(e.getX(), e.getY()));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            simulation.leftMouseReleased(new Vec2(e.getX(), e.getY()));
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            simulation.rightMouseReleased(new Vec2(e.getX(), e.getY()));
        }
        repaint();
    }

    @Override
    public Rectangle getCompDropArea() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override public void compDrop(Placeable placeable) {
        simulation.add(placeable);
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override public void onStep() {
        repaint();
    }
}
