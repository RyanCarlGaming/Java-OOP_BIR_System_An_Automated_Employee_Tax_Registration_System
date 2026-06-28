package view;

import dao.Sql;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteApplication extends JPanel {

    private int foundApplicantId = -1;

    public DeleteApplication() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title panel at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel titleLabel = new JLabel("Delete Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 65, 150));
        titlePanel.add(titleLabel);

        // Top panel with search bar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel enterLabel = new JLabel("Enter Applicant ID:");
        enterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(30, 65, 150));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        topPanel.add(enterLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Top wrapper
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);
        topWrapper.add(titlePanel, BorderLayout.NORTH);
        topWrapper.add(topPanel, BorderLayout.SOUTH);

        // Record display area
        JTextArea recordTextArea = new JTextArea();
        recordTextArea.setEditable(false);
        recordTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        recordTextArea.setMargin(new Insets(10, 10, 10, 10));
        recordTextArea.setText("Search for an Applicant by their ID to view their record before deleting.");
        JScrollPane recordScrollPane = new JScrollPane(recordTextArea);
        recordScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Bottom panel with delete button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBackground(Color.WHITE);
        JButton deleteButton = new JButton("Delete Record");
        deleteButton.setBackground(new Color(180, 30, 30));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setEnabled(false);
        bottomPanel.add(deleteButton);

        // Search action: find the record and display it
        ActionListener searchAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = searchField.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(DeleteApplication.this,
                        "Please enter an Applicant ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    int id = Integer.parseInt(input);
                    Sql db = new Sql();
                    String details = db.getTaxpayerDetailsById(id);
                    if (details != null) {
                        recordTextArea.setText(details);
                        recordTextArea.setCaretPosition(0);
                        foundApplicantId = id;
                        deleteButton.setEnabled(true);
                    } else {
                        recordTextArea.setText("No record found for Applicant ID: " + id);
                        foundApplicantId = -1;
                        deleteButton.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DeleteApplication.this,
                        "Applicant ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        searchButton.addActionListener(searchAction);
        searchField.addActionListener(searchAction);

        // Delete action: confirm before deleting
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (foundApplicantId < 0) return;

                int confirm = JOptionPane.showConfirmDialog(DeleteApplication.this,
                    "Are you sure you want to permanently delete Applicant ID " + foundApplicantId + "?\n" +
                    "This will also delete their spouse, dependents, and employer records.",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    Sql db = new Sql();
                    boolean success = db.deleteTaxpayerById(foundApplicantId);
                    if (success) {
                        JOptionPane.showMessageDialog(DeleteApplication.this,
                            "Applicant ID " + foundApplicantId + " has been successfully deleted.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        recordTextArea.setText("Record deleted successfully.");
                        foundApplicantId = -1;
                        deleteButton.setEnabled(false);
                        searchField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(DeleteApplication.this,
                            "Failed to delete the record. Check the console for errors.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        add(topWrapper, BorderLayout.NORTH);
        add(recordScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
