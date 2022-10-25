package userInterface;

import controller.Control;
import exception.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ModifyReservationWindow extends JFrame {

    private Container container;
    private JPanel panel_reservation_selection,panel_modification, panel_form, panel_meal, panel_radio_meal, panel_form_options,panel_number_person, panel_number_adult, panel_number_child, panel_button;
    private JTextField text_number_adult, text_number_child,text_adult_meal, text_child_meal;
    private JLabel label_reservation_name, label_class, label_number_adult, label_number_child, label_cancel_insurance, label_meal, label_nb_adult_meal, label_nb_child_meal, label_reservation_date, label_client, label_flight;
    private JCheckBox check_has_cancel_insurance;
    private JComboBox<String> combo_reservation, combo_list_client, combo_list_flight,combo_class;
    private JSpinner date_reservation;
    private JRadioButton button_no_meal, button_all_meal, button_personalized_meal;
    private ButtonGroup buttonGroupMeal;
    private JButton button_save,button_cancel,button_clear;
    private ArrayList<Client> list_client;
    private ArrayList<Flight> list_flight;
    private ArrayList<String> list_class;
    private String [] tab_client;
    private String [] tab_flight;
    private ArrayList<Reservation> list_reservations;
    private String [] tab_Reservations;



    public ModifyReservationWindow() {
        super("Modify a reservation");
        setBounds(530,150,700,550);
        container = this.getContentPane();
        container.setLayout(new GridLayout(3,1,0,10));
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );
        setVisible(true);
        //select reservation
        panel_reservation_selection = new JPanel(new GridLayout(1,2,0,10));
        container.add(panel_reservation_selection,BorderLayout.NORTH);
        label_reservation_name = new JLabel("Select a Reservation : ");
        label_reservation_name.setHorizontalAlignment(SwingConstants.RIGHT);
        try{
            list_reservations = new Control().getAll_reservations();
            tab_Reservations = new String[list_reservations.size()+1];
            tab_Reservations[0] = "--Select a reservation--";

            for(Reservation reservation : list_reservations){
                addReservationToTable(reservation.getReservation_id()+"");
            }


        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        combo_reservation = new JComboBox(tab_Reservations);
        ComboBoxListener combo_reservation_listener = new ComboBoxListener();
        combo_reservation.addItemListener(combo_reservation_listener);

        panel_reservation_selection.add(label_reservation_name);
        panel_reservation_selection.add(combo_reservation);
        //panel modifications
        panel_modification= new JPanel(new GridLayout(4,1,0,10));
        container.add(panel_modification,BorderLayout.CENTER);
        //panel form
        panel_form = new JPanel(new GridLayout(3,2,0,10));
        panel_modification.add(panel_form);

        label_class = new JLabel("Class : ");
        label_class.setHorizontalAlignment(SwingConstants.RIGHT);
        label_client = new JLabel("Client : ");
        label_client.setHorizontalAlignment(SwingConstants.RIGHT);
        label_flight = new JLabel("Flight : ");
        label_flight.setHorizontalAlignment(SwingConstants.RIGHT);

        combo_class = new JComboBox();

        try {
            list_client = new Control().getAll_client();
            tab_client = new String[list_client.size()+1];
            tab_client[0] = "--Select a client--";

            for (Client client: list_client) {
                addClientToTable(client.getFirst_name()+" "+client.getLast_name());
            }

            list_flight = new Control().getAll_flight();
            tab_flight = new String[list_flight.size()+1];
            tab_flight[0] = "--Select a flight--";
            for (Flight flight: list_flight) {
                addFlightToTable(flight.getDeparture_airport().getCity_name()+" -> "+flight.getArrival_airport().getCity_name());
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        combo_list_client = new JComboBox(tab_client);
        combo_list_client.setMaximumRowCount(5);

        combo_list_flight = new JComboBox(tab_flight);
        combo_list_flight.setMaximumRowCount(5);
        ComboBoxListener combo_list_flight_listener = new ComboBoxListener();
        combo_list_flight.addItemListener(combo_list_flight_listener);

        panel_form.add(label_client);
        panel_form.add(combo_list_client);
        panel_form.add(label_flight);
        panel_form.add(combo_list_flight);
        panel_form.add(label_class);
        panel_form.add(combo_class);

        //panel number person
        panel_number_person = new JPanel(new GridLayout(2,1,0,10));
        panel_modification.add(panel_number_person);
        panel_number_adult = new JPanel(new GridLayout(1,2,0,10));
        panel_number_person.add(panel_number_adult);
        panel_number_child = new JPanel(new GridLayout(1,2,0,10));
        panel_number_person.add(panel_number_child);

        label_number_adult = new JLabel("Adult : ");
        label_number_adult.setHorizontalAlignment(SwingConstants.RIGHT);
        label_number_child = new JLabel("Child : ");
        label_number_child.setHorizontalAlignment(SwingConstants.RIGHT);

        TextListener listener = new TextListener();
        text_number_adult = new JTextField("0");
        text_number_adult.addActionListener(listener);
        text_number_child = new JTextField("0");
        text_number_child.addActionListener(listener);

        panel_number_adult.add(label_number_adult);
        panel_number_adult.add(text_number_adult);
        panel_number_child.add(label_number_child);
        panel_number_child.add(text_number_child);

        //panel meal
        panel_meal = new JPanel(new GridLayout(3,2,0,10));
        panel_modification.add(panel_meal);

        label_meal = new JLabel("Meals : ");
        label_meal.setHorizontalAlignment(SwingConstants.RIGHT);
        label_nb_adult_meal = new JLabel("Adult meals : ");
        label_nb_adult_meal.setHorizontalAlignment(SwingConstants.RIGHT);
        label_nb_child_meal = new JLabel("Child meals : ");
        label_nb_child_meal.setHorizontalAlignment(SwingConstants.RIGHT);

        text_adult_meal = new JTextField("0");
        text_adult_meal.setEditable(false);
        text_child_meal = new JTextField("0");
        text_child_meal.setEditable(false);

        panel_radio_meal = new JPanel(new GridLayout(1,3));
        button_no_meal = new JRadioButton("No meal",true);
        panel_radio_meal.add(button_no_meal);
        button_all_meal = new JRadioButton("All meal",false);
        panel_radio_meal.add(button_all_meal);
        button_personalized_meal = new JRadioButton("Personalized meal",false);
        panel_radio_meal.add(button_personalized_meal);
        buttonGroupMeal = new ButtonGroup();
        buttonGroupMeal.add(button_no_meal);
        buttonGroupMeal.add(button_all_meal);
        buttonGroupMeal.add(button_personalized_meal);


        RadioButtonListener radioButtonlistener = new RadioButtonListener();
        button_no_meal.addItemListener(radioButtonlistener);
        button_all_meal.addItemListener(radioButtonlistener);
        button_personalized_meal.addItemListener(radioButtonlistener);

        panel_meal.add(label_meal);
        panel_meal.add(panel_radio_meal);
        panel_meal.add(label_nb_adult_meal);
        panel_meal.add(text_adult_meal);
        panel_meal.add(label_nb_child_meal);
        panel_meal.add(text_child_meal);

        //panel reservation options
        panel_form_options = new JPanel(new GridLayout(2,2,0,10));
        panel_modification.add(panel_form_options);
        label_cancel_insurance = new JLabel("Have a cancel insurance : ");
        label_cancel_insurance.setHorizontalAlignment(SwingConstants.RIGHT);
        label_reservation_date = new JLabel("Reservation date : ");
        label_reservation_date.setHorizontalAlignment(SwingConstants.RIGHT);

        check_has_cancel_insurance = new JCheckBox("Yes");

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.YEAR, -1);
        Date date_min = calendar.getTime();
        calendar.add(Calendar.YEAR, 10);
        Date date_max = calendar.getTime();
        SpinnerDateModel model = new SpinnerDateModel(today,date_min,date_max, Calendar.YEAR);
        date_reservation = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date_reservation, "dd-MM-yyyy");
        date_reservation.setEditor(editor);
        date_reservation.setEnabled(false);

        panel_form_options.add(label_reservation_date);
        panel_form_options.add(date_reservation);
        panel_form_options.add(label_cancel_insurance);
        panel_form_options.add(check_has_cancel_insurance);

        //panel buttons
        panel_button = new JPanel(new GridLayout(1,3));
        container.add(panel_button,BorderLayout.SOUTH);

        button_save = new JButton("Save");
        button_clear = new JButton("Clear");
        button_cancel = new JButton("Cancel");

        panel_button.add(button_save);
        panel_button.add(button_clear);
        panel_button.add(button_cancel);

        button_save.addActionListener(new EventSave());
        button_save.setEnabled(false);
        button_clear.addActionListener(new EventClear());
        button_clear.setEnabled(false);
        button_cancel.addActionListener(new EventCancel());

    }

    public void addReservationToTable(String reservation){
        int i = 0;
        while (i<tab_Reservations.length && tab_Reservations[i] != null){
            i++;
        }
        tab_Reservations[i] = reservation;
    }
    public void addClientToTable(String name){
        int i = 0;
        while (i<tab_client.length && tab_client[i] != null){
            i++;
        }
        tab_client[i] = name;
    }
    public void addFlightToTable(String name){
        int i = 0;
        while (i<tab_flight.length && tab_flight[i] != null){
            i++;
        }
        tab_flight[i] = name;
    }
    public int getClientIndex(String client){
        int i = 0;
        while (i < tab_client.length && !tab_client[i].equals(client)){
            i++;
        }
        return i;
    }
    public int getFlightIndex(String flight){
        int i = 0;
        while (i < tab_flight.length && !tab_flight[i].equals(flight)){
            i++;
        }
        return i;
    }
    public int getClassIndex(String searched_flight_class){
        return list_class.indexOf(searched_flight_class);
    }

    private class TextListener implements ActionListener  {
        public void actionPerformed ( ActionEvent event) {
            if (event.getSource() == text_number_adult && button_all_meal.isSelected()){
                text_adult_meal.setText(text_number_adult.getText());
            }else if (event.getSource() == text_number_child && button_all_meal.isSelected()){
                text_child_meal.setText(text_number_child.getText());
            }
        }
    }
    private class ComboBoxListener implements ItemListener {
        public void itemStateChanged( ItemEvent event) {
            if (event.getSource() == combo_list_flight) {
                int selected = combo_list_flight.getSelectedIndex() - 1;
                if( selected != -1){
                    combo_class.removeAllItems();
                    list_class = list_flight.get(selected).getListClass();
                    for (String flight_class : list_class) {
                        combo_class.addItem(flight_class);
                    }
                    combo_class.setSelectedIndex(0);
                    combo_class.setEnabled(true);

                    if (list_flight.get(selected).getMeal() == null) {
                        text_adult_meal.setText("0");
                        text_child_meal.setText("0");
                        label_meal.setText("Meal ( No meals on board ):");
                        label_nb_adult_meal.setText("Adult meals :");
                        label_nb_child_meal.setText("Child meals :");
                        panel_meal.setEnabled(false);
                    } else {
                        label_meal.setText("Meal ( " + list_flight.get(selected).getMeal().getMeal_name() + " ):");
                        label_nb_adult_meal.setText("Adult meals ( " + list_flight.get(selected).getMeal().getPrice_adult() + "€ ):");
                        label_nb_child_meal.setText("Child meals ( " + list_flight.get(selected).getMeal().getPrice_child() + "€ ):");
                        panel_meal.setEnabled(true);
                    }
                }
            }else{
                int selected = combo_reservation.getSelectedIndex() - 1;
                if (selected == -1) {
                    button_save.setEnabled(false);
                    button_clear.setEnabled(false);

                } else {
                    try {
                        Reservation reservation = new Control().getReservationBy_id(list_reservations.get(selected).getReservation_id());
                        combo_list_client.setSelectedIndex(getClientIndex(reservation.getClient().getFirst_name()+" "+reservation.getClient().getLast_name()));
                        combo_list_flight.setSelectedIndex(getFlightIndex(reservation.getFlight().getDeparture_airport().getCity_name()+" -> "+reservation.getFlight().getArrival_airport().getCity_name()));
                        combo_class.setSelectedIndex(getClassIndex(reservation.getClass_flight()));
                        text_number_adult.setText(Integer.toString(reservation.getNumber_adult()));
                        text_number_child.setText(Integer.toString(reservation.getNumber_child()));
                        button_personalized_meal.setSelected(true);
                        text_adult_meal.setText(Integer.toString(reservation.getNb_adult_meal()));
                        text_child_meal.setText(Integer.toString(reservation.getNb_child_meal()));
                        date_reservation.setValue(reservation.getReservation_date().getTime());
                        check_has_cancel_insurance.setSelected(reservation.getHas_cancel_insurance());
                        button_save.setEnabled(true);
                        button_clear.setEnabled(true);
                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    private class RadioButtonListener implements ItemListener {
        public void itemStateChanged( ItemEvent event) {
            if (event.getSource() == button_no_meal && event.getStateChange()== ItemEvent.SELECTED) {
                text_adult_meal.setEditable(false);
                text_child_meal.setEditable(false);
                text_adult_meal.setText("0");
                text_child_meal.setText("0");
            }
            else if (event.getSource() == button_all_meal && event.getStateChange()== ItemEvent.SELECTED){
                text_adult_meal.setEditable(false);
                text_child_meal.setEditable(false);
                text_adult_meal.setText(text_number_adult.getText());
                text_child_meal.setText(text_number_child.getText());
            }
            else if (event.getSource() == button_personalized_meal && event.getStateChange()== ItemEvent.SELECTED) {
                text_adult_meal.setEditable(true);
                text_child_meal.setEditable(true);
            }
        }
    }

    public int getNumber_adult() throws NumberFormatException, PositiveNumberException{
        int number_adults = Integer.parseInt(text_number_adult.getText());
        if (number_adults<0) throw new PositiveNumberException(number_adults);
        return number_adults;
    }
    public int getNumber_child() throws NumberFormatException, PositiveNumberException{
        int number_child = Integer.parseInt(text_number_child.getText());
        if (number_child<0) throw new PositiveNumberException(number_child);
        return number_child;
    }
    public int getHas_cancel_insurance(){
        int has_cancel_insurance = check_has_cancel_insurance.isSelected() ? 1 : 0;
        return has_cancel_insurance;
    }
    public int getAdult_meals() throws NumberFormatException, PositiveNumberException{
        int adult_meal = Integer.parseInt(text_adult_meal.getText());
        if (adult_meal<0) throw new PositiveNumberException(adult_meal);
        return adult_meal;
    }
    public int getChild_meals() throws NumberFormatException, PositiveNumberException{
        int child_meals = Integer.parseInt(text_child_meal.getText());
        if (child_meals<0) throw new PositiveNumberException(child_meals);
        return child_meals;
    }
    public GregorianCalendar getReservation_date() {
        GregorianCalendar reservation_date = new GregorianCalendar();
        reservation_date.setTime((Date)date_reservation.getValue());
        return reservation_date;
    }
    public Client getClient()throws  NoSelectedException{
        if (combo_list_client.getSelectedIndex() == 0)throw new NoSelectedException("client");
        return list_client.get(combo_list_client.getSelectedIndex()-1);
    }
    public Flight getFlight()throws  NoSelectedException{
        if (combo_list_flight.getSelectedIndex() == 0)throw new NoSelectedException("flight");
        return list_flight.get(combo_list_flight.getSelectedIndex()-1);
    }

    private class EventCancel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
        }
    }
    private class EventClear implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent) {
            combo_list_client.setSelectedIndex(0);
            combo_list_flight.setSelectedIndex(0);
            combo_class.setSelectedIndex(0);
            text_number_adult.setText("0");
            text_number_child.setText("0");
            button_no_meal.setSelected(true);
            text_adult_meal.setText("0");
            text_child_meal.setText("0");
            check_has_cancel_insurance.setSelected(false);
        }
    }
    private class EventSave implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent) {

            try {
                if (( getNumber_adult()+ getNumber_child()) == 0) throw new NoOneAdded();
                if (getAdult_meals() > getNumber_adult()) throw new TooManyMeals("adult");
                if (getChild_meals() > getNumber_child()) throw new TooManyMeals("child");
                Flight flight = getFlight();
                Reservation reservation = new Control().getReservationBy_id(list_reservations.get(combo_reservation.getSelectedIndex() - 1).getReservation_id());
                int free_places = Control.getFreePlacesByFlight_id(flight.getFlight_id())+ reservation.getNumber_person();
                if (( getNumber_adult()+ getNumber_child()) > free_places) throw new NotEnoughSpaceInPlane(free_places);
                if ( combo_reservation.getSelectedIndex() == 0) throw new NoSelectedException("reservation");
                Reservation modifiedReservation = new Reservation(list_reservations.get(combo_reservation.getSelectedIndex()-1).getReservation_id(),combo_class.getSelectedItem().toString(),getNumber_adult(),getNumber_child(),getHas_cancel_insurance(),getAdult_meals(),getChild_meals(),getReservation_date(),getClient(),getFlight());
                new Control().updateReservation(modifiedReservation);

                JOptionPane.showMessageDialog(null, "Updated "+ modifiedReservation.toString(), "Update confirmation", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
