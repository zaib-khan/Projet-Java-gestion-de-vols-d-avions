package userInterface;

import controller.Control;
import exception.ClosingException;
import model.FlightLeavingSoonThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private Container container;
    private JMenuBar menuBar;
    private JMenu application, meal, flight, reservation, list, research, businesstask;
    private JMenuItem exit, addMeal, addFlight, modifyFlight, deleteFlight, deleteMeal, modifyMeal,
            addReservation, deleteReservation, modifyReservation, listMeal, listReservation, listFlight, reshearchEmploye,
            reshearchNotFullFlight, researchClientsByMeals, totalDelay, averageFlightPerMonth, eightyPercentFullFlight;
    private ImageIcon icon;
    private JLabel image;
    private FlightLeavingSoonThread thread;


    public MainWindow() {

        super("AirLine and co");
        setBounds(500, 100, 650, 600);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    new Control().closeConnection();
                    System.exit(0);
                } catch (ClosingException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        setVisible(true);
        setResizable(false);

        icon = new ImageIcon("image\\avion_quienes-somos_es-mx.png");
        image = new JLabel(icon);

        container.add(image);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        application = new JMenu("Application");
        meal = new JMenu("Meals");
        flight = new JMenu("Flights");
        reservation = new JMenu("Reservations");
        list = new JMenu("Lists");
        research = new JMenu("Researches");
        businesstask = new JMenu("Business tasks");

        menuBar.add(application);
        menuBar.add(list);
        menuBar.add(meal);
        menuBar.add(flight);
        menuBar.add(reservation);
        menuBar.add(research);
        menuBar.add(businesstask);

        exit = new JMenuItem("Exit");
        addMeal = new JMenuItem("Add a Meal");
        deleteMeal = new JMenuItem("Remove a Meal");
        modifyMeal = new JMenuItem("Modify a Meal");
        addFlight = new JMenuItem("Add a flight");
        modifyFlight = new JMenuItem("Modify a flight");
        deleteFlight = new JMenuItem("Delete a flight");
        addReservation = new JMenuItem("Add a reservation");
        deleteReservation = new JMenuItem("Delete a reservation");
        modifyReservation = new JMenuItem("Modify a reservation");
        listMeal = new JMenuItem("Meal list");
        listReservation = new JMenuItem("Reservation list");
        listFlight = new JMenuItem("Flights list");
        reshearchEmploye = new JMenuItem("Research staff in a country between 2 dates");
        reshearchNotFullFlight = new JMenuItem("Research not full flight with country");
        researchClientsByMeals = new JMenuItem("Research which clients ate a specific meal");
        totalDelay = new JMenuItem("The total delay accumulated by flights");
        averageFlightPerMonth = new JMenuItem("Average number of flight per month for a special country during the past year");
        eightyPercentFullFlight = new JMenuItem("Percentage of flights filled to more than 80 percent");

        exit.addActionListener(new EvenementQuitter());
        addMeal.addActionListener(new EvenementAjouterClient());
        deleteMeal.addActionListener(new EvenementSuppressionMeal());
        modifyMeal.addActionListener(new EvenementModificationMeal());
        addFlight.addActionListener(new EventAddFlight());
        modifyFlight.addActionListener(new EvenementModifyFlight());
        deleteFlight.addActionListener(new EvenementDeleteFlight());
        addReservation.addActionListener(new EventAddReservation());
        deleteReservation.addActionListener(new EventDeleteReservation());
        modifyReservation.addActionListener(new EventModifyReservation());
        listMeal.addActionListener(new EvenementListMeal());
        listReservation.addActionListener(new EvenementListReservation());
        listFlight.addActionListener(new FlightListListener());
        reshearchEmploye.addActionListener(new EvenementResearch1());
        reshearchNotFullFlight.addActionListener(new EvenementreshearchNotFullFlight());
        researchClientsByMeals.addActionListener(new ResearchClientsByMealListener());
        totalDelay.addActionListener(new DelayEvent());
        averageFlightPerMonth.addActionListener(new AverageFlightPerMonthEvent());
        eightyPercentFullFlight.addActionListener(new EightyPercentFullFlightEvent());

        application.add(exit);
        meal.add(addMeal);
        meal.add(deleteMeal);
        meal.add(modifyMeal);
        flight.add(addFlight);
        flight.add(modifyFlight);
        flight.add(deleteFlight);
        reservation.add(addReservation);
        reservation.add(deleteReservation);
        reservation.add(modifyReservation);
        list.add(listMeal);
        list.add(listReservation);
        list.add(listFlight);
        research.add(reshearchEmploye);
        research.add(reshearchNotFullFlight);
        research.add(researchClientsByMeals);
        businesstask.add(totalDelay);
        businesstask.add(averageFlightPerMonth);
        businesstask.add(eightyPercentFullFlight);

        //Démarrer le Thread
        thread = new FlightLeavingSoonThread();
        thread.start();

        container.revalidate(); // c'est cette fonction qui corrige le beug

    }

    private class EvenementQuitter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }

    private class EvenementAjouterClient implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new AddMealWindow();
        }
    }

    private class EvenementSuppressionMeal implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new DeleteMealWindow();
        }
    }

    private class EvenementModificationMeal implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new ModifySelectMealWindow();
        }
    }

    private class EventAddFlight implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new AddFlightWindow();
        }
    }

    private class EventAddReservation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new AddReservationWindow();
        }
    }

    private class EventDeleteReservation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new DeleteReservationWindow();
        }
    }

    private class EventModifyReservation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new ModifyReservationWindow();
        }
    }

    private class EvenementListMeal implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new MealListWindow();
        }
    }

    private class EvenementListReservation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new ReservationListWindow();
        }
    }

    private class EvenementResearch1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new ResearchEmployeesWindow();
        }
    }

    private class EvenementreshearchNotFullFlight implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new ResearchNotFullFlightWindow();
        }
    }

    private class EvenementModifyFlight implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new SelectFlightWindow(1);
        }
    }

    private class EvenementDeleteFlight implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new SelectFlightWindow(0);
        }
    }

    private class ResearchClientsByMealListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new ResearchClientWindow();
        }
    }

    private class DelayEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new TaskDelay();
        }
    }

    private class FlightListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new FlightListWindow();
        }
    }

    private class AverageFlightPerMonthEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new TaskAverageFlighCount();
        }
    }
    private class EightyPercentFullFlightEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new TaskEightyPercentFull();
        }
    }

}
