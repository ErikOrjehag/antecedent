package se.orjehag.antecedent;

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
public class SimComponent extends JComponent implements MouseListener, MouseMotionListener, DropTargetListener {

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

        System.out.println(getWidth() + " " + getHeight());
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
    public void mouseClicked(MouseEvent e) {

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
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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

    @Override public void dragEnter(final DropTargetDragEvent dtde) {
        System.out.println("drag enter");
    }

    @Override public void dragOver(final DropTargetDragEvent dtde) {
        System.out.println("drag over");

    }

    @Override public void dropActionChanged(final DropTargetDragEvent dtde) {
        System.out.println("drop action changed");

    }

    @Override public void dragExit(final DropTargetEvent dte) {
        System.out.println("drag exit");

    }

    @Override public void drop(final DropTargetDropEvent dtde) {
        System.out.println("drop");

    }
}
