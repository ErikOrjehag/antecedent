package se.orjehag.antecedent;

import se.orjehag.antecedent.logical.InputSocket;
import se.orjehag.antecedent.logical.Logical;
import se.orjehag.antecedent.logical.OutputSocket;
import se.orjehag.antecedent.logical.gate.AndGate;
import se.orjehag.antecedent.logical.gate.NotGate;
import se.orjehag.antecedent.logical.input.*;
import se.orjehag.antecedent.logical.input.Button;
import se.orjehag.antecedent.logical.output.Lamp;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;

/**
 * Created by erik on 31/03/16.
 */
public class Simulation {

    public ArrayList<Placeable> placeables = new ArrayList<>();
    public ArrayList<Logical> logicals = new ArrayList<>();

    public Simulation() {
        Label label = new Label(150, 50, "Test");
        placeables.add(label);

        High high1 = new High(200, 300);
        placeables.add(high1);
        logicals.add(high1);

        Low low = new Low(250, 60);
        placeables.add(low);
        logicals.add(low);

        AndGate and = new AndGate(350, 200);
        placeables.add(and);
        logicals.add(and);

        Lamp lamp = new Lamp(600, 250);
        placeables.add(lamp);
        logicals.add(lamp);

        se.orjehag.antecedent.logical.input.Button button = new Button(50, 100);
        placeables.add(button);
        logicals.add(button);

        Lamp lamp2 = new Lamp(600, 100);
        placeables.add(lamp2);
        logicals.add(lamp2);

        NotGate not = new NotGate(470, 160);
        placeables.add(not);
        logicals.add(not);

        and.inputs.get(1).connectTo(high1.outputs.get(0));
        lamp.inputs.get(0).connectTo(and.outputs.get(0));
        and.inputs.get(0).connectTo(button.outputs.get(0));
        not.inputs.get(0).connectTo(and.outputs.get(0));
        lamp2.inputs.get(0).connectTo(not.outputs.get(0));

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
        for (Placeable placable : placeables) {
            placable.mousePressed(e);
        }
        step();
    }

    public void mouseReleased(MouseEvent e) {
        for (Placeable placable : placeables) {
            placable.mouseReleased(e);
        }
        step();
    }

    public void mouseMoved(MouseEvent e) {
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
                OutputSocket output = input.connectedTo();
                if (output != null) {
                    Point from = output.getPosition();
                    Point to = input.getPosition();
                    boolean high = output.getValue();
                    drawWire(g2d, from, to, high);
                }
            }
        }

        drawWire(g2d, new Point(20, 20), new Point(200, 5), false);

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

}
