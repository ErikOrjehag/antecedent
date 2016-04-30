package se.orjehag.antecedent.placeable.logical.input;

import se.orjehag.antecedent.Vec2;
import se.orjehag.antecedent.placeable.logical.Logical;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Input toggle switch.
 */
public class Clock extends Logical {

    private boolean isHigh = false;
    private Timer timer;

    public Clock(int x, int y) {
        super(x, y);
        addOutputs(1);

        timer = new Timer(500, new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                isHigh = !isHigh;
                notifyInteractionListeners();
            }
        });
        timer.setCoalesce(true);
    }

    @Override
    public void init() {
        // Start the clock. We don't want to do this in the constructor because
        // then the clock will switch on and of in the list to the left.
        timer.start();
    }

    @Override
    public boolean[] func(boolean[] in) {
        return new boolean[]{ isHigh };
    }

    @Override protected String getLabel() {
        return "CLK: " + (isHigh ? "1" : "0");
    }

    @Override
    public void showPropertiesDialog() {
        final int minimum = 100;
        int timerMillis = minimum - 1;

        String input = JOptionPane.showInputDialog("Enter milliseconds (>= " + minimum + "):");

        if (input != null) {
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
