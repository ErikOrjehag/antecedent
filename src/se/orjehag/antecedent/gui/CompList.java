package se.orjehag.antecedent.gui;

import se.orjehag.antecedent.SimComponent;
import se.orjehag.antecedent.placeable.Text;
import se.orjehag.antecedent.placeable.logical.gate.AndGate;
import se.orjehag.antecedent.placeable.logical.gate.NandGate;
import se.orjehag.antecedent.placeable.logical.gate.NorGate;
import se.orjehag.antecedent.placeable.logical.gate.NotGate;
import se.orjehag.antecedent.placeable.logical.gate.OrGate;
import se.orjehag.antecedent.placeable.logical.gate.XOrGate;
import se.orjehag.antecedent.placeable.logical.gate.XnorGate;
import se.orjehag.antecedent.placeable.logical.input.Button;
import se.orjehag.antecedent.placeable.logical.input.High;
import se.orjehag.antecedent.placeable.logical.input.Low;
import se.orjehag.antecedent.placeable.logical.input.Switch;
import se.orjehag.antecedent.placeable.logical.output.FourBitDisplay;
import se.orjehag.antecedent.placeable.logical.output.Lamp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;

public class CompList extends JScrollPane {

    private JPanel panel;
    private static final Color TITLE_BACKGROUND_COLOR = new Color(200, 200, 200);

    // I got this warning because this methods lists all the classes of the placeable components.
    // It's meant to be a list so there is not much I can do about it.
    @SuppressWarnings("OverlyCoupledMethod")
    public CompList(JPanel dragPanel, SimComponent dropTarget) {
        super(new JPanel(), VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        panel = (JPanel) getViewport().getView();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addTitle("Gates");
        JPanel gates = new JPanel();
        gates.setLayout(new GridLayout(4, 2));
        gates.add(new CompListItem(dragPanel, dropTarget, AndGate.class));
        gates.add(new CompListItem(dragPanel, dropTarget, NandGate.class));
        gates.add(new CompListItem(dragPanel, dropTarget, OrGate.class));
        gates.add(new CompListItem(dragPanel, dropTarget, NorGate.class));
        gates.add(new CompListItem(dragPanel, dropTarget, XOrGate.class));
        gates.add(new CompListItem(dragPanel, dropTarget, XnorGate.class));
        gates.add(new CompListItem(dragPanel, dropTarget, NotGate.class));
        panel.add(gates);

        addTitle("Inputs");
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(2, 2));
        inputs.add(new CompListItem(dragPanel, dropTarget, High.class));
        inputs.add(new CompListItem(dragPanel, dropTarget, Low.class));
        inputs.add(new CompListItem(dragPanel, dropTarget, Button.class));
        inputs.add(new CompListItem(dragPanel, dropTarget, Switch.class));
        panel.add(inputs);

        addTitle("Outputs");
        JPanel outputs = new JPanel();
        outputs.setLayout(new GridLayout(1, 2));
        outputs.add(new CompListItem(dragPanel, dropTarget, FourBitDisplay.class));
        outputs.add(new CompListItem(dragPanel, dropTarget, Lamp.class));
        panel.add(outputs);

        addTitle("Others");
        JPanel others = new JPanel();
        others.setLayout(new GridLayout(1, 2));
        others.add(new CompListItem(dragPanel, dropTarget, Text.class));
        panel.add(others);
    }

    private void addTitle(String title) {
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(true);
        labelPanel.setBackground(TITLE_BACKGROUND_COLOR);
        labelPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        JLabel label = new JLabel(title);
        labelPanel.add(label);
        panel.add(labelPanel);
    }
}
