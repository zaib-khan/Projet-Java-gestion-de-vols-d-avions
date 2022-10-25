package userInterface;

import controller.Control;
import exception.*;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DeleteReservationWindow extends JFrame {

    private Container container;
    private JPanel panel_reservation, panel_reservation_information, panel_button;
    private JLabel label_reservation_name, label_client, label_flight, label_places, label_meals, label_insurance, label_date;
    private JComboBox combo_reservation;
    private ArrayList<Reservation> list_reservations;
    private String [] array_Reservations;
    private JButton button_delete, button_cancel;

    public DeleteReservationWindow(){

        super("Delete a Reservation");
        setBounds(530,150,500,130);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );

        setVisible(true);

        panel_reservation = new JPanel(new GridLayout(1,2));
        panel_reservation_information = new JPanel(new GridLayout(6,1));
        panel_button = new JPanel(new GridLayout(1,2));

        container.add(panel_reservation,BorderLayout.NORTH);
        container.add(panel_reservation_information,BorderLayout.CENTER);
        container.add(panel_button,BorderLayout.SOUTH);


        label_reservation_name = new JLabel("Select a Reservation : ");
        label_reservation_name.setHorizontalAlignment(SwingConstants.RIGHT);

        label_client = new JLabel("");
        label_flight = new JLabel("");
        label_places = new JLabel("");
        label_meals = new JLabel("");
        label_insurance = new JLabel("");
        label_date = new JLabel("");

        try{
            list_reservations = new Control().getAll_reservations();
            array_Reservations = new String[list_reservations.size()+1];
            array_Reservations[0] = "--Select a reservation--";

            for(Reservation reservation : list_reservations){
                addReservationToTable(reservation.getReservation_id()+"");
            }


        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        combo_reservation = new JComboBox(array_Reservations);

        button_delete = new JButton("Remove");
        button_cancel = new JButton("Cancel");



        panel_reservation.add(label_reservation_name);
        panel_reservation.add(combo_reservation);

        panel_reservation_information.add(label_client);
        panel_reservation_information.add(label_flight);
        panel_reservation_information.add(label_places);
        panel_reservation_information.add(label_meals);
        panel_reservation_information.add(label_insurance);
        panel_reservation_information.add(label_date);
        panel_reservation_information.setVisible(false);

        panel_button.add(button_delete);
        panel_button.add(button_cancel);


        button_cancel.addActionListener(new EventCancel());
        combo_reservation.addItemListener(new EventComboBox());
        button_delete.addActionListener(new EventRemove());
        
    }
    public void addReservationToTable(String reservation){
        int i = 0;
        while (i< array_Reservations.length && array_Reservations[i] != null){
            i++;
        }
        array_Reservations[i] = reservation;
    }

    private class EventCancel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
        }
    }
    private class EventComboBox implements ItemListener{
        public void itemStateChanged( ItemEvent event){
            if (combo_reservation.getSelectedIndex() == 0){
                panel_reservation_information.setVisible(false);
                label_client.setText("");
                label_flight.setText("");
                label_places.setText("");
                label_meals.setText("");
                label_insurance.setText("");
                label_date.setText("");
            }else {
                panel_reservation_information.setVisible(true);
                label_client.setText("Client : "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getClient().getLast_name()+" "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getClient().getFirst_name());
                label_flight.setText( "Flight : "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getFlight().getPlane().getPlane_type()+" ( from  "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getFlight().getDeparture_airport().getCity_name()+" to "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getFlight().getArrival_airport().getCity_name()+" ) in "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getClass_flight());
                label_places.setText( "Places : "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getNumber_adult()+ " adult and "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getNumber_child()+" child");
                label_meals.setText( "Meals : "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getNb_adult_meal()+ " adult and "+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getNb_child_meal()+" child");
                label_insurance.setText((list_reservations.get(combo_reservation.getSelectedIndex() - 1).getHas_cancel_insurance()? "With" : "Without")+" cancel insurance");
                label_date.setText(list_reservations.get(combo_reservation.getSelectedIndex() - 1).getReservation_date().get(Calendar.DAY_OF_MONTH)+"/"+(list_reservations.get(combo_reservation.getSelectedIndex() - 1).getReservation_date().get(Calendar.MONTH)+1)+"/"+list_reservations.get(combo_reservation.getSelectedIndex() - 1).getReservation_date().get(Calendar.YEAR));
            }
        }
    }
    private class EventRemove implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try{
                if(combo_reservation.getSelectedIndex() == 0) throw new NoSelectedException("reservation");
                Reservation temp = list_reservations.get(combo_reservation.getSelectedIndex()-1);
                new Control().deleteReservation(temp);
                JOptionPane.showMessageDialog(null,"Removed "+temp.toString(), "Removal confirmation", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}
