package userInterface;

import controller.Control;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReservationListWindow extends JFrame {
    private Container container;
    private AllReservationsModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton quit;


    public ReservationListWindow() {
        super("Reservation list");
        setBounds(200, 150, 1200, 500);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setResizable(false);
        setVisible(true);

        try {
            model = new AllReservationsModel(new Control().getAll_reservations());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        table = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        scrollPane = new JScrollPane(table);
        container.add(scrollPane, BorderLayout.CENTER);

        quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        container.add(quit, BorderLayout.SOUTH);

    }


}
