package view;

import javax.swing.*;

public class EmployerPanel extends JPanel {

    public JComboBox typeofempComboBox;
    public JComboBox typeofregComboBox;
    public JTextField emptinJtextfield;
    public JTextField empameJtextfield;
    public JTextField empaddrJtextfield;
    public JSpinner day8JSpinner;
    public JSpinner month8JSpinner;
    public JSpinner year8JSpinner;
    public JTextField contnumJtextfield;
    public PlaceholderTextField municicodeJtextfield;
    public JSpinner day9JSpinner;
    public JSpinner month9JSpinner;
    public JSpinner year9JSpinner;
    public JComboBox succorconComboBox;
    public JTextField empame1Jtextfield;
    public JTextField emptin1Jtextfield;
    public JTextField zipCodeJtextfield;

    public EmployerPanel() {
        
        setLayout(null);

        int rh = 28;
        int rg = 40;
        int y0 = 15;

        // ===== LEFT COLUMN: Primary Employer =====
        int c1lbl = 25;
        int c1fld = 220;
        int c1w = 220;

        JLabel empltitlelabel = new JLabel("Part IV - Primary Employer Information");
        empltitlelabel.setBounds(c1lbl, y0, 400, rh);
        add(empltitlelabel);

        int row = 1;
        JLabel typeofemplabel = new JLabel("Type of Employment");
        typeofemplabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(typeofemplabel);
        String[] typeofemp = {"Primary"};
        typeofempComboBox = new JComboBox<>(typeofemp);
        typeofempComboBox.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(typeofempComboBox);

        row = 2;
        JLabel typeofreglabel = new JLabel("Type of Registering Office");
        typeofreglabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(typeofreglabel);
        String[] typeofreg = {"Head Office", "Branch Office"};
        typeofregComboBox = new JComboBox<>(typeofreg);
        typeofregComboBox.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(typeofregComboBox);

        row = 3;
        JLabel emptinlabel = new JLabel("Employer TIN");
        emptinlabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(emptinlabel);
        emptinJtextfield = new PlaceholderTextField("e.g. 000-000-000-000");
        emptinJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(emptinJtextfield);

        row = 4;
        JLabel empamelabel = new JLabel("Employer's Name");
        empamelabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(empamelabel);
        empameJtextfield = new JTextField();
        empameJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(empameJtextfield);

        row = 5;
        JLabel empaddrlabel = new JLabel("Employer's Address");
        empaddrlabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(empaddrlabel);
        empaddrJtextfield = new JTextField();
        empaddrJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(empaddrJtextfield);

        row = 6;
        JLabel relstartlabel = new JLabel("Relationship Start Date");
        relstartlabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(relstartlabel);

        JLabel day8 = new JLabel("Day");
        day8.setBounds(c1fld, y0 + rg*row, 28, rh);
        day8JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day8JSpinner.setBounds(c1fld+28, y0 + rg*row, 45, rh);
        add(day8); add(day8JSpinner);

        JLabel month8 = new JLabel("Month");
        month8.setBounds(c1fld+80, y0 + rg*row, 40, rh);
        String[] months8 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month8JSpinner = new JSpinner(new SpinnerListModel(months8));
        month8JSpinner.setBounds(c1fld+120, y0 + rg*row, 90, rh);
        add(month8); add(month8JSpinner);

        JLabel year8 = new JLabel("Year");
        year8.setBounds(c1fld+215, y0 + rg*row, 30, rh);
        year8JSpinner = new JSpinner(new SpinnerNumberModel(2026,1900,2026,1));
        year8JSpinner.setBounds(c1fld+245, y0 + rg*row, 60, rh);
        add(year8); add(year8JSpinner);

        row = 7;
        JLabel contnumlabel = new JLabel("Contact Number");
        contnumlabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(contnumlabel);
        contnumJtextfield = new PlaceholderTextField("e.g. 09123456789");
        contnumJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(contnumJtextfield);

        row = 8;
        JLabel municicodelabel = new JLabel("Municipality");
        municicodelabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(municicodelabel);
        municicodeJtextfield = new PlaceholderTextField("e.g. Manila");
        municicodeJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(municicodeJtextfield);

        // Add focus listener for auto-filling mun code
        municicodeJtextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String input = municicodeJtextfield.getText().trim();
                if (!input.isEmpty()) {
                    dao.Sql sql = new dao.Sql();
                    for (model.Location loc : sql.getLocations()) {
                        if (loc.getMun().equalsIgnoreCase(input) || loc.getMun_code().equalsIgnoreCase(input)) {
                            municicodeJtextfield.setText(loc.getMun_code());
                            break;
                        }
                    }
                }
            }
        });

        row = 9;
        JLabel zipCodelabel = new JLabel("Zip Code");
        zipCodelabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(zipCodelabel);
        zipCodeJtextfield = new JTextField();
        zipCodeJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(zipCodeJtextfield);

        row = 10;
        JLabel effedatelabel = new JLabel("Effectivity Date of Exemption");
        effedatelabel.setBounds(c1lbl, y0 + rg*row, 190, rh);
        add(effedatelabel);

        JLabel day9 = new JLabel("Day");
        day9.setBounds(c1fld, y0 + rg*row, 28, rh);
        day9JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day9JSpinner.setBounds(c1fld+28, y0 + rg*row, 45, rh);
        add(day9); add(day9JSpinner);

        JLabel month9 = new JLabel("Month");
        month9.setBounds(c1fld+80, y0 + rg*row, 40, rh);
        String[] months9 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month9JSpinner = new JSpinner(new SpinnerListModel(months9));
        month9JSpinner.setBounds(c1fld+120, y0 + rg*row, 90, rh);
        add(month9); add(month9JSpinner);

        JLabel year9 = new JLabel("Year");
        year9.setBounds(c1fld+215, y0 + rg*row, 30, rh);
        year9JSpinner = new JSpinner(new SpinnerNumberModel(2026,1900,2026,1));
        year9JSpinner.setBounds(c1fld+245, y0 + rg*row, 60, rh);
        add(year9); add(year9JSpinner);


        // ===== RIGHT COLUMN: Successive/Concurrent Employer =====
        int c2lbl = 530;
        int c2fld = 730;
        int c2w = 220;

        JLabel succorconlabel = new JLabel("Successive or Concurrent Employer Info");
        succorconlabel.setBounds(c2lbl, y0, 400, rh);
        add(succorconlabel);

        row = 1;
        JLabel succorcon1label = new JLabel("Type of Employment");
        succorcon1label.setBounds(c2lbl, y0 + rg*row, 190, rh);
        add(succorcon1label);
        String[] succorcon = {"None", "Successive", "Concurrent"};
        succorconComboBox = new JComboBox<>(succorcon);
        succorconComboBox.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(succorconComboBox);

        row = 2;
        JLabel empame1label = new JLabel("Employer's Name");
        empame1label.setBounds(c2lbl, y0 + rg*row, 190, rh);
        add(empame1label);
        empame1Jtextfield = new JTextField();
        empame1Jtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(empame1Jtextfield);

        row = 3;
        JLabel emptin1label = new JLabel("Employer TIN");
        emptin1label.setBounds(c2lbl, y0 + rg*row, 190, rh);
        add(emptin1label);
        emptin1Jtextfield = new PlaceholderTextField("000-000-000-000");
        emptin1Jtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(emptin1Jtextfield);

        // Logic to toggle secondary employer fields
        succorconComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    boolean enabled = !"None".equals(e.getItem());
                    empame1Jtextfield.setEnabled(enabled);
                    emptin1Jtextfield.setEnabled(enabled);
                    if (!enabled) {
                        empame1Jtextfield.setText("");
                        emptin1Jtextfield.setText("");
                    }
                }
            }
        });
        // Initial state
        empame1Jtextfield.setEnabled(false);
        emptin1Jtextfield.setEnabled(false);
    }
}
