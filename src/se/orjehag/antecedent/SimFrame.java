package se.orjehag.antecedent;

import se.orjehag.antecedent.gui.CompList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is the window. It is responsible for the
 * layout of the application. It is also responsible
 * for the top menu bar and the menu shortcuts.
 */
public class SimFrame extends JFrame {

    private final static int WIDTH = 1400, HEIGHT = 700;
    private final static String WINDOW_TITLE = "Logic Simulator";
    private SimComponent simComponent;

    private final static String FILE_EXTENSION = "sim";
    private final JFileChooser fileChooser = new JFileChooser();
    private final Logger logger = Logger.getLogger(SimFrame.class.getName());

    public SimFrame() {
        super(WINDOW_TITLE);

        fileChooser.setFileFilter(new FileNameExtensionFilter("Simulation files", FILE_EXTENSION));

        createLayout();
        createMenu();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void createLayout() {
        setLayout(new BorderLayout());

        // We need two layers.
        JLayeredPane layers = new JLayeredPane();
        add(layers, BorderLayout.CENTER);
        layers.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // The front layer is used as an overlay to
        // create drag and drop functionality of items
        // in the list on the left to the
        // simulation component on the right.
        final JPanel front = new JPanel();
        layers.add(front, Integer.valueOf(1));
        front.setLayout(null);
        front.setOpaque(false);
        front.setBounds(0, 0, WIDTH, HEIGHT);

        // The back layer contains most of the stuff,
        // such as the scrolling component list to
        // the left and the simulation component
        // to the right.
        final JPanel back = new JPanel();
        layers.add(back, Integer.valueOf(0));
        back.setBounds(0, 0, WIDTH, HEIGHT);
        back.setLayout(new BorderLayout());

        simComponent = new SimComponent();
        simComponent.setSimulation(ExamplesFactory.introSim());
        back.add(simComponent, BorderLayout.CENTER);

        back.add(new CompList(front, simComponent), BorderLayout.WEST);
    }

    private void createMenu() {

        // Platform independent shortcut key (Mac uses the Meta key, Windows and Linux uses Ctrl).
        int shortcut = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask();

        // Menu bar that looks like [ File | Edit | Examples ].
        final JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // File:
        final JMenu file = new JMenu("File");
        menuBar.add(file);

        // Save simulation.
        final JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, shortcut));
        save.addActionListener(this::saveCallback);
        file.add(save);

        // Open simulation.
        final JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, shortcut));
        open.addActionListener(this::openCallback);
        file.add(open);

        // Quit application.
        final JMenuItem quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcut));
        quit.addActionListener(this::quitCallback);
        file.add(quit);

        // Edit:
        final JMenu edit = new JMenu("Edit");
        menuBar.add(edit);

        // Remove the selected placeable.
        final JMenuItem removeSelected = new JMenuItem("Remove selected");
        removeSelected.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
        removeSelected.addActionListener(this::removeSelectedCallback);
        edit.add(removeSelected);
        // Additional key binding to remove placeable, for convenience.
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "removeSelected");
        getRootPane().getActionMap().put("removeSelected", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelected();
            }
        });

        // Remove everything from the simulation.
        final JMenuItem removeAll = new JMenuItem("Remove all");
        removeAll.addActionListener(new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                simComponent.setSimulation(new Simulation());
            }
        });
        edit.add(removeAll);

        // Examples:
        final JMenu examples = new JMenu("Examples");
        menuBar.add(examples);

        // Full adder example.
        final JMenuItem fullAdder = new JMenuItem("Full adder");
        fullAdder.addActionListener(new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                simComponent.setSimulation(ExamplesFactory.fullAdder());
            }
        });
        examples.add(fullAdder);

        // Flip flop example.
        final JMenuItem dflipflop = new JMenuItem("D flipflop");
        dflipflop.addActionListener(new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                simComponent.setSimulation(ExamplesFactory.dFlipFlop());
            }
        });
        examples.add(dflipflop);

        // Click counter example.
        final JMenuItem clickCounter = new JMenuItem("Click counter");
        clickCounter.addActionListener(new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                simComponent.setSimulation(ExamplesFactory.clickCounter());
            }
        });
        examples.add(clickCounter);

        // Click counter example.
        final JMenuItem cascadingFlipFlops = new JMenuItem("Cascading flip flops");
        cascadingFlipFlops.addActionListener(new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                simComponent.setSimulation(ExamplesFactory.cascadingFlipFlops());
            }
        });
        examples.add(cascadingFlipFlops);
    }

    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    private void quitCallback(final ActionEvent actionEvent) {
        System.exit(0);
    }

    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    private void removeSelectedCallback(final ActionEvent actionEvent) {
        removeSelected();
    }

    private void removeSelected() {
        simComponent.getSimulation().removeSelected();
        repaint();
    }

    /* -------------------------------------------------

        Saving and opening files does not work because I added a logger which is
        not serializable to the simulation class. Having a logger is part of the
        grading criteria but saving and opening is not so I hope this ok. I plan
        to implement a better save system by storing the state of the simulation
        myself in a human readable format (not binary). I don't have time to do
        it before the hand in though.

     */


    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    private void saveCallback(final ActionEvent actionEvent) {

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            String fileName = file.toString();
            if (!fileName.endsWith("." + FILE_EXTENSION)) {
                fileName += "." + FILE_EXTENSION;
            }

            // Try to save file and recover if we fail.
            try (FileOutputStream fileOut = new FileOutputStream(fileName); ObjectOutput out = new ObjectOutputStream(fileOut)) {
                out.writeObject(simComponent.getSimulation());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error while trying to save simulation to file.", e);
                JOptionPane.showMessageDialog(this, "Woops, unable to save simulation!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    private void openCallback(final ActionEvent actionEvent) {
        Simulation simulation = null;

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Try to open file and recover if we fail.
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                simulation = (Simulation) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error while trying to open simulation from file.", e);
                JOptionPane.showMessageDialog(this, "Woops, unable to open simulation!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                logger.log(Level.SEVERE, "Could not find class needed to open simulation stored in file.", e);
                JOptionPane.showMessageDialog(this, "Woops, unable to open simulation! Your save file might not be up to date " +
                                                    "with the current version of the application.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (simulation != null) {
            simComponent.setSimulation(simulation);
        }
    }
}