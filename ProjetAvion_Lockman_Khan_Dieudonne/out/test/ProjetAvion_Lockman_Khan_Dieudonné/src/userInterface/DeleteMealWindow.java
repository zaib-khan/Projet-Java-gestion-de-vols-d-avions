package userInterface;

import controller.Control;
import model.Meal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeleteMealWindow extends JFrame {

    private Container container;
    private JPanel panel_meal, panel_button;
    private JLabel label_meal_name;
    private JComboBox combo_meal;
    private ArrayList<Meal> meal_list;
    private String [] array_meal;
    private JButton boutton_remove, boutton_cancel;

    public DeleteMealWindow(){

        super("Remove of a meal");
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
        panel_button = new JPanel(new GridLayout(1,2));

        container.add(panel_meal,BorderLayout.NORTH);
        container.add(panel_button,BorderLayout.SOUTH);


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

        boutton_remove = new JButton("Remove");
        boutton_cancel = new JButton("Cancel");



        panel_meal.add(label_meal_name);
        panel_meal.add(combo_meal);

        panel_button.add(boutton_remove);
        panel_button.add(boutton_cancel);


        boutton_cancel.addActionListener(new EvenementCancel());
        combo_meal.addItemListener(new EvenementComboBox());
        boutton_remove.addActionListener(new EvenementRemove());



    }

    public void addMealToTable(String meal){
        int i = 0;
        while (i< array_meal.length && array_meal[i] != null){
            i++;
        }
        array_meal[i] = meal;
    }
    public void displayInformationAboutMeal(String meal){
        JOptionPane.showMessageDialog(null, searchMealInArrayList(meal).messageBeforeRemoval(), "Meal Information", JOptionPane.INFORMATION_MESSAGE);
    }
    public Meal searchMealInArrayList(String meal){
        Meal res = new Meal();
        for(Meal var : meal_list){
            if (var.getMeal_name().matches(meal)) res = var;
        }
        return res;
    }

    private class EvenementCancel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
        }
    }

    private class EvenementComboBox implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED){
                displayInformationAboutMeal(event.getItem().toString());
            }
        }
    }

    private class EvenementRemove implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try{
                Meal temp = searchMealInArrayList(array_meal[combo_meal.getSelectedIndex()]);
                new Control().deleteMeal(temp);
                JOptionPane.showMessageDialog(null,temp.toString()+"\n HAS BEEN REMOVED", "Removal confirmation", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}
