package se.orjehag.antecedent;

import se.orjehag.antecedent.logical.gate.AndGate;
import se.orjehag.antecedent.logical.gate.NandGate;
import se.orjehag.antecedent.logical.gate.NorGate;
import se.orjehag.antecedent.logical.gate.NotGate;
import se.orjehag.antecedent.logical.gate.OrGate;
import se.orjehag.antecedent.logical.gate.XOrGate;
import se.orjehag.antecedent.logical.gate.XnorGate;
import se.orjehag.antecedent.logical.input.Button;
import se.orjehag.antecedent.logical.input.High;
import se.orjehag.antecedent.logical.input.Low;
import se.orjehag.antecedent.logical.input.Switch;
import se.orjehag.antecedent.logical.output.FourBitDisplay;
import se.orjehag.antecedent.logical.output.Lamp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;

/**
 * Created by erik on 12/04/16.
 */
public class ComponentDrawer extends JScrollPane {

    JPanel panel;

    public ComponentDrawer() {
        super(new JPanel(), VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        panel = (JPanel) getViewport().getView();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addTitle("Gates");
        JPanel gates = new JPanel();
        gates.setLayout(new GridLayout(4, 2));
        //gates.setPreferredSize(new Dimension(200, 300));
        gates.add(new ComponentIcon(new AndGate(0, 0)));
        gates.add(new ComponentIcon(new NandGate(0, 0)));
        gates.add(new ComponentIcon(new OrGate(0, 0)));
        gates.add(new ComponentIcon(new NorGate(0, 0)));
        gates.add(new ComponentIcon(new XOrGate(0, 0)));
        gates.add(new ComponentIcon(new XnorGate(0, 0)));
        gates.add(new ComponentIcon(new NotGate(0, 0)));
        panel.add(gates);

        addTitle("Inputs");
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(2, 2));
        //inputs.setPreferredSize(new Dimension(200, 300));
        inputs.add(new ComponentIcon(new High(0, 0)));
        inputs.add(new ComponentIcon(new Low(0, 0)));
        inputs.add(new ComponentIcon(new Button(0, 0)));
        inputs.add(new ComponentIcon(new Switch(0, 0)));
        panel.add(inputs);

        addTitle("Outputs");
        JPanel outputs = new JPanel();
        outputs.setLayout(new GridLayout(1, 2));
        outputs.add(new ComponentIcon(new FourBitDisplay(0, 0)));
        outputs.add(new ComponentIcon(new Lamp(0, 0)));
        panel.add(outputs);


    }


    private void addTitle(String title) {
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(true);
        labelPanel.setBackground(new Color(200, 200, 200));
        labelPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        JLabel label = new JLabel(title);
        labelPanel.add(label);
        panel.add(labelPanel);
    }
}
