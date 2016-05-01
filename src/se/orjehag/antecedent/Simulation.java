package se.orjehag.antecedent;

import se.orjehag.antecedent.placeable.Placeable;
import se.orjehag.antecedent.placeable.logical.InputSocket;
import se.orjehag.antecedent.placeable.logical.InteractionListener;
import se.orjehag.antecedent.placeable.logical.Logical;
import se.orjehag.antecedent.placeable.logical.OutputSocket;
import se.orjehag.antecedent.placeable.logical.Socket;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.CubicCurve2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds all current state of the simulation. It's responsible for
 * keeping track of which components are in the simulation, how
 * they are connected to each other and also for calculating the
 * next state of the simulation by stepping forward in time.
 */
public class Simulation implements Serializable, InteractionListener
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

    private List<StepListener> stepListeners = new ArrayList<>();

    // Used to do drag and drop of wires between components.
    private InputSocket fromInputSocket = null;
    private OutputSocket fromOutputSocket = null;

    private Vec2 mousePos = new Vec2();

    private final Logger logger = Logger.getLogger(Simulation.class.getName());

    private static final Color LOGICAL_HIGH_COLOR = new Color(29, 123, 255);
    private static final Color LOGICAL_LOW_COLOR = Color.WHITE;
    // This means that you can't have a circuit depth of more than this value or
    // else the changes will not propagate the circuit all the way though.
    private static final int ITERATIONS_PER_STEP = 10;

    public void step() {
        // Added this log to get a sence of performance.
        // I am currently stepping more oftan than I need to.
        logger.log(Level.INFO, "Simulation step.");
        for (int n = 0; n < ITERATIONS_PER_STEP; n++) {
            for (Logical logical : logicals) {
                logical.step();
            }
            for (Logical logical : logicals) {
                logical.commit();
            }
        }
        notifyStepListeners();
    }

    public void leftMousePressed(Vec2 mousePos) {

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
                        step();
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
            placable.leftMousePressed(mousePos);
        }
    }

    public void leftMouseReleased(Vec2 mousePos) {

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
                        step();
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
                        step();
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
            placable.leftMouseReleased(mousePos);
        }
    }

    public void rightMousePressed(Vec2 mousePos) {
        for (Placeable placable : placeables) {
            placable.rightMousePressed(mousePos);
        }
    }

    public void rightMouseReleased(Vec2 mousePos) {
        for (Placeable placable : placeables) {
            placable.rightMouseReleased(mousePos);
        }
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
            boolean isHigh = fromSocket.getValue();
            drawWire(g2d, from, to, isHigh);
        }

    }

    private void drawWire(Graphics2D g2d, Vec2 from, Vec2 to, boolean high) {
        CubicCurve2D curve = new CubicCurve2D.Float();
        // Magic number 2 means dividing in half.
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
        // Figure out which list to add to with polymorfism,
        // instead of instanceof if statement.
        placeable.addTo(this);
    }

    public void remove(Placeable placeable) {
        placeable.removeFrom(this);
    }

    public void removeAll() {
        // I do this because I cant remove items from the list that I'm currently iterating.
        List<Placeable> toRemove = new ArrayList<>();

        for (Placeable placeable : placeables) {
            toRemove.add(placeable);
        }

        for (Placeable placeable : toRemove) {
            remove(placeable);
        }
    }

    public void addLogical(Logical logical) {
        placeables.add(logical);
        logicals.add(logical);
        logical.addInteractionListener(this);
        logical.onAdd();
    }

    public void addPlacable(Placeable placeable) {
        placeables.add(placeable);
        placeable.onAdd();
    }

    public void removeLogical(Logical logical) {
        placeables.remove(logical);
        logicals.remove(logical);
        logical.onRemove();
    }

    public void removePlacable(Placeable placable) {
        placeables.remove(placable);
        placable.onRemove();
    }

    public void removeSelected() {
        List<Placeable> selected = new ArrayList<>();

        for (Placeable placeable : placeables) {
            if (placeable.isSelected()) {
                selected.add(placeable);
            }
        }

        int len = logicals.size();

        for (Placeable placeable : selected) {
            remove(placeable);
        }

        if (logicals.size() != len) {
            step();
        }
    }

    public void addStepListener(StepListener stepListener) {
        stepListeners.add(stepListener);
    }

    private void notifyStepListeners() {
        stepListeners.forEach(StepListener::onStep);
    }

    @Override public void onInteraction() {
        step();
    }
}