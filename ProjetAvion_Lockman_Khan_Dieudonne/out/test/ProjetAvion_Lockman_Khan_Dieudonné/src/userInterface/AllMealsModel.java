package userInterface;

import model.Meal;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class AllMealsModel extends AbstractTableModel {

    private ArrayList<String> columnName;
    private ArrayList<Meal> contents;

    public AllMealsModel(ArrayList<Meal> meals){
        columnName = new ArrayList<>();
        columnName.add("meal_id");
        columnName.add("meal_name");
        columnName.add("is_hot");
        columnName.add("price_adult");
        columnName.add("price_child");
        columnName.add("spicy_level");
        columnName.add("availibility_date");
        columnName.add("country");
        setContents(meals);
    }


    public void setContents(ArrayList<Meal> meals){
        this.contents = meals;
    }


    @Override
    public int getRowCount() { // retourne le nombre de meals
        return contents.size();
    }

    @Override
    public int getColumnCount() { // retourne le nombre de colonne
        return columnName.size();
    }

    public String getColumnName(int column){
        return columnName.get(column);
    }

    @Override
    public Object getValueAt(int row, int column) {
        Meal meal = contents.get(row);
        switch (column){
            case 0: return meal.getMeal_id();
            case 1: return meal.getMeal_name();
            case 2: return meal.is_hot();
            case 3: return meal.getPrice_adult();
            case 4: return meal.getPrice_child();
            case 5: return meal.getSpicy_level();
            case 6: return meal.getSimpleDate();
            case 7: return meal.getCountry().getCountry_name();
            default: return null;
        }
    }

    public Class getColumnClass(int column){
        Class c;

        switch (column){
            case 0: c = Integer.class;
                break;
            case 1: c = String.class;
                break;
            case 2: c = Boolean.class;
                break;
            case 3: c = Double.class;
                break;
            case 4: c = Double.class;
                break;
            case 5: c = Integer.class;
                break;
            case 6: c = Date.class;
                break;
            case 7: c = String.class;
                break;
            default: c = String.class;
        }
        return c;
    }


}
