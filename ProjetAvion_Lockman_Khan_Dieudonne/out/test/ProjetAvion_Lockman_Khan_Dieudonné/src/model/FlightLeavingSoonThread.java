package model;

import controller.Control;
import userInterface.FlightLeavingSoonWindow;

import javax.swing.*;
import java.util.ArrayList;

public class FlightLeavingSoonThread extends Thread {
    private ArrayList<String> flightSoonToLeave;

    //Récupère tout les vols qui décolleront dans les 10 prochaines minutes

    public void run() {
        try {
            while (true) {
                flightSoonToLeave = new Control().getFlightLeavingSoon();
                if (!flightSoonToLeave.isEmpty()) {
                    new FlightLeavingSoonWindow(flightSoonToLeave);
                }
                sleep(Tools.TEN_MINS_IN_MILLISECONDS);
            }
        } catch (InterruptedException exception) {
            JOptionPane.showMessageDialog(null, "Thread encountered an error and was interrupted.");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }
}
