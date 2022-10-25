package userInterface;

import controller.Control;
import exception.ConnexionException;
import exception.OperationDataException;
import model.Country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class TaskAverageFlighCount extends JFrame {
    private JLabel countryLabel, averageLabel, resultLabel;
    private JPanel countryPanel, resultPanel, buttonPanel;
    private JComboBox<String> country_combobox;
    private JButton quit, select;

    public TaskAverageFlighCount() {
        super("Average flight per month that left a specified country the past year");
        try {
            setLayout(new BorderLayout());
            setSize(350, 200);
            setResizable(false);
            setLocationRelativeTo(null);

            setCountryPanel();
            setResultPanel();
            setButtonPanel();

            setVisible(true);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void setCountryPanel() throws ConnexionException, OperationDataException {
        countryPanel = new JPanel(new GridLayout(1, 2));

        countryLabel = new JLabel("Country :  ");
        countryLabel.setHorizontalAlignment(JLabel.RIGHT);
        countryPanel.add(countryLabel);

        country_combobox = new JComboBox<>();
        fillCountryCombobox(new Control().getAll_country());
        country_combobox.addItemListener(new ComboBoxListener());
        countryPanel.add(country_combobox);

        add(countryPanel, BorderLayout.NORTH);
    }

    public void setResultPanel() {
        resultPanel = new JPanel(new GridLayout(1, 2));

        averageLabel = new JLabel("Result :  ");
        resultPanel.add(averageLabel);

        add(resultPanel, BorderLayout.CENTER);
    }

    public void setButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(1, 2));

        select = new JButton("Select");
        select.addActionListener(new ButtonListener());
        buttonPanel.add(select);

        quit = new JButton("Quit");
        quit.addActionListener(new ButtonListener());
        buttonPanel.add(quit);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void fillCountryCombobox(ArrayList<Country> countries) {
        for (Country country : countries) {
            country_combobox.addItem(country.getCountry_name());
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                double average;
                int countryId = country_combobox.getSelectedIndex() + 1;

                if (event.getSource() == select) {
                    average = new Control().getAverageFlightPerMonthForACountry(countryId);
                    resultLabel = new JLabel(String.format("%.01f" + " flights/month the last year.", average));
                    resultPanel.add(resultLabel);
                    select.setEnabled(false);
                    revalidate();
                } else dispose();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    private class ComboBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            if (resultLabel != null) resultPanel.remove(resultLabel);
            select.setEnabled(true);
        }
    }
}
