package view;

import dao.Sql;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReportPanel extends JPanel {

    //Make variables for total applications and pending applications
    private JLabel totalapp;
    private JLabel pendingapp;

    public ReportPanel () {

        //Set the Main Layout
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        //Make title JPanel with FlowLayout to center the Report Summary Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        //Make title for Report Summary
        JLabel reporttitle = new JLabel("REPORT SUMMARY");
        reporttitle.setFont(new Font("Arial", Font.BOLD, 20));
        reporttitle.setForeground(new Color(30, 65, 150));
        
        //Add report title JLabel to titlePanel
        titlePanel.add(reporttitle);

        // Refresh button
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        refreshPanel.setBackground(Color.WHITE);
        JButton refreshButton = new JButton("Refresh Report");
        refreshButton.setBackground(new Color(30, 65, 150));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshPanel.add(refreshButton);

        // Top wrapper
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);
        topWrapper.add(titlePanel, BorderLayout.NORTH);
        topWrapper.add(refreshPanel, BorderLayout.SOUTH);

        //Add title panel to ReportPanel
        add(topWrapper, BorderLayout.NORTH);

        //Make center Panel for left and right panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2,40,5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100,200,100,200));
        centerPanel.setBackground(Color.WHITE);

        //Make Left Panel for Total Applications
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2,1));
        leftPanel.setBackground(new Color(30,65,150));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        

        //Make JLabel for Total Applications
        JLabel totalappLabel = new JLabel("Total Applications", SwingConstants.CENTER);
        totalappLabel.setFont(new Font("Arial", Font.BOLD,30));
        totalappLabel.setForeground(Color.WHITE);
        
        //Make JLabel for the total application count using the totalapp JLabel variable
        totalapp = new JLabel("0", SwingConstants.CENTER);
        totalapp.setFont(new Font("Arial", Font.BOLD,40));
        totalapp.setForeground(Color.WHITE);

        leftPanel.add(totalappLabel);
        leftPanel.add(totalapp);

        //Make Right Panel for Pending Applications
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2,1));
        rightPanel.setBackground(new Color(30,65,150));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //Make JLabel for Pending Applications
        JLabel pendappLabel = new JLabel("Pending Applications", SwingConstants.CENTER);
        pendappLabel.setFont(new Font("Arial", Font.BOLD,30));
        pendappLabel.setForeground(Color.WHITE);
        
        //Make JLabel for the actual pending application count
        pendingapp = new JLabel("0", SwingConstants.CENTER);
        pendingapp.setFont(new Font("Arial", Font.BOLD,40));
        pendingapp.setForeground(Color.WHITE);

        rightPanel.add(pendappLabel);
        rightPanel.add(pendingapp);

        //Add the Left and Right Panels to Center Panel
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        //Add centerPanel to ReportPanel
        add(centerPanel, BorderLayout.CENTER);

        // Refresh button action to load live data
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReportData();
            }
        });

        // Load data on initialization
        loadReportData();
    }

    // Load report counts from the database
    private void loadReportData() {
        Sql db = new Sql();
        totalapp.setText(String.valueOf(db.getTotalApplicationCount()));
        pendingapp.setText(String.valueOf(db.getPendingApplicationCount()));
    }
}