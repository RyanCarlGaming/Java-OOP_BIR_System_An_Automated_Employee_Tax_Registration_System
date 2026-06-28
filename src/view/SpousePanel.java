package view;

import javax.swing.*;

public class SpousePanel extends JPanel {

    public JComboBox spouseempstatComboBox;
    public JComboBox exemptclaimComboBox;
    public JTextField spousenameJtextfield;
    public JTextField spousetinJtextfield;
    public JTextField spouseempnameJtextfield;
    public JTextField spouseemptinJtextfield;


    public SpousePanel() {

        setLayout(null);

        int rh = 28;
        int rg = 45;
        int y0 = 15;

        JLabel spoutitlelabel = new JLabel("Part II - Spouse Information");
        spoutitlelabel.setBounds(25, y0, 400, rh);
        add(spoutitlelabel);

        int row = 1;
        JLabel spouseempstatlabel = new JLabel("Employment Status of Spouse");
        spouseempstatlabel.setBounds(25, y0 + rg*row, 220, rh);
        add(spouseempstatlabel);
        String[] spouseempstat = {"Unemployed", "Employed Locally", "Employed Abroad", "Engaged in Business/Practice of Profession"};
        spouseempstatComboBox = new JComboBox<>(spouseempstat);
        spouseempstatComboBox.setBounds(260, y0 + rg*row, 350, rh);
        add(spouseempstatComboBox);

        row = 2;
        JLabel exemptclaimlabel = new JLabel("Exemption Claimant");
        exemptclaimlabel.setBounds(25, y0 + rg*row, 220, rh);
        add(exemptclaimlabel);
        String[] exemptclaim = {"Husband Claims", "Wife Claims"};
        exemptclaimComboBox = new JComboBox<>(exemptclaim);
        exemptclaimComboBox.setBounds(260, y0 + rg*row, 350, rh);
        add(exemptclaimComboBox);

        row = 3;
        JLabel spousenamelabel = new JLabel("Spouse Full Name");
        spousenamelabel.setBounds(25, y0 + rg*row, 220, rh);
        add(spousenamelabel);
        spousenameJtextfield = new JTextField();
        spousenameJtextfield.setBounds(260, y0 + rg*row, 350, rh);
        add(spousenameJtextfield);

        row = 4;
        JLabel spousetinlabel = new JLabel("Spouse TIN");
        spousetinlabel.setBounds(25, y0 + rg*row, 220, rh);
        add(spousetinlabel);
        spousetinJtextfield = new JTextField();
        spousetinJtextfield.setBounds(260, y0 + rg*row, 250, rh);
        add(spousetinJtextfield);

        row = 5;
        JLabel spouseempnamelabel = new JLabel("Employer's Name of Spouse");
        spouseempnamelabel.setBounds(25, y0 + rg*row, 220, rh);
        add(spouseempnamelabel);
        spouseempnameJtextfield = new JTextField();
        spouseempnameJtextfield.setBounds(260, y0 + rg*row, 350, rh);
        add(spouseempnameJtextfield);

        row = 6;
        JLabel spouseemptinlabel = new JLabel("Employer's TIN of Spouse");
        spouseemptinlabel.setBounds(25, y0 + rg*row, 220, rh);
        add(spouseemptinlabel);
        spouseemptinJtextfield = new JTextField();
        spouseemptinJtextfield.setBounds(260, y0 + rg*row, 250, rh);
        add(spouseemptinJtextfield);
    }
}
