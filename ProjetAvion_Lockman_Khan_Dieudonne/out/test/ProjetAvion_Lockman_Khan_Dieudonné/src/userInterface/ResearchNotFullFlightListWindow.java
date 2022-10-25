package userInterface;

import model.ResearchEmployees;
import model.ResearchFlights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ResearchNotFullFlightListWindow extends JFrame {

    private Container container;
    private AllResearchFlightsModel model;
    private JTable table;
    private JScrollPane scrollPane;

    public ResearchNotFullFlightListWindow(ArrayList<ResearchFlights> research) {
        super("Not full flights");
        setBounds(200,150,1200,500);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );
        setResizable(false);
        setVisible(true);

        model = new AllResearchFlightsModel(research);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        container.add(scrollPane);


    }


}
