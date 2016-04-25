package se.orjehag.antecedent.placable.logical;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import se.orjehag.antecedent.Point;
import se.orjehag.antecedent.placable.Placeable;

import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Logical extends Placeable {

    public List<InputSocket> inputs = new ArrayList<>();
    public List<OutputSocket> outputs = new ArrayList<>();

    protected Logical(int x, int y, int width, int height) {
        // Magic number 50 is default height and width.
        //noinspection MagicNumber
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

        boolean[] out = func(in);

        assert outputs.size() == out.length;

        for (int i = 0; i < outputs.size(); i++) {
            outputs.get(i).setValue(out[i]);
        }
    }

    public Point relativeSocketPosition(Socket socket) {
        // This is not suspicious and not a bug. The method is used
        // to get the position of a socket relative to its Logical
        // owner. This assertion was added because you are not allowed
        // to call this method on an instance of Logical that does not
        // own the socket.
        //noinspection SuspiciousMethodCalls
        assert inputs.contains(socket) || outputs.contains(socket);

        //noinspection SuspiciousMethodCalls
        boolean isInput = inputs.contains(socket);
        //noinspection SuspiciousMethodCalls
        int index = isInput ? inputs.indexOf(socket) : outputs.indexOf(socket);
        int len = isInput ? inputs.size() : outputs.size();
        int x = (width / 2 + 10) * (isInput ? -1 : 1);
        final int spacing = 15;
        // Magic number 2 means dividing in half.
        //noinspection MagicNumber
        int y = (int)(index * spacing + (len - 1) / 2.0f * -spacing);
        return new Point(x, y);
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
        int len = sockets.size();

        for (int i = 0; i < len; i++) {
            // This is not suspicious, we need to know if its an input or output.
            //noinspection SuspiciousMethodCalls
            boolean isInput = inputs.contains(sockets.get(i));
            Point pos = relativeSocketPosition(sockets.get(i));
            g2d.drawLine(pos.x, pos.y, pos.x + 10 * (isInput ? 1 : -1), pos.y);
            g2d.fillOval(pos.x - 5, pos.y - 5, 5 * 2, 5 * 2);
        }

        g2d.setTransform(old);
    }

    protected abstract String getLabel();

    public boolean disconnect() {
        inputs.forEach(InputSocket::disconnect);
        outputs.forEach(OutputSocket::disconnect);
        return true;
    }

    @Override
    public void addTo(List<Placeable> placeables, List<Logical> logicals) {
        placeables.add(this);
        logicals.add(this);
    }
}
