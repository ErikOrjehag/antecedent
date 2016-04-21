package se.orjehag.antecedent;

import se.orjehag.antecedent.placable.Placeable;
import se.orjehag.antecedent.placable.Text;
import se.orjehag.antecedent.placable.logical.InputSocket;
import se.orjehag.antecedent.placable.logical.Logical;
import se.orjehag.antecedent.placable.logical.OutputSocket;
import se.orjehag.antecedent.placable.logical.Socket;
import se.orjehag.antecedent.placable.logical.gate.AndGate;
import se.orjehag.antecedent.placable.logical.gate.OrGate;
import se.orjehag.antecedent.placable.logical.gate.XOrGate;
import se.orjehag.antecedent.placable.logical.input.Switch;
import se.orjehag.antecedent.placable.logical.output.Lamp;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by erik on 31/03/16.
 */
public class Simulation implements java.io.Serializable {

    public ArrayList<Placeable> placeables = new ArrayList<>();
    public ArrayList<Logical> logicals = new ArrayList<>();
    private InputSocket fromInputSocket = null;
    private OutputSocket fromOutputSocket = null;
    private Point mousePos = new Point();

    public Simulation() {
        test2();
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
        g2d.setColor(high ? new Color(29, 123, 255) : Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(curve);
    }

    public void test2() {
        Text cinLabel = new Text(100, 50);
        add(cinLabel);

        Switch cin = new Switch(100, 100);
        add(cin);

        Text iaLabel = new Text(100, 150);
        add(iaLabel);

        Switch ia = new Switch(100, 200);
        add(ia);

        Text ibLabel = new Text(100, 250);
        add(ibLabel);

        Switch ib = new Switch(100, 300);
        add(ib);

        XOrGate xor1 = new XOrGate(300, 200);
        add(xor1);
        xor1.inputs.get(0).connectTo(ia.outputs.get(0));
        xor1.inputs.get(1).connectTo(ib.outputs.get(0));

        AndGate and1 = new AndGate(300, 300);
        add(and1);
        and1.inputs.get(0).connectTo(ia.outputs.get(0));
        and1.inputs.get(1).connectTo(ib.outputs.get(0));

        XOrGate xor2 = new XOrGate(400, 200);
        add(xor2);
        xor2.inputs.get(0).connectTo(cin.outputs.get(0));
        xor2.inputs.get(1).connectTo(xor1.outputs.get(0));

        AndGate and2 = new AndGate(400, 300);
        add(and2);
        and2.inputs.get(0).connectTo(cin.outputs.get(0));
        and2.inputs.get(1).connectTo(xor1.outputs.get(0));

        OrGate or = new OrGate(500, 300);
        add(or);
        or.inputs.get(0).connectTo(and2.outputs.get(0));
        or.inputs.get(1).connectTo(and1.outputs.get(0));

        Lamp sum = new Lamp(600, 200);
        add(sum);
        sum.inputs.get(0).connectTo(xor2.outputs.get(0));

        Lamp cout = new Lamp(600, 300);
        add(cout);
        cout.inputs.get(0).connectTo(or.outputs.get(0));
    }

    public void add(Placeable placeable) {
        placeable.addTo(placeables, logicals);
    }

    public void removeSelected() {
        placeables.removeIf(e -> (e.isSelected()));
        logicals.removeIf(e -> (e.isSelected() && e.disconnect()));
    }

}
