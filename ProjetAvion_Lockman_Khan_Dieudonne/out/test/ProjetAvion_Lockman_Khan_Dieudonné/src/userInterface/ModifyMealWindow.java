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

public class ModifyMealWindow extends JFrame {

    private Container container;
    private JPanel panel_form, panel_button, panel_spice_level_panel;

    private JLabel label_meal, label_is_hot, label_price_adult, label_price_child, label_spice_level, label_availability_date, label_country;
    private JCheckBox check_is_hot;
    private JTextField text_meal_name, text_price_adult_meal, text_price_child_meal;
    private JSpinner date_meal;
    private JRadioButton spice_low, spice_medium, spice_high;
    private ButtonGroup spice_group;
    private JComboBox combo_country_name_list;
    private ArrayList<Country> list_country;
    private String [] tab_Country;
    private Integer meal_id;

    private JButton save, cancel, erase;

    public ModifyMealWindow(Meal meal){
        super("Modify the meal");
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

        meal_id = meal.getMeal_id();

        panel_form = new JPanel(new GridLayout(7,2,0,13));
        container.add(panel_form,BorderLayout.NORTH);

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

        text_meal_name = new JTextField(meal.getMeal_name());
        text_price_adult_meal = new JTextField(meal.getPrice_adult().toString());
        text_price_child_meal = new JTextField(meal.getPrice_child().toString());

        check_is_hot = new JCheckBox("Yes");
        if (meal.is_hot()) check_is_hot.setSelected(true);

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
        date_meal.setValue(meal.getAvailibility_date().getTime());

        panel_spice_level_panel = new JPanel(new GridLayout(1,3));
        spice_low = new JRadioButton("Low",(meal.getSpicy_level() == Meal.getLowSpicy()));
        spice_medium = new JRadioButton("Medium",(meal.getSpicy_level() == Meal.getMediumSpicy()));
        spice_high = new JRadioButton("High",(meal.getSpicy_level() == Meal.getHighSpicy()));
        panel_spice_level_panel.add(spice_low);
        panel_spice_level_panel.add(spice_medium);
        panel_spice_level_panel.add(spice_high);
        spice_group = new ButtonGroup();
        spice_group.add(spice_low);
        spice_group.add(spice_medium);
        spice_group.add(spice_high);

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
        combo_country_name_list.setSelectedIndex(getCountryIndex(meal.getCountry().getCountry_name()));

        panel_form.add(label_meal);
        panel_form.add(text_meal_name);
        panel_form.add(label_is_hot);
        panel_form.add(check_is_hot);
        panel_form.add(label_price_adult);
        panel_form.add(text_price_adult_meal);
        panel_form.add(label_price_child);
        panel_form.add(text_price_child_meal);
        panel_form.add(label_spice_level);
        panel_form.add(panel_spice_level_panel);
        panel_form.add(label_country);
        panel_form.add(combo_country_name_list);
        panel_form.add(label_availability_date);
        panel_form.add(date_meal);


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
    public Double getPriceChild() throws EmptyTextFieldException, PriceException {
        if (text_price_child_meal.getText().isEmpty()) throw new EmptyTextFieldException(label_price_child.getText());
        if(!Tools.isValidPrice(text_price_child_meal.getText())) throw new PriceException(text_price_child_meal.getText());
        return Double.parseDouble(text_price_child_meal.getText());
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
        while (i<tab_Country.length && tab_Country[i] != null){
            i++;
        }
        tab_Country[i] = name;
    }
    public int getCountryIndex(String country){
        int i = 0;
        while (i < tab_Country.length && !tab_Country[i].equals(country)){
            i++;
        }
        return i;
    }

    private class EvenementAnnuler implements ActionListener {
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
            combo_country_name_list.setSelectedItem("Afghanistan");
            date_meal.setValue(Calendar.getInstance().getTime());

        }
    }
    private class EvenementEnreigistrer implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            try {
                Meal nouveauMeal = new Meal(meal_id,getMealName(),getIsHot(),getPriceAdult(),getPriceChild(),getSpiceSelection(),getAvailabilityDate(),getCountry(combo_country_name_list.getSelectedItem().toString()));
                new Control().updateMeal(nouveauMeal);

                JOptionPane.showMessageDialog(null, nouveauMeal.toString()+"\nHAS BEEN UPDATED", "Confirmation d'insertion", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
