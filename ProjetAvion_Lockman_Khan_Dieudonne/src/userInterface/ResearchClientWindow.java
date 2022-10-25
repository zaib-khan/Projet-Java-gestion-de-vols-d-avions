package userInterface;

import controller.Control;
import exception.ConnexionException;
import exception.NameException;
import exception.OperationDataException;
import exception.PriceException;
import model.Meal;
import model.ResearchClients;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ResearchClientWindow extends JFrame {
    private JComboBox<String> meal_combobox;
    private ArrayList<Meal> meals;
    private ArrayList<ResearchClients> researchClients;
    private JSpinner start_date_spinner, end_date_spinner;
    private JLabel meal_label, start_date_label, end_date_label;
    private JPanel buttonPanel, infoPanel;
    private JButton confirm, cancel;
    private SpinnerDateModel spinnerStartDateModel, spinnerEndDateModel;
    private static final String dateFormat = "dd-MM-yyyy";

    public ResearchClientWindow() {
        super("Research which clients ate a specific meal");

        try {
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
            setBounds(250, 250, 420, 200);
            setLayout(new BorderLayout());
            setResizable(false);
            getAllMeals();

            setInfoPanel();
            setButtonPanel();

            setVisible(true);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private void setInfoPanel() {
        infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        //Meal
        meal_label = new JLabel("Meal : ");
        meal_label.setHorizontalAlignment(SwingConstants.CENTER);
        meal_combobox = new JComboBox<>();
        fillMealCombobox();
        meal_combobox.setSelectedIndex(0);
        infoPanel.add(meal_label);
        infoPanel.add(meal_combobox);

        //Start date
        setSpinnersModel();
        start_date_label = new JLabel("From : ");
        start_date_label.setHorizontalAlignment(SwingConstants.CENTER);
        start_date_spinner = new JSpinner(spinnerStartDateModel);
        start_date_spinner.setEditor(new JSpinner.DateEditor(start_date_spinner, dateFormat));
        start_date_spinner.addChangeListener(new SpinnerListener());
        infoPanel.add(start_date_label);
        infoPanel.add(start_date_spinner);

        //End date
        end_date_label = new JLabel("to : ");
        end_date_label.setHorizontalAlignment(SwingConstants.CENTER);
        end_date_spinner = new JSpinner(spinnerEndDateModel);
        end_date_spinner.setEditor(new JSpinner.DateEditor(end_date_spinner, dateFormat));
        end_date_spinner.addChangeListener(new SpinnerListener());
        infoPanel.add(end_date_label);
        infoPanel.add(end_date_spinner);

        add(infoPanel, BorderLayout.NORTH);
    }

    private void setButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        confirm = new JButton("Confirm");
        confirm.addActionListener(new ButtonListener());

        cancel = new JButton("Cancel");
        cancel.addActionListener(new ButtonListener());

        buttonPanel.add(confirm);
        buttonPanel.add(cancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void getAllMeals() throws ConnexionException, OperationDataException, NameException, PriceException {
        meals = new Control().getAllMeal();
    }

    private void fillMealCombobox() {
        for (Meal meal : meals) {
            meal_combobox.addItem(meal.getMeal_name());
        }
    }

    private void setSpinnersModel() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.YEAR, -10);
        Date date_min = calendar.getTime();
        calendar.add(Calendar.YEAR, 20);
        Date date_max = calendar.getTime();
        spinnerStartDateModel = new SpinnerDateModel(today, date_min, date_max, Calendar.YEAR);
        spinnerEndDateModel = new SpinnerDateModel(today, date_min, date_max, Calendar.YEAR);
    }

    private class SpinnerListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            Date start_date = (Date) start_date_spinner.getValue();
            Date end_date = (Date) end_date_spinner.getValue();

            if (start_date.after(end_date)) {
                start_date_spinner.setValue(end_date);
            }
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getSource() == confirm) {
                    researchClients = new Control().researchClients(meal_combobox.getSelectedIndex() + 1,
                            (Date) start_date_spinner.getValue(), (Date) end_date_spinner.getValue());
                    if (researchClients.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No results found.");
                    } else new ResearchClientsTableWindow(researchClients);
                }
                dispose();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }
}
