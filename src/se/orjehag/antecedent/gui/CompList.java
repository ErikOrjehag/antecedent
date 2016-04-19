package se.orjehag.antecedent.gui;

import se.orjehag.antecedent.SimComponent;
import se.orjehag.antecedent.placable.Text;
import se.orjehag.antecedent.placable.logical.gate.AndGate;
import se.orjehag.antecedent.placable.logical.gate.NandGate;
import se.orjehag.antecedent.placable.logical.gate.NorGate;
import se.orjehag.antecedent.placable.logical.gate.NotGate;
import se.orjehag.antecedent.placable.logical.gate.OrGate;
import se.orjehag.antecedent.placable.logical.gate.XOrGate;
import se.orjehag.antecedent.placable.logical.gate.XnorGate;
import se.orjehag.antecedent.placable.logical.input.Button;
import se.orjehag.antecedent.placable.logical.input.High;
import se.orjehag.antecedent.placable.logical.input.Low;
import se.orjehag.antecedent.placable.logical.input.Switch;
import se.orjehag.antecedent.placable.logical.output.FourBitDisplay;
import se.orjehag.antecedent.placable.logical.output.Lamp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;

/**
 * Created by erik on 12/04/16.
 */
public class CompList extends JScrollPane {

    JPanel panel;

    public CompList(JPanel dragPanel, SimComponent dropTarget) {
        super(new JPanel(), VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        panel = (JPanel) getViewport().getView();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addTitle("Gates");
        JPanel gates = new JPanel();
        gates.setLayout(new GridLayout(4, 2));
        gates.add(new CompListItem(this, gates, dragPanel, dropTarget, new AndGate(0, 0)));
        gates.add(new CompListItem(this, gates, dragPanel, dropTarget, new NandGate(0, 0)));
        gates.add(new CompListItem(this, gates, dragPanel, dropTarget, new OrGate(0, 0)));
        gates.add(new CompListItem(this, gates, dragPanel, dropTarget, new NorGate(0, 0)));
        gates.add(new CompListItem(this, gates, dragPanel, dropTarget, new XOrGate(0, 0)));
        gates.add(new CompListItem(this, gates, dragPanel, dropTarget, new XnorGate(0, 0)));
        gates.add(new CompListItem(this, gates, dragPanel, dropTarget, new NotGate(0, 0)));
        panel.add(gates);

        addTitle("Inputs");
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(2, 2));
        inputs.add(new CompListItem(this, inputs, dragPanel, dropTarget, new High(0, 0)));
        inputs.add(new CompListItem(this, inputs, dragPanel, dropTarget, new Low(0, 0)));
        inputs.add(new CompListItem(this, inputs, dragPanel, dropTarget, new Button(0, 0)));
        inputs.add(new CompListItem(this, inputs, dragPanel, dropTarget, new Switch(0, 0)));
        panel.add(inputs);

        addTitle("Outputs");
        JPanel outputs = new JPanel();
        outputs.setLayout(new GridLayout(1, 2));
        outputs.add(new CompListItem(this, outputs, dragPanel, dropTarget, new FourBitDisplay(0, 0)));
        outputs.add(new CompListItem(this, outputs, dragPanel, dropTarget, new Lamp(0, 0)));
        panel.add(outputs);

        addTitle("Others");
        JPanel others = new JPanel();
        others.setLayout(new GridLayout(1, 2));
        others.add(new CompListItem(this, outputs, dragPanel, dropTarget, new Text(0, 0, "Text")));
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