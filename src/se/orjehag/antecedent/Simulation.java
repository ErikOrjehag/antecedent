package se.orjehag.antecedent;

import se.orjehag.antecedent.logical.InputSocket;
import se.orjehag.antecedent.logical.Logical;
import se.orjehag.antecedent.logical.OutputSocket;
import se.orjehag.antecedent.logical.gate.AndGate;
import se.orjehag.antecedent.logical.gate.NotGate;
import se.orjehag.antecedent.logical.gate.OrGate;
import se.orjehag.antecedent.logical.gate.XOrGate;
import se.orjehag.antecedent.logical.input.*;
import se.orjehag.antecedent.logical.input.Button;
import se.orjehag.antecedent.logical.output.FourBitDisplay;
import se.orjehag.antecedent.logical.output.Lamp;
import sun.rmi.runtime.Log;

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

    public void test1() {
        Label label = new Label(150, 50, "Test");
        placeables.add(label);

        High high1 = new High(200, 300);
        placeables.add(high1);
        logicals.add(high1);

        Low low = new Low(250, 370);
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

        FourBitDisplay display = new FourBitDisplay(600, 350);
        placeables.add(display);
        logicals.add(display);

        and.inputs.get(1).connectTo(high1.outputs.get(0));
        lamp.inputs.get(0).connectTo(and.outputs.get(0));
        and.inputs.get(0).connectTo(button.outputs.get(0));
        not.inputs.get(0).connectTo(and.outputs.get(0));
        lamp2.inputs.get(0).connectTo(not.outputs.get(0));
        display.inputs.get(0).connectTo(and.outputs.get(0));
        display.inputs.get(1).connectTo(high1.outputs.get(0));
        display.inputs.get(2).connectTo(high1.outputs.get(0));
        display.inputs.get(3).connectTo((low.outputs.get(0)));
    }

    public void test2() {
        Label cinLabel = (Label)addPlacable(new Label(100, 50, "Carry in"));
        Switch cin = (Switch)addLogical(new Switch(100, 100));
        Label iaLabel = (Label)addPlacable(new Label(100, 150, "Input A"));
        Switch ia = (Switch)addLogical(new Switch(100, 200));
        Label ibLabel = (Label)addPlacable(new Label(100, 250, "Input B"));
        Switch ib = (Switch)addLogical(new Switch(100, 300));
        XOrGate xor1 = (XOrGate)addLogical(new XOrGate(300, 200));
        xor1.inputs.get(0).connectTo(ia.outputs.get(0));
        xor1.inputs.get(1).connectTo(ib.outputs.get(0));
        AndGate and1 = (AndGate)addLogical(new AndGate(300, 300));
        and1.inputs.get(0).connectTo(ia.outputs.get(0));
        and1.inputs.get(1).connectTo(ib.outputs.get(0));
        XOrGate xor2 = (XOrGate)addLogical(new XOrGate(400, 200));
        xor2.inputs.get(0).connectTo(cin.outputs.get(0));
        xor2.inputs.get(1).connectTo(xor1.outputs.get(0));
        AndGate and2 = (AndGate)addLogical(new AndGate(400, 300));
        and2.inputs.get(0).connectTo(cin.outputs.get(0));
        and2.inputs.get(1).connectTo(xor1.outputs.get(0));
        OrGate or = (OrGate)addLogical(new OrGate(500, 300));
        or.inputs.get(0).connectTo(and2.outputs.get(0));
        or.inputs.get(1).connectTo(and1.outputs.get(0));
        Lamp sum = (Lamp)addLogical(new Lamp(600, 200));
        sum.inputs.get(0).connectTo(xor2.outputs.get(0));
        Lamp cout = (Lamp)addLogical(new Lamp(600, 300));
        cout.inputs.get(0).connectTo(or.outputs.get(0));
    }

    public void test3() {
        Button btn1 = (Button)addLogical(new Button(100, 100));
        Button btn2 = (Button)addLogical(new Button(100, 200));
        XOrGate xor = (XOrGate)addLogical(new XOrGate(300, 150));
        Lamp res = (Lamp)addLogical(new Lamp(500, 150));
        xor.inputs.get(0).connectTo(btn1.outputs.get(0));
        xor.inputs.get(1).connectTo(btn2.outputs.get(0));
        res.inputs.get(0).connectTo(xor.outputs.get(0));
    }

    public Logical addLogical(Logical logical) {
        logicals.add(logical);
        addPlacable(logical);
        return logical;
    }

    public Placeable addPlacable(Placeable placeable) {
        placeables.add(placeable);
        return placeable;
    }

}
