package userInterface;

import controller.Control;
import exception.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class MealListWindow extends JFrame {
    private Container container;
    private AllMealsModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton quit;


    public MealListWindow() {
        super("Meal list");

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
            model = new AllMealsModel(new Control().getAllMeal());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }

        table = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

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
