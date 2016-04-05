package se.orjehag.antecedent;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by erik on 31/03/16.
 */
public class Simulation {

    public ArrayList<Placeable> placeables = new ArrayList<>();
    public ArrayList<Logical> logicals = new ArrayList<>();

    public Simulation() {
        Label label = new Label(100, 100, "Test");
        placeables.add(label);

        High high = new High(200, 200);
        placeables.add(high);
        logicals.add(high);

        Lamp lamp = new Lamp(300, 200);
        placeables.add(lamp);
        logicals.add(lamp);

        high.outputs.get(0).connectTo(lamp.inputs.get(0));

        step();
    }

    public void step() {
        for (int n = 0; n < 4; n++) {
            for (int i = 0; i < logicals.size(); i++) {
                logicals.get(i).step();
            }
            for (int i = 0; i < logicals.size(); i++) {
                logicals.get(i).commit();
            }
        }
    }

    public void draw(Graphics2D g2d) {
        for (Placeable placeable : placeables) {
            placeable.draw(g2d);
        }
    }
}
