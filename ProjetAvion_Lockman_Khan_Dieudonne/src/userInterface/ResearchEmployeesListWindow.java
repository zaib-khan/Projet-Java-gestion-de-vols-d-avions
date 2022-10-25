package userInterface;

import model.ResearchEmployees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ResearchEmployeesListWindow extends JFrame {

    private Container container;
    private AllResearchEmployeesModel model;
    private JTable table;
    private JScrollPane scrollPane;

    public ResearchEmployeesListWindow(ArrayList<ResearchEmployees> research){
        super("Research staff in a country between 2 dates");
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

        model = new AllResearchEmployeesModel(research);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        container.add(scrollPane);





    }




}
