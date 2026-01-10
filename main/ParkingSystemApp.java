package main;

import view.AdminPanel;
import javax.swing.*;

public class ParkingSystemApp extends JFrame {

    public ParkingSystemApp() {
        setTitle("Parking Lot Management System - Group [YourGroup]");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Add your Admin Panel here
        add(new AdminPanel());

        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new ParkingSystemApp());
    }
}