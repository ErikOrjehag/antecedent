package se.orjehag.antecedent;

import se.orjehag.antecedent.gui.CompList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;


/**
 * Created by erik on 19/03/16.
 */
public class SimFrame extends JFrame {

    final static int WIDTH = 1400, HEIGHT = 700;
    private SimComponent simComponent;

    private String fileExtension = "sim";
    private JFileChooser fileChooser = new JFileChooser();

    public SimFrame() {
        super("Logic Simulator");

        fileChooser.setFileFilter(new FileNameExtensionFilter("Simulation files", fileExtension));

        setLayout(new BorderLayout());

        JLayeredPane layers = new JLayeredPane();
        add(layers, BorderLayout.CENTER);
        layers.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        final JPanel back = new JPanel();
        back.setBounds(0, 0, WIDTH, HEIGHT);
        layers.add(back, Integer.valueOf(0));

        final JPanel front = new JPanel();
        front.setLayout(null);
        front.setOpaque(false);
        front.setBounds(0, 0, WIDTH, HEIGHT);
        layers.add(front, Integer.valueOf(1));

        back.setLayout(new BorderLayout());

        simComponent = new SimComponent();
        simComponent.setSimulation(ExamplesFactory.fullAdder());
        back.add(simComponent, BorderLayout.CENTER);

        back.add(new CompList(front, simComponent), BorderLayout.WEST);

        createMenu();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void createMenu() {

        // Platform independent shortcut key (Mac uses the Meta key, Windows and Linux uses Ctrl).
        int shortcut = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask();

        final JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        final JMenu file = new JMenu("File");
        menuBar.add(file);
        final JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, shortcut));
        save.addActionListener(this::saveCallback);
        file.add(save);
        final JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, shortcut));
        open.addActionListener(this::openCallback);
        file.add(open);
        final JMenuItem quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcut));
        quit.addActionListener(this::quitCallback);
        file.add(quit);

        final JMenu edit = new JMenu("Edit");
        menuBar.add(edit);
        final JMenuItem remove = new JMenuItem("Remove selected");
        remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
        remove.addActionListener(this::removeCallback);
        edit.add(remove);

        // Additional key binding to remove placable, for convenience.
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "removeSelected");
        getRootPane().getActionMap().put("removeSelected", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelected();
            }
        });

        final JMenu examples = new JMenu("Examples");
        menuBar.add(examples);
        final JMenuItem fullAdder = new JMenuItem("Full adder");
        fullAdder.addActionListener(new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                simComponent.setSimulation(ExamplesFactory.fullAdder());
            }
        });
        examples.add(fullAdder);
    }

    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    @SuppressWarnings("UnusedParameters")
    private void quitCallback(final ActionEvent actionEvent) {
        System.exit(0);
    }

    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    @SuppressWarnings("UnusedParameters")
    private void saveCallback(final ActionEvent actionEvent) {

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            String fileName = file.toString();
            if (!fileName.endsWith("." + fileExtension)) {
                fileName += "." + fileExtension;
            }

            try (FileOutputStream fileOut = new FileOutputStream(fileName); ObjectOutput out = new ObjectOutputStream(fileOut)) {
                out.writeObject(simComponent.getSimulation());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    @SuppressWarnings("UnusedParameters")
    private void openCallback(final ActionEvent actionEvent) {
        Simulation simulation = null;

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                simulation = (Simulation) in.readObject();
                in.close();
                fileIn.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        if (simulation != null) {
            simComponent.setSimulation(simulation);
        }
    }

    // Still needs the parameter even though it's never used
    // in order to match the correct method signature.
    @SuppressWarnings("UnusedParameters")
    private void removeCallback(final ActionEvent actionEvent) {
        removeSelected();
    }

    private void removeSelected() {
        simComponent.getSimulation().removeSelected();
        repaint();
    }
}