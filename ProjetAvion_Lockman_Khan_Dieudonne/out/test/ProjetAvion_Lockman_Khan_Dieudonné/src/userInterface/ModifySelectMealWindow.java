package userInterface;

import controller.Control;
import model.Meal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ModifySelectMealWindow extends JFrame {

    private JPanel panel_meal, panel_bouton;
    private JLabel label_meal_name;
    private JComboBox combo_meal;
    private ArrayList<Meal> meal_list;
    private String [] array_meal;
    private JButton button_select, button_cancel;

    private Container container;


    public ModifySelectMealWindow(){

        super("Modification of a meal");
        setBounds(530,150,450,130);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );
        setResizable(false);
        setVisible(true);

        panel_meal = new JPanel(new GridLayout(1,2));
        panel_bouton = new JPanel(new GridLayout(1,2));

        container.add(panel_meal,BorderLayout.NORTH);
        container.add(panel_bouton,BorderLayout.SOUTH);

        label_meal_name = new JLabel("Select a meal : ");
        label_meal_name.setHorizontalAlignment(SwingConstants.RIGHT);

        try{
            meal_list = new Control().getAllMeal();
            array_meal = new String[meal_list.size()];

            for(Meal var : meal_list){
                addMealToTable(var.getMeal_name());
            }


        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        combo_meal = new JComboBox(array_meal);

        button_select = new JButton("Select");
        button_cancel = new JButton("Cancel");

        panel_meal.add(label_meal_name);
        panel_meal.add(combo_meal);

        panel_bouton.add(button_select);
        panel_bouton.add(button_cancel);

        button_cancel.addActionListener(new EvenementCancel());
        button_select.addActionListener(new EvenementSelect());





    }
    public void addMealToTable(String meal){
        int i = 0;
        while (i< array_meal.length && array_meal[i] != null){
            i++;
        }
        array_meal[i] = meal;
    }
    public Meal searchMealInArrayList(String meal){
        Meal res = new Meal();
        for(Meal var : meal_list){
            if (var.getMeal_name().matches(meal)) res = var;
        }
        return res;
    }
    private class EvenementCancel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
        }
    }


    private class EvenementSelect implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
            new ModifyMealWindow(searchMealInArrayList(array_meal[combo_meal.getSelectedIndex()]));
        }
    }


}
