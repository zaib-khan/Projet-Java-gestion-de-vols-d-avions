package userInterface;

import controller.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TaskEightyPercentFull extends JFrame {

    private Container container;
    private JLabel delay;

    public TaskEightyPercentFull(){
        super("Percentage of flights that are at least 80% full");
        setBounds(600,150,450,70);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        } );
        setResizable(false);
        setVisible(true);

        try {
            double pourc = new Control().getEightyPercentFull();
            delay = new JLabel("  The percentage of flights filled to more than eighty percent is : "+String.format("%.02f",pourc)+"%");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        container.add(delay);

    }


}
