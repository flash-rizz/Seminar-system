package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPanel extends JPanel {

    // UI Components
    private JLabel lblRevenue, lblOccupancy;
    private JTable vehiclesTable, finesTable;
    private DefaultTableModel vehiclesModel, finesModel;
    private JComboBox<String> cmbFineStrategy;

    public AdminPanel() {
        setLayout(new BorderLayout(10, 10));

        // =========================================
        // 1. TOP SECTION: Statistics & Controls
        // =========================================
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Dashboard Overview"));

        // Statistic Labels (Requirement: View Revenue & Occupancy)
        lblRevenue = new JLabel("Total Revenue: RM 0.00", SwingConstants.CENTER);
        lblRevenue.setFont(new Font("Arial", Font.BOLD, 16));
        lblRevenue.setForeground(new Color(0, 100, 0)); // Dark Green

        lblOccupancy = new JLabel("Occupancy Rate: 0%", SwingConstants.CENTER);
        lblOccupancy.setFont(new Font("Arial", Font.BOLD, 16));

        // Fine Strategy Selector (Requirement: Manage Fine Schemes)
        JPanel strategyPanel = new JPanel(new FlowLayout());
        strategyPanel.add(new JLabel("Active Fine Scheme:"));
        String[] strategies = {"Scheme A: Fixed Flat Rate", "Scheme B: Progressive Rate", "Scheme C: Hourly Rate"};
        cmbFineStrategy = new JComboBox<>(strategies);
        strategyPanel.add(cmbFineStrategy);

        topPanel.add(lblRevenue);
        topPanel.add(lblOccupancy);
        topPanel.add(strategyPanel);

        add(topPanel, BorderLayout.NORTH);

        // =========================================
        // 2. CENTER SECTION: Reports (Tabs)
        // =========================================
        JTabbedPane tabbedPane = new JTabbedPane();

        // TAB A: Current Parked Vehicles (Requirement: View Vehicles)
        // Columns: License Plate, Vehicle Type, Assigned Spot, Entry Time
        String[] vehicleCols = {"License Plate", "Type", "Spot ID", "Entry Time"};
        vehiclesModel = new DefaultTableModel(vehicleCols, 0);
        vehiclesTable = new JTable(vehiclesModel);
        JScrollPane vehicleScroll = new JScrollPane(vehiclesTable);
        tabbedPane.addTab("Current Vehicles", vehicleScroll);

        // TAB B: Unpaid Fines (Requirement: View Unpaid Fines)
        // Columns: License Plate, Amount Due, Reason
        String[] fineCols = {"License Plate", "Fine Amount (RM)", "Reason"};
        finesModel = new DefaultTableModel(fineCols, 0);
        finesTable = new JTable(finesModel);
        JScrollPane fineScroll = new JScrollPane(finesTable);
        tabbedPane.addTab("Outstanding Fines", fineScroll);

        add(tabbedPane, BorderLayout.CENTER);

        // =========================================
        // 3. BOTTOM SECTION: Setup Actions
        // =========================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Button to Initialize Floors (Requirement: Configure Structure)
        JButton btnSetup = new JButton("System Setup (Floors/Spots)");
        JButton btnRefresh = new JButton("Refresh Data");
        
        btnSetup.addActionListener(e -> showSetupDialog());
        btnRefresh.addActionListener(e -> refreshDashboard());

        bottomPanel.add(btnSetup);
        bottomPanel.add(btnRefresh);

        add(bottomPanel, BorderLayout.SOUTH);
        
        // Load some dummy data to test the look
        loadDummyData();
    }

    // =========================================
    // HELPER METHODS (To be connected to Logic later)
    // =========================================
    
    private void showSetupDialog() {
        // This answers the requirement: "Admin defines floors"
        String input = JOptionPane.showInputDialog(this, "Enter number of floors to initialize:");
        if (input != null && !input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Parking Lot Initialized with " + input + " floors.");
            // Later, you will call: ParkingLotManager.getInstance().initFloors(Integer.parseInt(input));
        }
    }

    private void refreshDashboard() {
        // Later, this will fetch real data from Member 1's Singleton
        JOptionPane.showMessageDialog(this, "Data Refreshed from Backend!");
    }

    private void loadDummyData() {
        // Just for you to see what it looks like
        vehiclesModel.addRow(new Object[]{"ABC 1234", "Car", "F1-R1-S1", "10:00 AM"});
        vehiclesModel.addRow(new Object[]{"WXYZ 88", "Bike", "F1-R1-S5", "11:30 AM"});
        
        finesModel.addRow(new Object[]{"JJJ 9999", "50.00", "Overstay > 24h"});
        
        lblRevenue.setText("Total Revenue: RM 150.00");
        lblOccupancy.setText("Occupancy Rate: 45%");
    }
}