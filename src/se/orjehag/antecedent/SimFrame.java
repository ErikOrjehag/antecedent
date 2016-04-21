package se.orjehag.antecedent;

import se.orjehag.antecedent.gui.CompList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Created by erik on 19/03/16.
 */
public class SimFrame extends JFrame {

    final static int WIDTH = 1400, HEIGHT = 700;
    private SimComponent simComponent;
    private JPanel front = new JPanel();
    private JPanel back = new JPanel();

    private String fileExtension = "sim";
    private JFileChooser fileChooser = new JFileChooser();

    public SimFrame() {
        super("Logic Simulator");

        fileChooser.setFileFilter(new FileNameExtensionFilter("Simulation files", fileExtension));

        setLayout(new BorderLayout());

        JLayeredPane layers = new JLayeredPane();
        add(layers, BorderLayout.CENTER);
        layers.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        back.setBounds(0, 0, WIDTH, HEIGHT);
        layers.add(back, new Integer(0));

        front.setLayout(null);
        front.setOpaque(false);
        front.setBounds(0, 0, WIDTH, HEIGHT);
        layers.add(front, new Integer(1));

        back.setLayout(new BorderLayout());

        simComponent = new SimComponent();
        back.add(simComponent, BorderLayout.CENTER);

        back.add(new CompList(front, simComponent), BorderLayout.WEST);

        createMenu();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenu() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu file = new JMenu("File");

        final JMenuItem save = new JMenuItem("Save", 'S');
        save.addActionListener(this::saveCallback);
        file.add(save);
        final JMenuItem open = new JMenuItem("Open", 'O');
        open.addActionListener(this::openCallback);
        file.add(open);
        final JMenuItem quit = new JMenuItem("Quit", 'Q');
        quit.addActionListener(this::quitCallback);
        file.add(quit);

        menuBar.add(file);

        final JMenu examples = new JMenu("Examples");
        menuBar.add(examples);

        setJMenuBar(menuBar);
    }

    private void quitCallback(final ActionEvent actionEvent) {
        System.exit(0);
    }

    private void saveCallback(final ActionEvent actionEvent) {

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            String fileName = file.toString();
            if (!fileName.endsWith("." + fileExtension)) {
                fileName += "." + fileExtension;
            }

            try {
                FileOutputStream fileOut = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(simComponent.getSimulation());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openCallback(final ActionEvent actionEvent) {
        Simulation simulation = null;

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
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
}
