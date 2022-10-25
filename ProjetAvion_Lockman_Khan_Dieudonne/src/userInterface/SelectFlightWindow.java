package userInterface;

import controller.Control;
import exception.ConnexionException;
import exception.NameException;
import exception.OperationDataException;
import exception.PriceException;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SelectFlightWindow extends JFrame {

    private JButton confirm, cancel;
    private JLabel select_flight;
    private JComboBox<String> flight_combo_box;
    private ArrayList<Flight> flight_list;
    private int action;

    public SelectFlightWindow(int action) {
        //action == 0 -> Delete
        //action == 1 -> Modify

        super("Select a flight");
        try {
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
            setBounds(150, 150, 800, 130);
            setLayout(new GridLayout(2, 2));
            setResizable(false);
            this.action = action;

            //SelectionPanel

            select_flight = new JLabel("Select a flight :");
            add(select_flight);

            getAllFlight();
            flight_combo_box = new JComboBox<>();
            fill_flight_combobox();
            add(flight_combo_box);


            //ButtonPanel
            confirm = new JButton("Select");
            confirm.addActionListener(new ButtonListener());
            add(confirm);

            cancel = new JButton("Cancel");
            cancel.addActionListener(new ButtonListener());
            add(cancel);

            setVisible(true);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void getAllFlight() throws ConnexionException, OperationDataException, NameException, PriceException {
        flight_list = new Control().getAll_flight();
    }

    public void fill_flight_combobox() {
        String flight_description;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (Flight flight : flight_list) {
            flight_description = flight.getDeparture_airport().getCity_name() + " to ";
            flight_description += flight.getArrival_airport().getCity_name() + " (";
            flight_description += sdf.format(flight.getDeparture_time()) + ")";
            flight_combo_box.addItem(flight_description);
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                Flight flight = flight_list.get(flight_combo_box.getSelectedIndex());

                //dispose();
                if (event.getSource() != cancel) {
                    if (action == 0) {

                        //Delete
                        new Control().deleteFlight(flight.getFlight_id());
                        JOptionPane.showMessageDialog(null, "Task succeeded !");

                    } else {
                        //Modify
                        new AddFlightWindow(flight);
                    }
                }
                dispose();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }

    }
}
