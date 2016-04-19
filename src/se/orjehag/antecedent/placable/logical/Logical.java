package se.orjehag.antecedent.placable.logical;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import se.orjehag.antecedent.Point;
import se.orjehag.antecedent.placable.Placeable;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Logical extends Placeable {

    public ArrayList<InputSocket> inputs = new ArrayList<>();
    public ArrayList<OutputSocket> outputs = new ArrayList<>();

    public Logical(int x, int y) {
        super(x, y);
        width = 50;
        height = 50;
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
        assert inputs.contains(socket) || outputs.contains(socket);

        boolean isInput = inputs.contains(socket);
        int index = isInput ? inputs.indexOf(socket) : outputs.indexOf(socket);
        int len = isInput ? inputs.size() : outputs.size();
        int x = (width / 2 + 10) * (isInput ? -1 : 1);
        int y = (int)(index * 15 + (len - 1) / 2f * -15);
        return new Point(x, y);
    }

    public void draw(Graphics2D g2d) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(position.x, position.y);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(-width / 2, -height / 2, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(-width / 2, -height / 2, width, height);

        int inLen = inputs.size();

        for (int i = 0; i < inLen; i++) {
            int x = -width / 2;
            int y = (int)(i * 15 + (inLen - 1) / 2f * -15);
            g2d.drawLine(x, y, x - 10, y);
            g2d.fillOval(x - 15, y - 5, 10, 10);
        }

        int outLen = outputs.size();

        for (int i = 0; i < outLen; i++) {
            int x = width / 2;
            int y = (int)(i * 20 + (outLen - 1) / 2f * -20);
            g2d.drawLine(x, y, x + 10, y);
            g2d.fillOval(x + 5, y - 5, 10, 10);
        }

        g2d.setTransform(old);
    }

    @Override
    public void addTo(ArrayList<Placeable> placeables, ArrayList<Logical> logicals) {
        placeables.add(this);
        logicals.add(this);
    }
}
