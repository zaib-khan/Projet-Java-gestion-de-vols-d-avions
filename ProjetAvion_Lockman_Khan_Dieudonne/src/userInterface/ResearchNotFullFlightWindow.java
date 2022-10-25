package userInterface;

import controller.Control;
import exception.ConnexionException;
import exception.OperationDataException;
import exception.ResearchDateException;
import model.Country;
import model.ResearchEmployees;
import model.ResearchFlights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ResearchNotFullFlightWindow extends JFrame {

    private Container container;
    private JLabel label_country, label_isDestination;
    private JComboBox combo_country_name_list;
    private JCheckBox checkBox_isDestination;
    private JPanel panel_form, panel_button;
    private JButton display, cancel;

    private ArrayList<Country> list_country;
    private String [] tab_Country;

    public ResearchNotFullFlightWindow(){

        super("Research not full flight with country");
        setBounds(550,150,400,130);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );
        setVisible(true);
        setResizable(false);

        panel_form = new JPanel(new GridLayout(2,2,0,10));
        container.add(panel_form,BorderLayout.NORTH);

        panel_button = new JPanel(new GridLayout(1,2));
        container.add(panel_button,BorderLayout.SOUTH);

        label_country = new JLabel("Select country : ");
        label_country.setHorizontalAlignment(SwingConstants.RIGHT);
        label_isDestination = new JLabel("Is destination : ");
        label_isDestination.setHorizontalAlignment(SwingConstants.RIGHT);




        try {
            list_country = new Control().getAll_country();
            tab_Country = new String[list_country.size()];

            for (Country country: list_country) {
                addCountryToTable(country.getCountry_name());
            }

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        combo_country_name_list = new JComboBox(tab_Country);
        checkBox_isDestination = new JCheckBox("Yes");

        panel_form.add(label_country);
        panel_form.add(combo_country_name_list);
        panel_form.add(label_isDestination);
        panel_form.add(checkBox_isDestination);


        display = new JButton("Display");
        cancel = new JButton("Cancel");

        panel_button.add(display);
        panel_button.add(cancel);

        display.addActionListener(new SaveEvent());
        cancel.addActionListener(new CancelEvent());


    }


    public void addCountryToTable(String name){
        int i = 0;
        while (i<tab_Country.length && tab_Country[i] != null){
            i++;
        }
        tab_Country[i] = name;
    }

    public Country getCountry(String country){
        Country res = null;
        for(Country var: list_country) {
            if (var.getCountry_name().equals(country)) res = var;
        }
        return  res;
    }



    private class SaveEvent implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
           try {
                ArrayList<ResearchFlights> list = new Control().researchFlights(getCountry(combo_country_name_list.getSelectedItem().toString()),checkBox_isDestination.isSelected());
                if (list.isEmpty()){
                    JOptionPane.showMessageDialog(null,"There were no flights found", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    new ResearchNotFullFlightListWindow(list);
                }

           }
           catch (Exception e){
               JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           }

        }
    }
    private class CancelEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
        }
    }


}
