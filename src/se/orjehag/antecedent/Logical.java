package se.orjehag.antecedent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by erik on 31/03/16.
 */
public abstract class Logical extends Placeable {

    public ArrayList<Socket> inputs = new ArrayList<>();
    public ArrayList<Socket> outputs = new ArrayList<>();
    public boolean[] nextOutputs;
    private final int width = 50, height = 50;

    public Logical(int x, int y) {
        super(x, y);
    }

    public abstract boolean[] func(boolean[] inVals);

    public void step() {
        int inLen = inputs.size();
        boolean[] inVals = new boolean[inLen];
        for (int i = 0; i < inLen; i++) {
            inVals[i] = inputs.get(0).connectedTo.get(0).getValue();
        }
        nextOutputs = func(inVals);
    }

    public void commit() {
        assert outputs.size() == nextOutputs.length;
        for (int i = 0; i < outputs.size(); i++) {
            outputs.get(i).setValue(nextOutputs[i]);
        }
    }

    public void draw(Graphics2D g2d) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, width, height);

        int inLen = inputs.size();

        for (int i = 0; i < inLen; i++) {
            g2d.drawRect(-10, height / 2 - 5 - (inLen / 2) * 20 + i * 20, 10, 10);
        }

        int outLen = outputs.size();

        for (int i = 0; i < outLen; i++) {
            g2d.drawRect(width, height / 2 - 5 - (outLen / 2) * 20 + i * 20, 10, 10);
        }

        g2d.setTransform(old);
    }
}
