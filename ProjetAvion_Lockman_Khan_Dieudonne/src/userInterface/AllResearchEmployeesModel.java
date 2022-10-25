package userInterface;

import model.ResearchEmployees;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class AllResearchEmployeesModel extends AbstractTableModel {

    private ArrayList<String> columnName;
    private ArrayList<ResearchEmployees> content;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public AllResearchEmployeesModel(ArrayList<ResearchEmployees> research){
        columnName = new ArrayList<>();
        columnName.add("first_name");
        columnName.add("last_name");
        columnName.add("role");
        columnName.add("city_name");
        columnName.add("departure_time");
        setContent(research);
    }


    public void setContent(ArrayList<ResearchEmployees> research){
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
        ResearchEmployees research = content.get(row);
        switch (column){
            case 0: return research.getFirst_name();
            case 1: return research.getLast_name();
            case 2: return research.getRole();
            case 3: return research.getCity_name();
            case 4: return sdf.format(research.getDeparture_time());
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
            case 2: c = String.class;
                break;
            case 4: c = String.class;
                break;
            case 5: c = SimpleDateFormat.class;
                break;
            default: c = String.class;
        }
        return c;
    }
}
