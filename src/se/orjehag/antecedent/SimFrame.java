package se.orjehag.antecedent;

import se.orjehag.antecedent.gui.CompList;
import se.orjehag.antecedent.gui.CompListDraggable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by erik on 19/03/16.
 */
public class SimFrame extends JFrame {

    final static int WIDTH = 1400, HEIGHT = 700;
    private SimComponent simComponent;
    private JPanel front;
    private JPanel back;

    public SimFrame() {
        super("Antecedent");

        setLayout(new BorderLayout());

        JLayeredPane layers = new JLayeredPane();
        add(layers, BorderLayout.CENTER);
        layers.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        back = new JPanel();
        back.setBounds(0, 0, WIDTH, HEIGHT);
        layers.add(back, new Integer(0));

        front = new JPanel();
        front.setLayout(null);
        front.setOpaque(false);
        front.setBounds(0, 0, WIDTH, HEIGHT);
        layers.add(front, new Integer(1));

        back.setLayout(new BorderLayout());

        simComponent = new SimComponent();
        back.add(simComponent, BorderLayout.CENTER);

        back.add(new CompList(front, simComponent), BorderLayout.WEST);

        createMenu();
        //createToolbar();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenu() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu file = new JMenu("File");

        final JMenuItem save = new JMenuItem("Save", 'S');
        file.add(save);
        final JMenuItem open = new JMenuItem("Open", 'O');
        file.add(open);
        final JMenuItem quit = new JMenuItem("Quit", 'Q');
        quit.addActionListener(this::quitCallback);
        file.add(quit);

        menuBar.add(file);

        final JMenu examples = new JMenu("Examples");
        menuBar.add(examples);

        setJMenuBar(menuBar);
    }

    private void createToolbar() {
        JToolBar toolBar = new JToolBar("Still draggable");
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.PAGE_START);
        JButton button = new JButton();
        button.setText("Save");
        toolBar.add(button);
    }

    private void quitCallback(final ActionEvent actionEvent) {
        System.exit(0);
    }
}
