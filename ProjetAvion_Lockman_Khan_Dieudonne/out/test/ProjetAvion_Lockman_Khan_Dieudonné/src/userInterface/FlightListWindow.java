package userInterface;

import controller.Control;
import model.Airport;
import model.Flight;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FlightListWindow extends JFrame {
    private FlightTable flightTable;
    private JTable table;
    private JButton quit;
    private JScrollPane tablePanel;

    public FlightListWindow(){
        super("Flights list");
        try{
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLayout(new BorderLayout());

            flightTable = new FlightTable(new Control().getAll_flight());
            table = new JTable(flightTable);
            tablePanel = new JScrollPane(table);
            add(tablePanel,BorderLayout.CENTER);

            quit = new JButton("Quit");
            quit.addActionListener(new ButtonListener());
            add(quit,BorderLayout.SOUTH);

            setVisible(true);

        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(null,exception.getMessage());
        }
    }

    private class FlightTable extends AbstractTableModel{
        private ArrayList<String> columnNames;
        private ArrayList<Flight> flights;
        private SimpleDateFormat sdf;

        public FlightTable(ArrayList<Flight> flights){
            this.flights= flights;
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            columnNames = new ArrayList<>();

            columnNames.add("Departure time");
            columnNames.add("Estimated duration");
            columnNames.add("Real duration");
            columnNames.add("Price class eco");
            columnNames.add("Price class business");
            columnNames.add("Price 1st class");
            columnNames.add("Price child");
            columnNames.add("Price cancel insurance");
            columnNames.add("Plane");
            columnNames.add("From");
            columnNames.add("To");
            columnNames.add("Meal served");

        }

        public int getRowCount(){
            return flights.size();
        }

        public int getColumnCount(){
            return columnNames.size();
        }

        public String getColumnName(int col){
            return columnNames.get(col);
        }

        public Object getValueAt(int row, int col){
            Flight flight = flights.get(row);
            switch (col){
                case 0: return sdf.format(flight.getDeparture_time());
                case 1: return formatMinsToHour(flight.getEstimated_duration());
                case 2 : return (flight.getReal_duration() == 0 ? "N/A" : formatMinsToHour(flight.getReal_duration()));
                case 3:return flight.getPrice_adult_eco() + "€";
                case 4: return (flight.getPrice_adult_business() == 0.0 ? "N/A" : flight.getPrice_adult_business() + "€");
                case 5:return (flight.getPrice_adult_first_class() == 0.0 ? "N/A" : flight.getPrice_adult_first_class() + "€");
                case 6:return flight.getPrice_child() + "€";
                case 7: return flight.getCancel_insurance_price() + "€";
                case 8: return flight.getPlane().getPlane_type();
                case 9: return formatAirportCountry(flight.getDeparture_airport());
                case 10: return formatAirportCountry(flight.getArrival_airport());
                default: return (flight.getMeal() == null ? "N/A" : flight.getMeal().getMeal_name());
            }
        }

        private String formatMinsToHour(int mins){
            int hour = mins / 60;
            int min = mins % 60;
            return String.format("%02d"+"h" + "%02d",hour,min);
        }

        private String formatAirportCountry(Airport airport){
            return airport.getCity_name() + " (" + airport.getCountry().getCountry_name() + ")";
        }

        public Class getColumnClass(int col){
            return String.class;
        }
    }

    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            dispose();
        }
    }
}
