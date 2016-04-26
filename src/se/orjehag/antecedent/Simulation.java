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
import java.util.List;

/**
 * Created by erik on 31/03/16.
 */
public class Simulation implements Serializable
{
    public List<Placeable> placeables = new ArrayList<>();
    public List<Logical> logicals = new ArrayList<>();
    private InputSocket fromInputSocket = null;
    private OutputSocket fromOutputSocket = null;
    private Point mousePos = new Point();
    private static final Color LOGICAL_HIGH_COLOR = new Color(29, 123, 255);
    private static final Color LOGICAL_LOW_COLOR = Color.WHITE;
    // Needed to propagate changes, 2 works fine but I use 3 for
    // extra safety. Might want to increase to 4 in the future.
    private static final int ITERATIONS_PER_STEP = 3;

    public Simulation() {

    }

    public void step() {
        for (int n = 0; n < ITERATIONS_PER_STEP; n++) {
            for (int i = 0; i < logicals.size(); i++) {
                logicals.get(i).step();
            }
        }
    }

    public void mousePressed(MouseEvent e) {

        for (Logical logical : logicals) {
            boolean shouldBreak = false;
            for (OutputSocket outputSocket : logical.outputs) {
                if (outputSocket.isNear(mousePos)) {
                    fromOutputSocket = outputSocket;
                    shouldBreak = true;
                    break;
                }
            }
            if (shouldBreak) {
                break;
            }
            for (InputSocket inputSocket : logical.inputs) {
                if (inputSocket.isNear(mousePos)) {
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

        step();
    }

    public void mouseReleased(MouseEvent e) {

        for (Logical logical : logicals) {
            boolean shouldBreak = false;
            if (fromInputSocket != null) {
                for (OutputSocket outputSocket : logical.outputs) {
                    if (outputSocket.isNear(mousePos)) {
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
                    if (inputSocket.isNear(mousePos)) {
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

        step();
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
        // Magic number 2 means dividing in half.
        //noinspection MagicNumber
        curve.setCurve(from.x, from.y, (from.x + to.x) / 2.0f,
                from.y, (from.x + to.x) / 2.0f, to.y, to.x, to.y);
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
        // I did not want to use regular for loops because I got into
        // trouble when I tried to iterate over the same list that I'm
        // removing items from. I still had problems when I used Iterable
        // and getNext() but this removeIf() method has solved the problem.
        placeables.removeIf(e -> (e.isSelected()));
        // This is a little hacky. The disconnect method will only be
        // called if isSelected() evaluates to true. Disconnect always
        // returns true.
        logicals.removeIf(e -> (e.isSelected() && e.disconnect()));
    }
}