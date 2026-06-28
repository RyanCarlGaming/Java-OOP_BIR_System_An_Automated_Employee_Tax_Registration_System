package view;

import dao.Sql;
import model.Taxpayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SearchApplication extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public SearchApplication() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title panel at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel titleLabel = new JLabel("Search Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 65, 150));
        titlePanel.add(titleLabel);

        // Top search panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel searchLabel = new JLabel("Search by Name, TIN, or ID:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(30, 65, 150));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Top wrapper
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);
        topWrapper.add(titlePanel, BorderLayout.NORTH);
        topWrapper.add(topPanel, BorderLayout.SOUTH);

        // Results table
        String[] columns = {"ID", "Full Name", "TIN", "Email", "Mobile", "Civil Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setSelectionBackground(new Color(30, 65, 150));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));

        // Center text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Style header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(30, 65, 150));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(220);
        table.getColumnModel().getColumn(2).setPreferredWidth(140);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(130);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        // Double-click a row to view full details in a JOptionPane dialog
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        int id = (int) tableModel.getValueAt(row, 0);
                        Sql db = new Sql();
                        String details = db.getTaxpayerDetailsById(id);
                        if (details != null) {
                            JTextArea textArea = new JTextArea(details);
                            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
                            textArea.setEditable(false);
                            textArea.setCaretPosition(0);
                            JScrollPane sp = new JScrollPane(textArea);
                            sp.setPreferredSize(new Dimension(500, 500));
                            JOptionPane.showMessageDialog(SearchApplication.this, sp,
                                "Applicant Details - ID #" + id, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(searchField.getText().trim());
            }
        });

        // Also search on Enter key
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(searchField.getText().trim());
            }
        });

        // Status label at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottomPanel.setBackground(Color.WHITE);
        JLabel hintLabel = new JLabel("Tip: Double-click a row to view full record details.");
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        hintLabel.setForeground(Color.GRAY);
        bottomPanel.add(hintLabel);

        add(topWrapper, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Run search and populate table
    private void performSearch(String keyword) {
        tableModel.setRowCount(0);
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search keyword.",
                "Search", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Sql db = new Sql();
        List<Taxpayer> results = db.searchTaxpayers(keyword);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No record found for: " + keyword,
                "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Taxpayer t : results) {
                tableModel.addRow(new Object[]{
                    t.getApplicant_id(),
                    t.getTaxpayer_fullname(),
                    t.getTaxpayer_tin() != null ? t.getTaxpayer_tin() : "N/A",
                    t.getEmail(),
                    t.getMobile(),
                    t.getCivil_status()
                });
            }
            // If exactly one result, auto-show the full details dialog
            if (results.size() == 1) {
                int id = results.get(0).getApplicant_id();
                String details = db.getTaxpayerDetailsById(id);
                if (details != null) {
                    JTextArea textArea = new JTextArea(details);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
                    textArea.setEditable(false);
                    textArea.setCaretPosition(0);
                    JScrollPane sp = new JScrollPane(textArea);
                    sp.setPreferredSize(new Dimension(500, 500));
                    JOptionPane.showMessageDialog(this, sp,
                        "Applicant Details - ID #" + id, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
