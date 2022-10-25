package userInterface;

import model.ResearchClients;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

public class ResearchClientsTableWindow extends JFrame {
    private JTable table;
    private ClientTableModel clientTableModel;
    private JButton quit;
    private JScrollPane scrollPane;

    public ResearchClientsTableWindow(ArrayList<ResearchClients> researchClients) {
        super("Research's results");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        clientTableModel = new ClientTableModel(researchClients);
        table = new JTable(clientTableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        quit = new JButton("Quit");
        quit.addActionListener(new ButtonListener());
        add(quit, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            dispose();
        }
    }

    private class ClientTableModel extends AbstractTableModel {
        private ArrayList<String> columnName;
        private ArrayList<ResearchClients> researchClients;

        public ClientTableModel(ArrayList<ResearchClients> researchClients) {
            columnName = new ArrayList<>();
            columnName.add("First name");
            columnName.add("Last name");
            columnName.add("Departure time");
            this.researchClients = researchClients;
        }

        public int getRowCount() {
            return researchClients.size();
        }

        public int getColumnCount() {
            return columnName.size();
        }

        public String getColumnName(int col) {
            return columnName.get(col);
        }

        public Object getValueAt(int row, int col) {
            ResearchClients researchClient = researchClients.get(row);
            switch (col) {
                case 0:
                    return researchClient.getFirst_name();
                case 1:
                    return researchClient.getLast_name();
                default:
                    return researchClient.getDeparture_time();
            }
        }

        public Class getColumnClass(int col) {
            switch (col) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                default:
                    return Date.class;
            }
        }
    }
}
