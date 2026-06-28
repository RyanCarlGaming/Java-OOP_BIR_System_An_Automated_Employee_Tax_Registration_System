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

public class ViewApplication extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public ViewApplication() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title panel at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel titleLabel = new JLabel("All Registered Applications");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 65, 150));
        titlePanel.add(titleLabel);

        // Refresh button below the title
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        controlPanel.setBackground(Color.WHITE);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(30, 65, 150));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        controlPanel.add(refreshButton);

        // Top wrapper
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);
        topWrapper.add(titlePanel, BorderLayout.NORTH);
        topWrapper.add(controlPanel, BorderLayout.SOUTH);

        // Table setup
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

        // Center all cell text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Style the header
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

        // Double-click to view full details
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
                            JOptionPane.showMessageDialog(ViewApplication.this, sp,
                                "Applicant Details - ID #" + id, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        // Refresh button loads data
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        add(topWrapper, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Load data on initialization
        loadData();
    }

    // Load all taxpayer records into the table
    private void loadData() {
        tableModel.setRowCount(0);
        Sql db = new Sql();
        List<Taxpayer> taxpayers = db.getAllTaxpayers();
        for (Taxpayer t : taxpayers) {
            tableModel.addRow(new Object[]{
                t.getApplicant_id(),
                t.getTaxpayer_fullname(),
                t.getTaxpayer_tin() != null ? t.getTaxpayer_tin() : "N/A",
                t.getEmail(),
                t.getMobile(),
                t.getCivil_status()
            });
        }
    }
}
