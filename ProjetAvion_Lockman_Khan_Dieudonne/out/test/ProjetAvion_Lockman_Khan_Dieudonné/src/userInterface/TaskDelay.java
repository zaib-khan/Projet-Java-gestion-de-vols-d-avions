package userInterface;

import controller.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TaskDelay extends JFrame {

    private Container container;
    private JLabel delay, title;

    public TaskDelay(){
        super("Total delay by flights");
        setBounds(600,150,400,100);
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
            int delayMinut = new Control().getTotalDelay();
            Double delayHour = new Control().getTotalDelayHour(delayMinut);
            delay = new JLabel(""+delayMinut+" minutes"+"/ "+delayHour+" hour");

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        title = new JLabel("  The total dealy acumulated by flights : ");
        container.add(title,BorderLayout.WEST);
        container.add(delay,BorderLayout.CENTER);

    }


}
