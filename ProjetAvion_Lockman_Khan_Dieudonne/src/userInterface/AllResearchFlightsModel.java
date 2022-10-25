package userInterface;

import model.ResearchEmployees;
import model.ResearchFlights;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class AllResearchFlightsModel extends AbstractTableModel {

    private ArrayList<String> columnName;
    private ArrayList<ResearchFlights> content;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public AllResearchFlightsModel(ArrayList<ResearchFlights> research){

        columnName = new ArrayList<>();
        columnName.add("Departure city");
        columnName.add("Arrival city");
        columnName.add("flight_id");
        columnName.add("nb_passenger");
        columnName.add("number_seats");
        columnName.add("departure_time");
        setContent(research);
    }


    public void setContent(ArrayList<ResearchFlights> research){
        this.content = research;
    }

    public int getRowCount(){
        return content.size();
    }
    public int getColumnCount(){
        return columnName.size();
    }

    public String getColumnName(int column){
        return columnName.get(column);
    }

    public Object getValueAt(int row, int column){
        ResearchFlights research = content.get(row);
        switch (column){
            case 0: return research.getDeparture_city_name();
            case 1: return research.getArrival_city_name();
            case 2: return research.getFlight_id();
            case 3: return research.getNb_seats_reserved();
            case 4: return research.getNb_seats_on_plane();
            case 5: return sdf.format(research.getDeparture_time());
            default: return null;
        }
    }

    public Class getColumnClass(int column){
        Class c;

        switch (column){
            case 0: c = String.class;
                break;
            case 1: c = String.class;
                break;
            case 2: c = Integer.class;
                break;
            case 3: c = Integer.class;
                break;
            case 4: c = Integer.class;
                break;
            case 5: c = SimpleDateFormat.class;
                break;
            default: c = String.class;
        }
        return c;
    }
}
