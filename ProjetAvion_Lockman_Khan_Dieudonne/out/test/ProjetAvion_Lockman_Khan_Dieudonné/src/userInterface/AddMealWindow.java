package userInterface;

import model.Tools;
import controller.Control;
import exception.*;
import model.Country;
import model.Meal;

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

public class AddMealWindow extends JFrame {

    private Container container;
    private JPanel panel_formulaire, panel_button, panel_spice_level_panel;

    private JLabel label_meal, label_is_hot, label_price_adult, label_price_child, label_spice_level, label_availability_date, label_country;
    private JCheckBox check_is_hot;
    private JTextField text_meal_name, text_price_adult_meal, text_price_child_meal;
    private JSpinner date_meal;
    private JRadioButton spice_low, spice_medium, spice_high;
    private ButtonGroup spice_group;
    private JComboBox combo_country_name_list;
    private ArrayList<Country> list_country;
    private String [] array_Country;

    private JButton save, cancel, erase;

    private static Integer NOTSPICY = 0;
    private static Integer LOW_SPICY = 1;
    private static Integer MEDIUM_SPICY = 2;
    private static Integer HIGH_SPICY = 3;


    public AddMealWindow() {
        super("Add a meal");
        setBounds(530,150,450,370);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );
        setResizable(false);
        setVisible(true);

        panel_formulaire = new JPanel(new GridLayout(7,2,0,13));



        container.add(panel_formulaire,BorderLayout.NORTH);


        panel_button = new JPanel(new GridLayout(1,3));
        container.add(panel_button,BorderLayout.SOUTH);

        label_meal = new JLabel("Meal name : ");
        label_meal.setHorizontalAlignment(SwingConstants.RIGHT);
        label_is_hot = new JLabel("Is the meal hot ? : ");
        label_is_hot.setHorizontalAlignment(SwingConstants.RIGHT);
        label_price_adult = new JLabel("Price for adults : ");
        label_price_adult.setHorizontalAlignment(SwingConstants.RIGHT);
        label_price_child = new JLabel("Price for childrens : ");
        label_price_child.setHorizontalAlignment(SwingConstants.RIGHT);
        label_spice_level = new JLabel("Spice level : ");
        label_spice_level.setHorizontalAlignment(SwingConstants.RIGHT);
        label_availability_date = new JLabel("Availability date : ");
        label_availability_date.setHorizontalAlignment(SwingConstants.RIGHT);
        label_country = new JLabel("Country : ");
        label_country.setHorizontalAlignment(SwingConstants.RIGHT);

        text_meal_name = new JTextField();
        text_price_adult_meal = new JTextField();
        text_price_child_meal = new JTextField();

