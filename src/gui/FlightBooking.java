package gui;

import logging.AssignmentLogger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import flights.*;

import exceptions.FlightClassException;

/**
 * The FlightBooking class represents a GUI application for booking flights.
 */
public class FlightBooking extends JFrame {

    private JLabel classLabel;
    private JLabel originLabel;
    private JLabel destinationLabel;
    private JTextField classTextField;
    private JTextField originTextField;
    private JTextField destinationTextField;
    private JButton bookButton;
    private JButton stopButton;
    private JTextArea confirmation;
    private Flights selectedFlight;
    private FlashingThread flashingThread;

    // Code for class colours
    private static final Color GOLD_COLOR = new Color(255, 215, 0);
    private static final Color SILVER_COLOR = new Color(192, 192, 192);
    private Clip confirmationSound;


    public static void main(String[] args) {
        AssignmentLogger.logStaticMethodEntry();
        JFrame jFrame = new FlightBooking();
        jFrame.setVisible(true);
        jFrame.setSize(850, 650);
        jFrame.setLocation(300, 150);
        jFrame.setTitle("Flight Booking System");
        AssignmentLogger.logMain();
        AssignmentLogger.logStaticMethodExit();
    }

    /**
     * Constructs the FlightBooking frame.
     */
    public FlightBooking() {
        AssignmentLogger.logConstructor(this);

        JPanel panel = new JPanel(new BorderLayout());
        Container contentPane = getContentPane();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        classLabel = new JLabel("Flight Class");
        originLabel = new JLabel("Origin");
        destinationLabel = new JLabel("Destination");

        classTextField = new JTextField();
        originTextField = new JTextField();
        destinationTextField = new JTextField();

        inputPanel.add(classLabel);
        inputPanel.add(classTextField);
        inputPanel.add(originLabel);
        inputPanel.add(originTextField);
        inputPanel.add(destinationLabel);
        inputPanel.add(destinationTextField);

        bookButton = new JButton("Book");
        stopButton = new JButton("Stop");

        buttonPanel.add(bookButton);
        buttonPanel.add(stopButton);

        confirmation = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(confirmation);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.SOUTH);

        contentPane.add(panel);

        /**
         * ActionListener implementation for the bookButton.
         */
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignmentLogger.logMethodEntry(this);
                String origin = originTextField.getText();
                String destination = destinationTextField.getText();
                String flightClass = classTextField.getText();

                // Check if any of the fields are empty
                if (origin.isEmpty() || destination.isEmpty() || flightClass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if any field is empty
                }

                try {
                    switch (flightClass.toLowerCase()) {
                        case "first":
                            selectedFlight = new FirstClass(origin, destination);
                            startFlashingThread(GOLD_COLOR);
                            loadConfirmationSound("first");
                            break;
                        case "business":
                            selectedFlight = new Business(origin, destination);
                            startFlashingThread(SILVER_COLOR);
                            loadConfirmationSound("business");
                            break;
                        case "coach":
                            selectedFlight = new Coach(origin, destination);
                            startFlashingThread(Color.BLUE);
                            loadConfirmationSound("coach");
                            break;
                        default:
                            throw new FlightClassException("Please enter correct flight class; First, Business, Coach");
                    }
                    confirmation.append("Flight class: " + selectedFlight.getFlightsClass() + "\n");
                    confirmation.append("Origin: " + selectedFlight.getOrigin() + "\n");
                    confirmation.append("Destination: " + selectedFlight.getDestination() + "\n");

                    playConfirmationSound();

                } catch (FlightClassException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } finally {
                    AssignmentLogger.logMethodExit(this);
                }
            }
        });

        /**
         * ActionListener implementation for the stopButton.
         */
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignmentLogger.logMethodEntry(this);
                stopConfirmationSound();
                stopFlashingThread();
                confirmation.setText("");
                AssignmentLogger.logMethodExit(this);
            }
        });
    }

    /**
     * Starts the flashing thread with the specified color.
     *
     * @param color The color to use for flashing.
     */
    private void startFlashingThread(Color color) {
        AssignmentLogger.logMethodEntry(this);
        if (flashingThread == null) {
            flashingThread = new FlashingThread(confirmation, color);
            flashingThread.start();
        }
        AssignmentLogger.logMethodExit(this);
    }

    /**
     * Stops the flashing thread.
     */
    private void stopFlashingThread() {
        AssignmentLogger.logMethodEntry(this);
        if (flashingThread != null) {
            flashingThread.stopThread();
            flashingThread = null;
        }
        AssignmentLogger.logMethodExit(this);
    }

    /**
     * Plays the confirmation sound if available.
     */
    private void playConfirmationSound() {
        AssignmentLogger.logMethodEntry(this);
        if (confirmationSound != null) {
            confirmationSound.start();
        }
        AssignmentLogger.logMethodExit(this);
    }

    /**
     * Stops the confirmation sound if it is currently playing.
     */
    private void stopConfirmationSound() {
        AssignmentLogger.logMethodEntry(this);
        if (confirmationSound != null && confirmationSound.isRunning()) {
            confirmationSound.stop();
        }
        AssignmentLogger.logMethodExit(this);
    }

    /**
     * Loads the confirmation sound based on the specified flight class.
     *
     * @param flightClass The class of the flight.
     */
    private void loadConfirmationSound(String flightClass) {
        try {
            String soundFile;
            switch (flightClass.toLowerCase()) {
                case "first":
                    soundFile = "/sounds/FirstClass.wav";
                    break;
                case "business":
                    soundFile = "/sounds/BusinessClass.wav";
                    break;
                case "coach":
                    soundFile = "/sounds/CoachClass.wav";
                    break;
                default:
                    soundFile = null;
                    break;
            }
            if (soundFile != null) {
                AssignmentLogger.logMethodEntry(this);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundFile));
                confirmationSound = AudioSystem.getClip();
                confirmationSound.open(audioInputStream);
                AssignmentLogger.logMethodExit(this);
            } else {
                System.out.println("Invalid flight class: " + flightClass);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
