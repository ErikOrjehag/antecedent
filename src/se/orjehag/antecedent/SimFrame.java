package se.orjehag.antecedent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by erik on 19/03/16.
 */
public class SimFrame extends JFrame {

    final static int WIDTH = 720, HEIGHT = 420;
    private SimComponent simComponent;

    public SimFrame() {
        super("Antecedent");

        setLayout(new BorderLayout());

        simComponent = new SimComponent();
        add(simComponent, BorderLayout.CENTER);

        /*JToolBar toolBar = new JToolBar("Still draggable");
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.PAGE_START);
        JButton button = new JButton();
        button.setText("Save");
        toolBar.add(button);*/

        DraggableComponent drag = new DraggableComponent();

        GridLayout grid = new GridLayout(5, 2);
        JPanel panel = new JPanel();
        panel.setLayout(grid);
        panel.add(drag);
        //panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));
        panel.add(new JButton("test"));

        JScrollPane scrollPane = new JScrollPane(
                panel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        add(scrollPane, BorderLayout.WEST);

        createMenu();

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

    private void quitCallback(final ActionEvent actionEvent) {
        System.exit(0);
    }

}
