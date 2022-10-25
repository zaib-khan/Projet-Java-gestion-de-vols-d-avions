package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class FlightLeavingSoonWindow extends JFrame {
    private ArrayList<String> flights;
    private JButton quit;
    private JPanel labelPanel;

    public FlightLeavingSoonWindow(ArrayList<String> flights) {
        super("Flight leaving in less than 10 minutes");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        this.flights = flights;
        setSize(400, 100 + 20 * flights.size());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        setLabelPanel();

        quit = new JButton("Quit");
        quit.addActionListener(new ButtonListener());
        add(quit, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setLabelPanel() {
        JLabel label;

        labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(flights.size(), 1));

        for (String flight : flights) {
            label = new JLabel(flight);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelPanel.add(label);
        }
        add(labelPanel, BorderLayout.CENTER);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            dispose();
        }
    }
}
