package se.orjehag.antecedent.placeable.logical;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import se.orjehag.antecedent.Simulation;
import se.orjehag.antecedent.Vec2;
import se.orjehag.antecedent.placeable.Placeable;

import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parent class of all logical components such as
 * 4-bit display or AND, OR, NOT gates.
 */
public abstract class Logical extends Placeable {

    public List<InputSocket> inputs = new ArrayList<>();
    public List<OutputSocket> outputs = new ArrayList<>();
    private boolean[] nextOutputs = null;
    // I dont want this to be a Collection.
    private List<InteractionListener> interactionListeners = new ArrayList<>();

    private final Logger logger = Logger.getLogger(Logical.class.getName());

    protected Logical(int x, int y, int width, int height) {
        // Magic number 50 is default height and width.
        super(x, y, (width == -1 ? 50 : width), (height == -1 ? 50 : height));
    }

    protected Logical(int x, int y) {
        this(x, y, -1, -1);
    }

    public abstract boolean[] func(boolean[] in);

    protected void addInputs(int n) {
        for (int i = 0; i < n; i++) {
            inputs.add(new InputSocket(this));
        }
    }

    protected void addOutputs(int n) {
        for (int i = 0; i < n; i++) {
            outputs.add(new OutputSocket(this));
        }
    }

    public void step() {
        int inLen = inputs.size();
        boolean[] in = new boolean[inLen];
        for (int i = 0; i < inLen; i++) {
            in[i] = inputs.get(i).getValue();
        }
        nextOutputs = func(in);
    }

    public void commit() {
        assert outputs.size() == nextOutputs.length;
        for (int i = 0; i < outputs.size(); i++) {
            outputs.get(i).setValue(nextOutputs[i]);
        }
    }

    public Vec2 relativeSocketPosition(Socket socket) {
        // This is not suspicious and not a bug. The method is used
        // to get the position of a socket relative to its Logical
        // owner. This assertion was added because you are not allowed
        // to call this method on an instance of Logical that does not
        // own the socket.
        assert inputs.contains(socket) || outputs.contains(socket);

        boolean isInput = inputs.contains(socket);
        int index = isInput ? inputs.indexOf(socket) : outputs.indexOf(socket);
        int len = isInput ? inputs.size() : outputs.size();
        int x = (width / 2 + 10) * (isInput ? -1 : 1);
        final int spacing = 15;
        // Magic number 2 means dividing in half.
        int y = (int)(index * spacing + (len - 1) / 2.0f * -spacing);
        return new Vec2(x, y);
    }

    public void draw(Graphics2D g2d) {
        super.draw(g2d);

        AffineTransform old = g2d.getTransform();
        g2d.translate(position.x, position.y);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(-width / 2, -height / 2, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(-width / 2, -height / 2, width, height);

        String label = getLabel();
        int labelWidth = g2d.getFontMetrics().stringWidth(label);
        g2d.drawString(getLabel(), -labelWidth/2, 0);

        // Concatenate inputs and outputs to reduce code
        // duplication during drawing because they look the same.
        List<Socket> sockets = new ArrayList<>();
        sockets.addAll(inputs);
        sockets.addAll(outputs);

        for (Socket socket : sockets) {
            // This is not suspicious, we need to know if its an input or output.
            boolean isInput = inputs.contains(socket);
            Vec2 pos = relativeSocketPosition(socket);
            g2d.drawLine(pos.x, pos.y, pos.x + 10 * (isInput ? 1 : -1), pos.y);
            g2d.fillOval(pos.x - 5, pos.y - 5, 5 * 2, 5 * 2);
        }

        g2d.setTransform(old);
    }

    protected abstract String getLabel();

    // This method always returns true so that it can be used in an expression like:
    // logical.isSelected() && logical.disconnect()
    public void disconnect() {
        inputs.forEach(InputSocket::disconnect);
        outputs.forEach(OutputSocket::disconnect);
    }

    public void addInteractionListener(InteractionListener interactionListener) {
        interactionListeners.add(interactionListener);
    }

    public void notifyInteractionListeners() {
        interactionListeners.forEach(InteractionListener::onInteraction);
    }

    @Override
    public void addTo(Simulation simulation) {
        logger.log(Level.INFO, "Adding logical.");
        simulation.addLogical(this);
    }

    @Override
    public void removeFrom(Simulation simulation) {
        logger.log(Level.INFO, "Removing logical.");
        simulation.removeLogical(this);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        disconnect();
    }
}
