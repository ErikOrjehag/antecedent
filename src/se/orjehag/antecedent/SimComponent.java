package se.orjehag.antecedent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by erik on 20/03/16.
 */
public class SimComponent extends JComponent {

    private final int WIDTH = 720, HEIGHT = 420;
    private final int GRID_SIZE = 20;
    private BufferedImage gridImage;
    private Simulation simulation;

    public SimComponent() {
        simulation = new Simulation();
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(220, 220, 220));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        drawGrid(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        if (gridImage == null) {
            gridImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            int color = new Color(160, 160, 160).getRGB();
            for (int x = 0; x < WIDTH / GRID_SIZE; x++) {
                for (int y = 0; y < HEIGHT / GRID_SIZE; y++) {
                    gridImage.setRGB(x * GRID_SIZE, y * GRID_SIZE, color);
                }
            }
        }
        g2d.drawImage(gridImage, null, null);
        simulation.draw(g2d);
    }
}
