package se.orjehag.antecedent.placeable.logical.input;

import se.orjehag.antecedent.placeable.logical.Logical;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Input toggle switch.
 */
public class Clock extends Logical {

    private boolean isHigh = false;
    private Timer timer;
    private final static int DEFAULT_TIMER_MS = 500;

    public Clock(int x, int y) {
        super(x, y);
        addOutputs(1);

        timer = new Timer(DEFAULT_TIMER_MS, new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                isHigh = !isHigh;
                notifyInteractionListeners();
            }
        });
        timer.setCoalesce(true);
    }

    @Override
    public void onAdd() {
        super.onAdd();
        timer.start();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        timer.stop();
    }

    @Override
    public boolean[] func(boolean[] in) {
        assert in.length == 0;
        return new boolean[]{ isHigh };
    }

    @Override protected String getLabel() {
        return "CLK: " + (isHigh ? "1" : "0");
    }

    @Override
    public void showPropertiesDialog() {
        final int minimum = 100;

        String input = JOptionPane.showInputDialog("Enter milliseconds (>= " + minimum + "):");

        if (input != null) {

            int timerMillis = minimum - 1;

            try {
                timerMillis = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Handled by if statement below.
            }

            if (timerMillis < minimum) {
                JOptionPane.showMessageDialog(null, "Not valid input", "Warning", JOptionPane.WARNING_MESSAGE);
                showPropertiesDialog();
            } else {
                timer.setDelay(timerMillis);
            }
        }
    }
}
