package se.orjehag.antecedent;

import se.orjehag.antecedent.placeable.Placeable;
import se.orjehag.antecedent.placeable.logical.InputSocket;
import se.orjehag.antecedent.placeable.logical.Logical;
import se.orjehag.antecedent.placeable.logical.OutputSocket;
import se.orjehag.antecedent.placeable.logical.Socket;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.CubicCurve2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds all current state of the simulation. It's responsible for
 * keeping track of which components are in the simulation, how
 * they are connected to each other and also for calculating the
 * next state of the simulation by stepping forward in time.
 */
public class Simulation implements Serializable
{
    /**
     * A list of all placeable components in the simulation.
     */
    private List<Placeable> placeables = new ArrayList<>();
    /**
     * A list of the logical components, this should always be
     * a subset of the placables list because all logicals are
     * placables (Logical extends Placable).
     */
    private List<Logical> logicals = new ArrayList<>();

    // Used to do drag and drop of wires between components.
    private InputSocket fromInputSocket = null;
    private OutputSocket fromOutputSocket = null;

    private Vec2 mousePos = new Vec2();

    private final Logger logger = Logger.getLogger(Simulation.class.getName());

    private static final Color LOGICAL_HIGH_COLOR = new Color(29, 123, 255);
    private static final Color LOGICAL_LOW_COLOR = Color.WHITE;
    // Needed to propagate changes, 2 works fine but I use 3 for
    // extra safety. Might want to increase to 4 in the future.
    private static final int ITERATIONS_PER_STEP = 3;

    public void step() {
        // Added this log to get a sence of performance.
        // I am currently stepping more oftan than I need to.
        logger.log(Level.INFO, "Simulation step.");

        for (int n = 0; n < ITERATIONS_PER_STEP; n++) {
            for (Logical logical : logicals) {
                logical.step();
            }
        }
    }

    public void mousePressed(Vec2 mousePos) {

        // Wire drag and drop logic follows:
        for (Logical logical : logicals) {
            boolean shouldBreak = false;

            // If the mouse is pressed near an output we
            // want to keep track of that socket.
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

            // If the mouse is pressed near an input we
            // want to keep track of that socket.
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

        // Let all placables know about the keypress.
        // Buttons might want to know that they are
        // clicked on for example.
        for (Placeable placable : placeables) {
            placable.mousePressed(mousePos);
        }

        // The mouse press might have effected the
        // simulation state so lets step.
        step();
    }

    public void mouseReleased(Vec2 mousePos) {

        // Wire drag and drop logic follows:
        for (Logical logical : logicals) {
            boolean shouldBreak = false;

            // If we release near an output and we are
            // currently keeping track of an input that
            // has been pressed on we want to connect them.
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

                // Same but the other way around...
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

        // Let all placables know about the release.
        // Buttons might want to swich off for example.
        for (Placeable placable : placeables) {
            placable.mouseReleased(mousePos);
        }

        // The release could have effected the simulation
        // so lets step!
        step();
    }

    public void mouseMoved(Vec2 mousePos) {
        this.mousePos.set(mousePos);
        for (Placeable placable : placeables) {
            placable.mouseMoved(mousePos);
        }
    }

    public void draw(Graphics2D g2d) {

        g2d.setStroke(new BasicStroke(1));

        // Draw placeable items that are on the board.
        for (Placeable placeable : placeables) {
            placeable.draw(g2d);
        }

        // Draw the wires between the logical components.
        for (Logical logical : logicals) {
            for (InputSocket input : logical.inputs) {
                OutputSocket output = input.getConnectedTo();
                if (output != null) {
                    Vec2 from = output.getPosition();
                    Vec2 to = input.getPosition();
                    boolean high = output.getValue();
                    drawWire(g2d, from, to, high);
                }
            }
        }

        // Draw drag and drop connection wire.
        Socket fromSocket = fromInputSocket != null ? fromInputSocket : fromOutputSocket;
        if (fromSocket != null) {
            Vec2 from = fromSocket.getPosition();
            Vec2 to = mousePos;
            drawWire(g2d, from, to, false);
        }

    }

    private void drawWire(Graphics2D g2d, Vec2 from, Vec2 to, boolean high) {
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
        // Figure out which list to add to with polymorfism.
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