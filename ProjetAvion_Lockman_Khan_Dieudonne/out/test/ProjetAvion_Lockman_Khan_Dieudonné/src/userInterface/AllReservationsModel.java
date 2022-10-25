package userInterface;

import model.Reservation;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;


public class AllReservationsModel extends AbstractTableModel {

    private ArrayList<String> columnName;
    private ArrayList<Reservation> contents;

    public AllReservationsModel(ArrayList<Reservation> reservations){
        columnName = new ArrayList<>();
        columnName.add("reservation_id");
        columnName.add("class");
        columnName.add("number_adult");
        columnName.add("number_child");
        columnName.add("has_cancel_insurance");
        columnName.add("number_adult_meal");
        columnName.add("number_child_meal");
        columnName.add("reservation_date");
        columnName.add("client");
        columnName.add("flight");
        setContents(reservations);
    }


    public void setContents(ArrayList<Reservation> reservations){
        this.contents = reservations;
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
        Reservation reservation = contents.get(row);
        switch (column){
            case 0: return reservation.getReservation_id();
            case 1: return reservation.getClass_flight();
            case 2: return reservation.getNumber_adult();
            case 3: return reservation.getNumber_child();
            case 4: return reservation.getHas_cancel_insurance();
            case 5: return reservation.getNb_adult_meal();
            case 6: return reservation.getNb_child_meal();
            case 7: return reservation.getSimpleDate();
            case 8: return reservation.getClient().getLast_name()+" "+reservation.getClient().getFirst_name();
            case 9: return reservation.getFlight().getFlight_id();
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
            case 2: c = Integer.class;
                break;
            case 3: c = Integer.class;
                break;
            case 4: c = Boolean.class;
                break;
            case 5: c = Integer.class;
                break;
            case 6: c = Integer.class;
                break;
            case 7: c = Date.class;
                break;
            case 8: c = String.class;
                break;
            case 9: c = String.class;
                break;
            default: c = String.class;
        }
        return c;
    }


}