        check_is_hot = new JCheckBox("Yes");

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.YEAR, -1);
        Date date_min = calendar.getTime();
        calendar.add(Calendar.YEAR, 10);
        Date date_max = calendar.getTime();

        SpinnerDateModel model = new SpinnerDateModel(today,date_min,date_max,Calendar.YEAR);
        date_meal = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date_meal, "dd-MM-yyyy");
        date_meal.setEditor(editor);

        panel_spice_level_panel = new JPanel(new GridLayout(1,3));
        spice_low = new JRadioButton("Low",false);
        spice_medium = new JRadioButton("Medium",false);
        spice_high = new JRadioButton("High",false);
        panel_spice_level_panel.add(spice_low);
        panel_spice_level_panel.add(spice_medium);
        panel_spice_level_panel.add(spice_high);
        spice_group = new ButtonGroup();
        spice_group.add(spice_low);
        spice_group.add(spice_medium);
        spice_group.add(spice_high);


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


        panel_formulaire.add(label_meal);
        panel_formulaire.add(text_meal_name);
        panel_formulaire.add(label_is_hot);
        panel_formulaire.add(check_is_hot);
        panel_formulaire.add(label_price_adult);
        panel_formulaire.add(text_price_adult_meal);
        panel_formulaire.add(label_price_child);
        panel_formulaire.add(text_price_child_meal);
        panel_formulaire.add(label_spice_level);
        panel_formulaire.add(panel_spice_level_panel);
        panel_formulaire.add(label_country);
        panel_formulaire.add(combo_country_name_list);
        panel_formulaire.add(label_availability_date);
        panel_formulaire.add(date_meal);

        save = new JButton("Save");
        cancel = new JButton("Cancel");
        erase = new JButton("Erase");

        panel_button.add(save);
        panel_button.add(erase);
        panel_button.add(cancel);

        cancel.addActionListener(new EvenementAnnuler());
        erase.addActionListener(new EvenementEffacer());
        save.addActionListener(new EvenementEnreigistrer());
    }

    //GETTERS
    public String getMealName() throws EmptyTextFieldException{
        if (text_meal_name.getText().isEmpty()) throw new EmptyTextFieldException(label_meal.getText());
        return text_meal_name.getText();
    }
    public boolean getIsHot(){
        return check_is_hot.isSelected();
    }

    public double getPriceAdult() throws EmptyTextFieldException, PriceException {
        if (text_price_adult_meal.getText().isEmpty()) throw new EmptyTextFieldException(label_price_adult.getText());
        if(!Tools.isValidPrice(text_price_adult_meal.getText())) throw new PriceException(text_price_adult_meal.getText());
        return Double.parseDouble(text_price_adult_meal.getText());
    }
    public Double getPriceChild() throws PriceException {
        Double res = null;
        if (!text_price_child_meal.getText().isEmpty()){
            if(!Tools.isValidPrice(text_price_child_meal.getText())) throw new PriceException(text_price_adult_meal.getText());
            res = Double.parseDouble(text_price_child_meal.getText());
        }
        return res;
    }
    public Integer getSpiceSelection(){
        Integer res = null;
        if (spice_group.getSelection() == null) res = Meal.getNOTSPICY();
        if (spice_group.getSelection() == spice_low.getModel()) res = Meal.getLowSpicy();
        if (spice_group.getSelection() == spice_medium.getModel()) res = Meal.getMediumSpicy();
        if (spice_group.getSelection() == spice_high.getModel()) res = Meal.getHighSpicy();
        return res;
    }


    public Country getCountry(String country){
        Country res = null;
        for(Country var: list_country) {
            if (var.getCountry_name().equals(country)) res = var;
        }
        return  res;
    }

    public GregorianCalendar getAvailabilityDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime((Date)date_meal.getValue());
        calendar.set(GregorianCalendar.DATE,calendar.get(GregorianCalendar.DATE)+1);
        return calendar;
    }

    public void addCountryToTable(String name){
        int i = 0;
        while (i< array_Country.length && array_Country[i] != null){
            i++;
        }
        array_Country[i] = name;
    }


    private class EvenementAnnuler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
        }
    }

    private class EvenementEffacer implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            text_meal_name.setText("");
            check_is_hot.setSelected(false);
            text_price_adult_meal.setText("");
            text_price_child_meal.setText("");
            spice_group.clearSelection();
            combo_country_name_list.setSelectedItem(0);
            date_meal.setValue(Calendar.getInstance().getTime());

        }
    }

    private class EvenementEnreigistrer implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            try {
                Meal nouveau;
                if (getPriceChild() == null) {
                    nouveau = new Meal(0, getMealName(), getIsHot(), getPriceAdult(), getSpiceSelection(), getAvailabilityDate(), getCountry(combo_country_name_list.getSelectedItem().toString()));
                } else {
                    nouveau = new Meal(0, getMealName(), getIsHot(), getPriceAdult(), getPriceChild(), getSpiceSelection(), getAvailabilityDate(), getCountry(combo_country_name_list.getSelectedItem().toString()));
                }
                new Control().insertMeal(nouveau);

                JOptionPane.showMessageDialog(null, nouveau.toString() + "\n HAS BEEN ADDED", "Confirmation d'insertion", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
