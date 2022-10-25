package userInterface;

import controller.Control;
import exception.ResearchDateException;
import model.Country;
import model.ResearchEmployees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ResearchEmployeesWindow extends JFrame {

    private Container container;
    private JLabel label_country, label_startDate, label_endDate;
    private JComboBox combo_country_name_list;
    private JPanel panel_form, panel_button;
    private JSpinner spinner_startDate, spinner_endDate;
    private JButton save, cancel, erase;

    private ArrayList<Country> list_country;
    private String [] array_Country;

    public ResearchEmployeesWindow(){

        super("Research staff in a country between 2 dates");
        setBounds(550,150,430,200);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );
        setVisible(true);
        setResizable(false);

        panel_form = new JPanel(new GridLayout(3,2,0,10));
        container.add(panel_form,BorderLayout.NORTH);

        panel_button = new JPanel(new GridLayout(1,3));
        container.add(panel_button,BorderLayout.SOUTH);

        label_country = new JLabel("Select country : ");
        label_country.setHorizontalAlignment(SwingConstants.RIGHT);
        label_startDate = new JLabel("Select start date : ");
        label_startDate.setHorizontalAlignment(SwingConstants.RIGHT);
        label_endDate = new JLabel("Select end date : ");
        label_endDate.setHorizontalAlignment(SwingConstants.RIGHT);



        try {
            list_country = new Control().getAll_country();
            array_Country = new String[list_country.size()];

            for (Country country: list_country) {
                addCountryToTable(country.getCountry_name());
            }

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        combo_country_name_list = new JComboBox(array_Country);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.YEAR, -10);
        Date date_min = calendar.getTime();
        calendar.add(Calendar.YEAR, 20);
        Date date_max = calendar.getTime();

        SpinnerDateModel modelStart = new SpinnerDateModel(today,date_min,date_max,Calendar.YEAR);
        spinner_startDate = new JSpinner(modelStart);
        JSpinner.DateEditor editorStart = new JSpinner.DateEditor(spinner_startDate, "dd-MM-yyyy");
        spinner_startDate.setEditor(editorStart);

        SpinnerDateModel modelEnd = new SpinnerDateModel(today,date_min,date_max,Calendar.YEAR);
        spinner_endDate = new JSpinner(modelEnd);
        JSpinner.DateEditor editorEnd = new JSpinner.DateEditor(spinner_endDate, "dd-MM-yyyy");
        spinner_endDate.setEditor(editorEnd);

        panel_form.add(label_country);
        panel_form.add(combo_country_name_list);
        panel_form.add(label_startDate);
        panel_form.add(spinner_startDate);
        panel_form.add(label_endDate);
        panel_form.add(spinner_endDate);

        save = new JButton("Search");
        cancel = new JButton("Cancel");
        erase = new JButton("Reset");

        panel_button.add(save);
        panel_button.add(erase);
        panel_button.add(cancel);

        save.addActionListener(new SaveEvent());
        cancel.addActionListener(new CancelEvent());
        erase.addActionListener(new EraseEvent());


    }




    public void addCountryToTable(String name){
        int i = 0;
        while (i< array_Country.length && array_Country[i] != null){
            i++;
        }
        array_Country[i] = name;
    }
    public GregorianCalendar getStartDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime((Date)spinner_startDate.getValue());
        calendar.set(GregorianCalendar.DATE,calendar.get(GregorianCalendar.DATE)+1);
        return calendar;
    }
    public GregorianCalendar getEndDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime((Date)spinner_endDate.getValue());
        calendar.set(GregorianCalendar.DATE,calendar.get(GregorianCalendar.DATE)+1);
        return calendar;
    }
    public boolean datesOK(){
        boolean res = false;

       StringBuilder string_start = new StringBuilder();
       string_start.append(getStartDate().get(Calendar.YEAR));
       if (getStartDate().get(Calendar.MONTH)+1<10) string_start.append(0);
       string_start.append(getStartDate().get(Calendar.MONTH)+1);
       string_start.append(getStartDate().get(Calendar.DATE)-1);

        StringBuilder string_end = new StringBuilder();
        string_end.append(getEndDate().get(Calendar.YEAR));
        if (getEndDate().get(Calendar.MONTH)+1<10) string_end.append(0);
        string_end.append(getEndDate().get(Calendar.MONTH)+1);
        string_end.append(getEndDate().get(Calendar.DATE)-1);

        int start, end;
        start = Integer.parseInt(string_start.toString());
        end = Integer.parseInt(string_end.toString());

        if (start <= end) res = true;

        return res;
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
               if(!datesOK()) throw new ResearchDateException();

                ArrayList<ResearchEmployees> list = new Control().researchEmployees(getCountry(combo_country_name_list.getSelectedItem().toString()),getStartDate(),getEndDate());

                if (list.isEmpty()){
                    JOptionPane.showMessageDialog(null,"There were no flights found", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    new ResearchEmployeesListWindow(list);
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
    private class EraseEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            combo_country_name_list.setSelectedIndex(0);
            spinner_startDate.setValue(Calendar.getInstance().getTime());
            spinner_endDate.setValue(Calendar.getInstance().getTime());
        }
    }

}
