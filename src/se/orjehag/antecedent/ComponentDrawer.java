package se.orjehag.antecedent;

import se.orjehag.antecedent.logical.gate.AndGate;
import se.orjehag.antecedent.logical.gate.NotGate;
import se.orjehag.antecedent.logical.gate.OrGate;
import se.orjehag.antecedent.logical.gate.XOrGate;
import se.orjehag.antecedent.logical.input.Button;
import se.orjehag.antecedent.logical.input.High;
import se.orjehag.antecedent.logical.input.Low;
import se.orjehag.antecedent.logical.input.Switch;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

/**
 * Created by erik on 12/04/16.
 */
public class ComponentDrawer extends JScrollPane {

    JPanel panel;

    public ComponentDrawer() {
        super(new JPanel(), VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        panel = (JPanel) getViewport().getView();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addTitle("Gates are fun stuff");
        JPanel gates = new JPanel();
        gates.setLayout(new GridLayout(2, 2, 1, 1));
        gates.setPreferredSize(new Dimension(200, 300));
        gates.add(new ComponentIcon(new AndGate(0, 0)));
        gates.add(new ComponentIcon(new OrGate(0, 0)));
        gates.add(new ComponentIcon(new XOrGate(0, 0)));
        gates.add(new ComponentIcon(new NotGate(0, 0)));
        panel.add(gates);

        addTitle("Inputs are also cool");
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(2, 2, 1, 1));
        inputs.setPreferredSize(new Dimension(200, 300));
        inputs.add(new ComponentIcon(new High(0, 0)));
        inputs.add(new ComponentIcon(new Low(0, 0)));
        inputs.add(new ComponentIcon(new Button(0, 0)));
        inputs.add(new ComponentIcon(new Switch(0, 0)));
        panel.add(inputs);


    }


    private void addTitle(String title) {
        JPanel gatesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel gatesLabel = new JLabel(title);
        gatesPanel.add(gatesLabel);
        panel.add(gatesPanel);
    }
}
