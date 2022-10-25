package userInterface;

import controller.Control;
import exception.ConnexionException;
import exception.OperationDataException;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddFlightWindow extends JFrame {

    private Container container;
    private JPanel infoPanel, employeePanel, airportPanel, mealPanel, buttonPanel;
    private JTextArea comment_area;
    private JComboBox<String> countryDep, airportDep, countryArr, airportArr, pilots, copilots, steward1, steward2, steward3, planes, meals;
    private JCheckBox check_wifi, check_meal;
    private JSpinner price_eco_field, price_business_field, price_first_class_field, price_child_field,
            price_insurance_field, est_duration_field, departure_date, departure_time, real_duration_field;
    private JButton add, erase, quit, modify;
    private ArrayList<Country> country_list;
    private ArrayList<Airport> list_airports;
    private ArrayList<Employee> pilots_list, copilot_list, steward_list;
    private ArrayList<Meal> meals_list;
    private ArrayList<Plane> plane_list;
    private String[] array_countries;
    Flight flight;

    private JLabel label_date, label_time, label_comment, label_country_dep, label_airport_dep, label_country_arr,
            label_airport_arr, label_pilot, label_copilot, label_steward1, label_steward2, label_steward3, label_plane,
            label_check_meal, label_meal, label_check_wifi, label_price_eco, label_price_business, label_first_class,
            label_price_child, label_price_insurance, label_est_duration, label_real_duration;

    public AddFlightWindow() {
        super("Add a flight");
        try {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            container = this.getContentPane();
            container.setLayout(new GridLayout(5, 1, 0, 10));
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });

            setInfoPanel();
            setAirportPanel();
            setEmployeePanel();
            setMealPanel();
            setButtonPanel();

            setVisible(true);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public AddFlightWindow(Flight flight) throws ConnexionException, OperationDataException {
        this();
        this.flight = flight;

        //Get indexes
        int planeIndex, countryDepIndex, countryArrIndex, airportDepIndex, airportArrIndex, mealIndex, pilotIndex,
                copilotIndex, steward1Index, steward2Index, steward3Index;

        //Plane
        planeIndex = 0;
        while (!plane_list.get(planeIndex).getPlane_id().equals(flight.getPlane().getPlane_id())) {
            planeIndex++;
        }
        planes.setSelectedIndex(planeIndex);

        //Departure country
        countryDepIndex = 0;
        while (!country_list.get(countryDepIndex).getCountry_id().equals(flight.getDeparture_airport().getCountry().getCountry_id())) {
            countryDepIndex++;
        }
        countryDep.setSelectedIndex(countryDepIndex);

        //Departure airport
        airportDepIndex = 0;
        while (!list_airports.get(airportDepIndex).getAirport_id().equals(flight.getDeparture_airport().getAirport_id())) {
            airportDepIndex++;
        }
        airportDep.setSelectedIndex(airportDepIndex);

        //Arrival country
        countryArrIndex = 0;
        while (!country_list.get(countryArrIndex).getCountry_id().equals(flight.getArrival_airport().getCountry().getCountry_id())) {
            countryArrIndex++;
        }
        countryArr.setSelectedIndex(countryArrIndex);

        //Arrival airport
        airportArrIndex = 0;
        while (!list_airports.get(airportArrIndex).getAirport_id().equals(flight.getArrival_airport().getAirport_id())) {
            airportArrIndex++;
        }
        airportArr.setSelectedIndex(airportArrIndex);

        //Meal
        if (flight.getMeal() != null) {
            check_meal.setSelected(true);
            mealIndex = 0;
            while (!meals_list.get(mealIndex).getMeal_id().equals(flight.getMeal().getMeal_id())) {
                mealIndex++;
            }
            meals.setSelectedIndex(mealIndex);
        }

        //Employees
        Employee pilot, copilot, stewardOne, stewardTwo, stewardThree;
        pilot = null;
        copilot = null;
        stewardOne = null;
        stewardTwo = null;
        stewardThree = null;

        Employee[] employees = new Control().getEmployeeStaff(flight.getFlight_id());
        for (Employee employee : employees) {
            if (employee.getRole().equals("Pilot")) pilot = employee;
            if (employee.getRole().equals("Copilot")) copilot = employee;
            if (employee.getRole().equals("Steward") && stewardOne == null) {
                stewardOne = employee;
            } else {
                if (employee.getRole().equals("Steward") && stewardTwo == null) {
                    stewardTwo = employee;
                } else {
                    if (employee.getRole().equals("Steward") && stewardThree == null) stewardThree = employee;
                }
            }
        }

        //Unselect Steward pour éviter la popup
        steward1.setSelectedIndex(-1);
        steward2.setSelectedIndex(-1);
        steward3.setSelectedIndex(-1);

        //Pilot
        pilotIndex = 0;
        while (!pilots_list.get(pilotIndex).getEmployee_id().equals(pilot.getEmployee_id())) {
            pilotIndex++;
        }
        pilots.setSelectedIndex(pilotIndex);

        //Copilot
        copilotIndex = 0;
        while (!copilot_list.get(copilotIndex).getEmployee_id().equals(copilot.getEmployee_id())) {
            copilotIndex++;
        }
        copilots.setSelectedIndex(copilotIndex);

        //Steward1
        steward1Index = 0;
        while (!steward_list.get(steward1Index).getEmployee_id().equals(stewardOne.getEmployee_id())) {
            steward1Index++;
        }
        steward1.setSelectedIndex(steward1Index);

        //Steward2
        steward2Index = 0;
        while (!steward_list.get(steward2Index).getEmployee_id().equals(stewardTwo.getEmployee_id())) {
            steward2Index++;
        }
        steward2.setSelectedIndex(steward2Index);

        //Steward3
        steward3Index = 0;
        while (!steward_list.get(steward3Index).getEmployee_id().equals(stewardThree.getEmployee_id())) {
            steward3Index++;
        }
        steward3.setSelectedIndex(steward3Index);

        //Add real_duration_field à infoPanel
        label_real_duration = new JLabel("Actual duration flight :  ");
        label_real_duration.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_real_duration);
        SpinnerNumberModel realDurationModel = new SpinnerNumberModel(0, 0, 1005, 1);
        real_duration_field = new JSpinner(realDurationModel);
        infoPanel.add(real_duration_field);

        //Replace Add Button with Modify Button
        modify = new JButton("Modify");
        modify.addActionListener(new ButtonListener());
        buttonPanel.remove(add);
        buttonPanel.add(modify, 0);

        //Fill the fields with correct info
        departure_date.setValue(flight.getDeparture_time());
        departure_time.setValue(flight.getDeparture_time());
        est_duration_field.setValue(flight.getEstimated_duration());
        real_duration_field.setValue(flight.getReal_duration());
        price_eco_field.setValue(flight.getPrice_adult_eco());
        price_business_field.setValue(flight.getPrice_adult_business());
        price_first_class_field.setValue(flight.getPrice_adult_first_class());
        price_child_field.setValue(flight.getPrice_child());
        price_insurance_field.setValue(flight.getCancel_insurance_price());
        check_wifi.setSelected(flight.isHas_wifi());
        comment_area.setText(flight.getCommentary());

        repaint();
        revalidate();
    }

    public void setInfoPanel() throws ConnexionException, OperationDataException {
        //Créer panel
        infoPanel = new JPanel(new GridLayout(12, 2));

        //Objet Date pour les spinner
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.YEAR, -10);
        Date date_min = calendar.getTime();
        calendar.add(Calendar.YEAR, 20);
        Date date_max = calendar.getTime();

        //Departure Date
        label_date = new JLabel("Departure date :  ");
        label_date.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_date);

        SpinnerDateModel dateModel = new SpinnerDateModel(today, date_min, date_max, Calendar.YEAR);
        departure_date = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(departure_date, "dd-MM-yyyy");
        departure_date.setEditor(dateEditor);
        infoPanel.add(departure_date);

        //Departure time
        label_time = new JLabel("Departure time :  ");
        label_time.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_time);

        SpinnerDateModel hourModel = new SpinnerDateModel(today, null, null, Calendar.MINUTE);
        departure_time = new JSpinner(hourModel);
        JSpinner.DateEditor hourEditor = new JSpinner.DateEditor(departure_time, "hh:mm a");
        hourEditor.getTextField().setEditable(false);
        departure_time.setEditor(hourEditor);
        infoPanel.add(departure_time);

        //Estimated duration
        label_est_duration = new JLabel("Estimated duration (express in minutes) :  ");
        label_est_duration.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_est_duration);

        SpinnerNumberModel estDurationModel = new SpinnerNumberModel(15, 15, 1005, 15);
        est_duration_field = new JSpinner(estDurationModel);
        infoPanel.add(est_duration_field);

        //Plane
        label_plane = new JLabel("Plane :  ");
        label_plane.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_plane);

        plane_list = new Control().getAllPlanes();
        planes = new JComboBox<>();
        fillPlanesComboBox(plane_list, planes);
        planes.setSelectedIndex(0);
        planes.addItemListener(new PlaneListener());
        infoPanel.add(planes);

        //Price adult eco
        label_price_eco = new JLabel("Price adult in class eco :  ");
        label_price_eco.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_price_eco);

        SpinnerNumberModel ecoClassModel = new SpinnerNumberModel(0.0, 0.0, 800.0, 5.0);
        price_eco_field = new JSpinner(ecoClassModel);
        infoPanel.add(price_eco_field);

        //Price adult business
        label_price_business = new JLabel("Price adult in business class :  ");
        label_price_business.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_price_business);

        SpinnerNumberModel businessClassModel = new SpinnerNumberModel(0.0, 0.0, 1000.0, 10.0);
        price_business_field = new JSpinner(businessClassModel);
        if (!plane_list.get(0).has_business_class()) price_business_field.setEnabled(false);
        infoPanel.add(price_business_field);

        //Price adult 1st
        label_first_class = new JLabel("Price adult in 1st class :  ");
        label_first_class.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_first_class);

        SpinnerNumberModel firstClassModel = new SpinnerNumberModel(0.0, 0.0, 1500.0, 20.0);
        price_first_class_field = new JSpinner(firstClassModel);
        if (!plane_list.get(0).has_first_class()) price_first_class_field.setEnabled(false);
        infoPanel.add(price_first_class_field);

        //Price child
        label_price_child = new JLabel("Price child :  ");
        label_price_child.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_price_child);

        SpinnerNumberModel childModel = new SpinnerNumberModel(0.0, 0.0, 800.0, 5.0);
        price_child_field = new JSpinner(childModel);
        infoPanel.add(price_child_field);

        //Price cancel insurance
        label_price_insurance = new JLabel("Price cancel insurance :  ");
        label_price_insurance.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_price_insurance);

        SpinnerNumberModel insuranceModel = new SpinnerNumberModel(0.0, 0.0, 1000.0, 5.0);
        price_insurance_field = new JSpinner(insuranceModel);
        infoPanel.add(price_insurance_field);

        //Has wifi
        label_check_wifi = new JLabel("Wifi on board :  ");
        label_check_wifi.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_check_wifi);

        check_wifi = new JCheckBox();
        infoPanel.add(check_wifi);

        //Comment
        label_comment = new JLabel("Comment :  ");
        label_comment.setHorizontalAlignment(JLabel.RIGHT);
        infoPanel.add(label_comment);

        comment_area = new JTextArea(3, 100);
        infoPanel.add(comment_area);

        add(infoPanel);
    }

    public void setEmployeePanel() throws ConnexionException, OperationDataException {
        employeePanel = new JPanel(new GridLayout(5, 2, 0, 15));

        label_pilot = new JLabel("Pilot :  ");
        label_pilot.setHorizontalAlignment(JLabel.RIGHT);
        pilots_list = new Control().getEmployeeByRole("Pilot");
        pilots = new JComboBox();
        fillEmployeeComboBox(pilots_list, pilots);

        label_copilot = new JLabel("Co-pilot :  ");
        label_copilot.setHorizontalAlignment(JLabel.RIGHT);
        copilot_list = new Control().getEmployeeByRole("Copilot");
        copilots = new JComboBox();
        fillEmployeeComboBox(copilot_list, copilots);

        label_steward1 = new JLabel("Steward 1 :  ");
        label_steward1.setHorizontalAlignment(JLabel.RIGHT);
        steward_list = new Control().getEmployeeByRole("Steward");
        steward1 = new JComboBox();
        fillEmployeeComboBox(steward_list, steward1);

        label_steward2 = new JLabel("Steward 2 :  ");
        label_steward2.setHorizontalAlignment(JLabel.RIGHT);
        steward2 = new JComboBox();
        fillEmployeeComboBox(steward_list, steward2);
        steward2.setSelectedIndex(1);

        label_steward3 = new JLabel("Steward 3 :  ");
        label_steward3.setHorizontalAlignment(JLabel.RIGHT);
        steward3 = new JComboBox();
        fillEmployeeComboBox(steward_list, steward3);
        steward3.setSelectedIndex(2);

        steward1.addItemListener(new StewardBoxListener());
        steward2.addItemListener(new StewardBoxListener());
        steward3.addItemListener(new StewardBoxListener());

        employeePanel.add(label_pilot);
        employeePanel.add(pilots);
        employeePanel.add(label_copilot);
        employeePanel.add(copilots);
        employeePanel.add(label_steward1);
        employeePanel.add(steward1);
        employeePanel.add(label_steward2);
        employeePanel.add(steward2);
        employeePanel.add(label_steward3);
        employeePanel.add(steward3);

        container.add(employeePanel);
    }

    public void setAirportPanel() throws ConnexionException, OperationDataException {
        //Creer Panel
        airportPanel = new JPanel(new GridLayout(4, 2, 0, 15));

        //Récupérer les Pays
        country_list = new Control().getAll_country();
        array_countries = new String[country_list.size()];
        int i = 0;
        while (i < country_list.size()) {
            array_countries[i] = country_list.get(i).getCountry_name();
            i++;
        }

        //Creer Label et ComboBox
        label_country_dep = new JLabel("Departure country :  ");
        label_country_dep.setHorizontalAlignment(JLabel.RIGHT);
        countryDep = new JComboBox(array_countries);
        countryDep.setSelectedIndex(-1); // = no selection
        countryDep.addItemListener(new CountryBoxListener());
        label_airport_dep = new JLabel("Departure airport :  ");
        label_airport_dep.setHorizontalAlignment(JLabel.RIGHT);
        airportDep = new JComboBox();
        airportDep.setEnabled(false);
        label_country_arr = new JLabel("Arrival country :  ");
        label_country_arr.setHorizontalAlignment(JLabel.RIGHT);
        countryArr = new JComboBox(array_countries);
        countryArr.setSelectedIndex(-1);
        countryArr.addItemListener(new CountryBoxListener());
        label_airport_arr = new JLabel("Arrival airport :  ");
        label_airport_arr.setHorizontalAlignment(JLabel.RIGHT);
        airportArr = new JComboBox();
        airportArr.setEnabled(false);

        airportPanel.add(label_country_dep);
        airportPanel.add(countryDep);
        airportPanel.add(label_airport_dep);
        airportPanel.add(airportDep);
        airportPanel.add(label_country_arr);
        airportPanel.add(countryArr);
        airportPanel.add(label_airport_arr);
        airportPanel.add(airportArr);

        container.add(airportPanel);
    }

    public void setMealPanel() {
        mealPanel = new JPanel(new GridLayout(2, 2));

        label_check_meal = new JLabel("On-board meal :  ");
        label_check_meal.setHorizontalAlignment(JLabel.RIGHT);
        check_meal = new JCheckBox();
        check_meal.addItemListener(new CheckBoxListener());
        label_meal = new JLabel("Meal :  ");
        label_meal.setHorizontalAlignment(JLabel.RIGHT);
        meals = new JComboBox<>();
        meals.setEnabled(false);

        mealPanel.add(label_check_meal);
        mealPanel.add(check_meal);
        mealPanel.add(label_meal);
        mealPanel.add(meals);

        container.add(mealPanel);
    }

    public void setButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(1, 3));

        add = new JButton("Add");
        add.addActionListener(new ButtonListener());
        erase = new JButton("Erase");
        erase.addActionListener(new ButtonListener());
        quit = new JButton("Cancel");
        quit.addActionListener(new ButtonListener());

        buttonPanel.add(add);
        buttonPanel.add(erase);
        buttonPanel.add(quit);

        container.add(buttonPanel);
    }

    private void fillPlanesComboBox(ArrayList<Plane> list, JComboBox<String> comboBox) {
        String entry;
        for (Plane plane : list) {
            entry = plane.getPlane_type();
            entry += " (ID " + plane.getPlane_id() + ") ";
            entry += plane.getNumber_seats() + " seats";
            comboBox.addItem(entry);
        }
    }

    private void fillEmployeeComboBox(ArrayList<Employee> list, JComboBox<String> comboBox) {
        for (Employee e : list) {
            comboBox.addItem(e.toString());
        }
    }

    private Date getDepartureDate() {
        GregorianCalendar hour = new GregorianCalendar();
        hour.setTime((Date) departure_time.getValue());

        GregorianCalendar date = new GregorianCalendar();
        date.setTime((Date) departure_date.getValue());

        hour.set(date.get(GregorianCalendar.YEAR), date.get(GregorianCalendar.MONTH), date.get(GregorianCalendar.DAY_OF_MONTH));
        return hour.getTime();
    }

    private class CountryBoxListener implements ItemListener {
        private JComboBox<String> airport, country;

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            try {
                if (itemEvent.getSource() == countryDep) {
                    country = countryDep;
                    airport = airportDep;

                    //MAJ Meal Combobox
                    meals.removeAllItems();
                    meals_list = new Control().getMealsByCountries(countryDep.getSelectedIndex() + 1);
                    for (Meal meal : meals_list) {
                        meals.addItem(meal.getMeal_name());
                    }

                } else {
                    country = countryArr;
                    airport = airportArr;
                }

                //MAJ des Combobox de Airport
                if (!airport.isEnabled()) airport.setEnabled(true);
                airport.removeAllItems();
                list_airports = new Control().getAirportByCountry(country.getSelectedIndex() + 1);
                for (Airport a : list_airports) {
                    airport.addItem(a.getCity_name());
                }

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    private class StewardBoxListener implements ItemListener {

        public void itemStateChanged(ItemEvent event) {

            if (event.getStateChange() == ItemEvent.SELECTED) {
                if (steward1.getSelectedIndex() == steward2.getSelectedIndex() || steward1.getSelectedIndex() == steward3.getSelectedIndex()
                        || steward2.getSelectedIndex() == steward3.getSelectedIndex() && steward1.getSelectedIndex() > 0
                        && steward2.getSelectedIndex() > 0 && steward3.getSelectedIndex() > 0) {
                    JOptionPane.showMessageDialog(null, "You can't select the same steward more than once !");
                    add.setEnabled(false);
                } else {
                    add.setEnabled(true);
                }
            }
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getSource() == add || event.getSource() == modify) {
                    //confirm
                    if (countryDep.getSelectedIndex() >= 0 && countryArr.getSelectedIndex() >= 0) {
                        Airport arr = null;
                        Airport dep = null;

                        Plane plane = plane_list.get(planes.getSelectedIndex());

                        Employee[] employees = new Employee[5];
                        employees[0] = pilots_list.get(pilots.getSelectedIndex());
                        employees[1] = copilot_list.get(copilots.getSelectedIndex());
                        employees[2] = steward_list.get(steward1.getSelectedIndex());
                        employees[3] = steward_list.get(steward2.getSelectedIndex());
                        employees[4] = steward_list.get(steward3.getSelectedIndex());


                        list_airports = new Control().getAirportByCountry(countryDep.getSelectedIndex() + 1);
                        dep = list_airports.get(airportDep.getSelectedIndex());
                        list_airports = new Control().getAirportByCountry(countryArr.getSelectedIndex() + 1);
                        arr = list_airports.get(airportArr.getSelectedIndex());

                        Meal meal;
                        if (check_meal.isSelected()) {
                            int i = 0;
                            while (i < meals_list.size() && meals.getSelectedItem() != meals_list.get(i).getMeal_name()) {
                                i++;
                            }
                            meal = meals_list.get(i);
                        } else meal = null;

                        Flight newFlight = new Flight(getDepartureDate(), (Integer) est_duration_field.getValue(), (Double) price_eco_field.getValue(),
                                (Double) price_business_field.getValue(), (Double) price_first_class_field.getValue(),
                                (Double) price_child_field.getValue(), (Double) price_insurance_field.getValue(), check_wifi.isSelected(),
                                comment_area.getText(), plane, arr, dep, meal);

                        if (event.getSource() == add) {
                            new Control().insertFlight(newFlight, employees);
                            JOptionPane.showMessageDialog(null, "Flight added successfully");
                        } else {
                            newFlight.setReal_duration((Integer) real_duration_field.getValue());
                            newFlight.setFlight_id(flight.getFlight_id());
                            new Control().modifyFlight(newFlight, employees);
                            JOptionPane.showMessageDialog(null, "Flight modified successfully");
                        }
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select the airports correctly !");
                    }
                } else {
                    if (event.getSource() == erase) {
                        //erase
                        departure_date.setValue(new Date());
                        departure_time.setValue(new Date());
                        est_duration_field.setValue(15);
                        planes.setSelectedIndex(0);
                        price_eco_field.setValue(0);
                        price_business_field.setValue(0);
                        price_first_class_field.setValue(0);
                        price_child_field.setValue(0);
                        price_insurance_field.setValue(0);
                        check_wifi.setSelected(false);
                        comment_area.removeAll();
                        countryDep.setSelectedIndex(-1);
                        countryArr.setSelectedIndex(-1);
                        pilots.setSelectedIndex(0);
                        copilots.setSelectedIndex(0);
                        steward1.setSelectedIndex(0);
                        steward2.setSelectedIndex(1);
                        steward3.setSelectedIndex(2);
                        check_meal.setSelected(false);
                    } else {
                        dispose();
                    }
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    private class CheckBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            meals.setEnabled(check_meal.isSelected());
        }
    }

    private class PlaneListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            int index = planes.getSelectedIndex() - 1;
            price_business_field.setEnabled(plane_list.get(index).has_business_class());
            if (!price_business_field.isEnabled()) price_business_field.setValue(0.0);
            price_first_class_field.setEnabled(plane_list.get(index).has_first_class());
            if (!price_first_class_field.isEnabled()) price_first_class_field.setValue(0.0);
        }
    }
}
