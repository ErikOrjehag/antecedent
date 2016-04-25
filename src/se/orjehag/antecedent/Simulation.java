package se.orjehag.antecedent;

import se.orjehag.antecedent.placable.Placeable;
import se.orjehag.antecedent.placable.logical.InputSocket;
import se.orjehag.antecedent.placable.logical.Logical;
import se.orjehag.antecedent.placable.logical.OutputSocket;
import se.orjehag.antecedent.placable.logical.Socket;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by erik on 31/03/16.
 */
public class Simulation implements Serializable
{
    public ArrayList<Placeable> placeables = new ArrayList<>();
    public ArrayList<Logical> logicals = new ArrayList<>();
    private InputSocket fromInputSocket = null;
    private OutputSocket fromOutputSocket = null;
    private Point mousePos = new Point();
    private static final Color LOGICAL_HIGH_COLOR = new Color(29, 123, 255);
    private static final Color LOGICAL_LOW_COLOR = Color.WHITE;

    public Simulation() {
        step();
    }

    public void step() {
        System.out.println("step");

        for (int n = 0; n < 2; n++) {
            for (int i = 0; i < logicals.size(); i++) {
                logicals.get(i).step();
            }
        }
    }

    public void mousePressed(MouseEvent e) {

        for (Logical logical : logicals) {
            boolean shouldBreak = false;
            for (OutputSocket outputSocket : logical.outputs) {
                if (outputSocket.contains(mousePos)) {
                    fromOutputSocket = outputSocket;
                    shouldBreak = true;
                    break;
                }
            }
            if (shouldBreak) {
                break;
            }
            for (InputSocket inputSocket : logical.inputs) {
                if (inputSocket.contains(mousePos)) {
                    if (inputSocket.isConnected()) {
                        fromOutputSocket = inputSocket.getConnectedTo();
                        inputSocket.disconnect();
                    } else {
                        fromInputSocket = inputSocket;
                    }
                    shouldBreak = true;
                    break;
                }
            }
            if (shouldBreak) {
                break;
            }
        }

        for (Placeable placable : placeables) {
            placable.mousePressed(e);
        }

        step(); // TODO
    }

    public void mouseReleased(MouseEvent e) {

        for (Logical logical : logicals) {
            boolean shouldBreak = false;
            if (fromInputSocket != null) {
                for (OutputSocket outputSocket : logical.outputs) {
                    if (outputSocket.contains(mousePos)) {
                        fromInputSocket.connectTo(outputSocket);
                        shouldBreak = true;
                        break;
                    }
                }
                if (shouldBreak) {
                    break;
                }
            } else if (fromOutputSocket != null) {
                for (InputSocket inputSocket : logical.inputs) {
                    if (inputSocket.contains(mousePos)) {
                        inputSocket.connectTo(fromOutputSocket);
                        shouldBreak = true;
                        break;
                    }
                }
                if (shouldBreak) {
                    break;
                }
            }
        }

        fromInputSocket = null;
        fromOutputSocket = null;

        for (Placeable placable : placeables) {
            placable.mouseReleased(e);
        }

        step(); // TODO
    }

    public void mouseMoved(MouseEvent e) {

        mousePos.x = e.getX();
        mousePos.y = e.getY();

        for (Placeable placable : placeables) {
            placable.mouseMoved(e);
        }
    }

    public void draw(Graphics2D g2d) {

        g2d.setStroke(new BasicStroke(1));

        // Draw placable items that are on the board.
        for (Placeable placeable : placeables) {
            placeable.draw(g2d);
        }

        // Draw the wires between the logical components.
        for (Logical logical : logicals) {
            for (InputSocket input : logical.inputs) {
                OutputSocket output = input.getConnectedTo();
                if (output != null) {
                    Point from = output.getPosition();
                    Point to = input.getPosition();
                    boolean high = output.getValue();
                    drawWire(g2d, from, to, high);
                }
            }
        }

        Socket fromSocket = fromInputSocket != null ? fromInputSocket : fromOutputSocket;

        if (fromSocket != null) {
            drawWire(g2d, fromSocket.getPosition(), mousePos, false);
        }

    }

    private void drawWire(Graphics2D g2d, Point from, Point to, boolean high) {
        CubicCurve2D curve = new CubicCurve2D.Float();
        curve.setCurve(from.x, from.y, (from.x + to.x) / 2,
                from.y, (from.x + to.x) / 2, to.y, to.x, to.y);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(curve);
        g2d.setColor(high ? LOGICAL_HIGH_COLOR : LOGICAL_LOW_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(curve);
    }

    public void add(Placeable placeable) {
        placeable.addTo(placeables, logicals);
    }

    public void removeSelected() {
        placeables.removeIf(e -> (e.isSelected()));
        logicals.removeIf(e -> (e.isSelected() && e.disconnect()));
    }
}