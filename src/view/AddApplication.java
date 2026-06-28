package view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AddApplication extends JPanel{

    public AddApplication() {

        setLayout(new BorderLayout());

        //Intialize the tabbed Pane which holds the different tabs or sections
        JTabbedPane tabbedpane = new JTabbedPane();
        tabbedpane.setBounds(0,0,1920,1080);
       

        //Make the tabs and add the panels of each tab
        TaxpayerPanel taxpayerPanel = new TaxpayerPanel();
        SpousePanel spousePanel = new SpousePanel();
        DependentPanel dependentPanel = new DependentPanel();
        EmployerPanel employerPanel = new EmployerPanel();

        tabbedpane.add("Taxpayer", taxpayerPanel);
        tabbedpane.add("Spouse", spousePanel);
        tabbedpane.add("Dependent", dependentPanel);
        tabbedpane.add("Employer", employerPanel);

        //Make Bottom Panel for the submit button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //Make Submit Button on the bottompanel
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(30,65,150));
        submitButton.setForeground(Color.WHITE);
        submitButton.setEnabled(false); // Grayed out until required fields are filled
        bottomPanel.add(submitButton);
        // Validation logic
        Runnable onUpdate = new Runnable() {
            @Override
            public void run() {
                checkSubmit(submitButton, taxpayerPanel, spousePanel, employerPanel);
            }
        };

        addValidationListeners(taxpayerPanel, onUpdate);
        addValidationListeners(employerPanel, onUpdate);
        addValidationListeners(spousePanel, onUpdate);

        // Initial state for Spouse Tab based on Civil Status
        tabbedpane.setEnabledAt(1, "Married".equals(taxpayerPanel.civilstatusComboBox.getSelectedItem()));

        // Item Listener to enable/disable Spouse Tab and trigger validation
        taxpayerPanel.civilstatusComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    boolean isMarried = "Married".equals(e.getItem());
                    tabbedpane.setEnabledAt(1, isMarried);
                    onUpdate.run();
                }
            }
        });

        //Create a Submit button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Basic Validation (Error Handling)
                if (taxpayerPanel.taxpayername.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AddApplication.this, "Taxpayer Full Name is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    dao.Sql sql = new dao.Sql();

                    // 1. Create and Save Taxpayer
                    model.Taxpayer taxpayer = new model.Taxpayer();
                    taxpayer.setTaxpayer_type(taxpayerPanel.taxpayerComboBox.getSelectedItem().toString());
                    taxpayer.setBir_reg_date(taxpayerPanel.yearJSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.monthJSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.dayJSpinner.getValue()));
                    taxpayer.setTaxpayer_tin(taxpayerPanel.taxtin.getText());
                    taxpayer.setPcn(taxpayerPanel.rdocode.getText());
                    taxpayer.setTaxpayer_fullname(taxpayerPanel.taxpayername.getText());
                    taxpayer.setCivil_status(taxpayerPanel.civilstatusComboBox.getSelectedItem().toString());
                    taxpayer.setGender(taxpayerPanel.genderComboBox.getSelectedItem().toString());
                    taxpayer.setDate_of_birth(taxpayerPanel.year1JSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.month1JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.day1JSpinner.getValue()));
                    taxpayer.setPlace_of_birth(taxpayerPanel.placeobJtextfield.getText());
                    taxpayer.setMother_fullname(taxpayerPanel.mothernameJtextfield.getText());
                    taxpayer.setFather_fullname(taxpayerPanel.fathernameJtextfield.getText());
                    taxpayer.setCitizenship(taxpayerPanel.citizenshipJtextfield.getText());
                    taxpayer.setOther_citizenship(taxpayerPanel.othercitizenJtextfield.getText());
                    taxpayer.setId_type(taxpayerPanel.idtypeComboBox.getSelectedItem().toString());
                    taxpayer.setId_number(taxpayerPanel.idnumJtextfield.getText());
                    taxpayer.setId_issuer(taxpayerPanel.idissuerJtextfield.getText());
                    taxpayer.setId_place(taxpayerPanel.idplaceJtextfield.getText());
                    taxpayer.setId_effectivity(taxpayerPanel.year2JSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.month2JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.day2JSpinner.getValue()));
                    taxpayer.setId_expiry(taxpayerPanel.year3JSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.month3JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.day3JSpinner.getValue()));
                    taxpayer.setLandline(taxpayerPanel.landlineJtextfield.getText());
                    taxpayer.setMobile(taxpayerPanel.mobilenumJtextfield.getText());
                    taxpayer.setFax(taxpayerPanel.faxnumJtextfield.getText());
                    taxpayer.setEmail(taxpayerPanel.emailaddJtextfield.getText());
                    taxpayer.setFull_address(taxpayerPanel.localaddressJtextfield.getText());
                    taxpayer.setForeign_address(taxpayerPanel.foreignaddressJtextfield.getText());
                    taxpayer.setMun_code(taxpayerPanel.municipalityJtextfield.getText());
                    taxpayer.setZip_code(taxpayerPanel.zipCodeJtextfield.getText());
                    taxpayer.setTax_type(taxpayerPanel.taxtypeJtextfield.getText());
                    taxpayer.setForm_type(taxpayerPanel.formtypeJtextfield.getText());
                    taxpayer.setAtc(taxpayerPanel.atcJtextfield.getText());

                    // 2. Pre-validate Spouse
                    model.Spouse spouse = null;
                    if (!spousePanel.spousenameJtextfield.getText().trim().isEmpty()) {
                        spouse = new model.Spouse();
                        spouse.setSpouse_employment_status(spousePanel.spouseempstatComboBox.getSelectedItem().toString());
                        spouse.setExemption_claimant(spousePanel.exemptclaimComboBox.getSelectedItem().toString());
                        spouse.setSpouse_fullname(spousePanel.spousenameJtextfield.getText());
                        spouse.setSpouse_tin(spousePanel.spousetinJtextfield.getText());
                        spouse.setSpouse_emp_tin(spousePanel.spouseemptinJtextfield.getText());
                    }

                    // 3. Pre-validate Employer Relationship
                    model.Employer newEmp = null;
                    model.Employee_Relationship empRel = null;
                    if (!employerPanel.empameJtextfield.getText().trim().isEmpty()) {
                        newEmp = new model.Employer();
                        newEmp.setEmp_tin(employerPanel.emptinJtextfield.getText());
                        newEmp.setEmp_fullname(employerPanel.empameJtextfield.getText());
                        newEmp.setEmp_full_address(employerPanel.empaddrJtextfield.getText());
                        newEmp.setZip_code(employerPanel.zipCodeJtextfield.getText());
                        newEmp.setEmp_landline(employerPanel.contnumJtextfield.getText());
                        newEmp.setEmp_mun_code(employerPanel.municicodeJtextfield.getText());
                        newEmp.setRegistering_office_type(employerPanel.typeofregComboBox.getSelectedItem().toString());

                        empRel = new model.Employee_Relationship();
                        empRel.setEmp_tin(employerPanel.emptinJtextfield.getText());
                        empRel.setEmp_type(employerPanel.typeofempComboBox.getSelectedItem().toString());
                        empRel.setHire_date(employerPanel.year8JSpinner.getValue() + "-" + getMonthNum(employerPanel.month8JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)employerPanel.day8JSpinner.getValue()));
                    }

                    // 4. Pre-validate Dependents
                    List<model.Dependent> dependentsToSave = new ArrayList<>();
                    preValidateDependent(dependentsToSave, dependentPanel.depename1Jtextfield, dependentPanel.month4JSpinner, dependentPanel.day4JSpinner, dependentPanel.year4JSpinner, dependentPanel.isincapa1ComboBox);
                    preValidateDependent(dependentsToSave, dependentPanel.depename2Jtextfield, dependentPanel.month5JSpinner, dependentPanel.day5JSpinner, dependentPanel.year5JSpinner, dependentPanel.isincapa2ComboBox);
                    preValidateDependent(dependentsToSave, dependentPanel.depename3Jtextfield, dependentPanel.month6JSpinner, dependentPanel.day6JSpinner, dependentPanel.year6JSpinner, dependentPanel.isincapa3ComboBox);
                    preValidateDependent(dependentsToSave, dependentPanel.depename4Jtextfield, dependentPanel.month7JSpinner, dependentPanel.day7JSpinner, dependentPanel.year7JSpinner, dependentPanel.isincapa4ComboBox);

                    // ==========================================
                    // SAVE PHASE (Only reached if no validation errors occurred above)
                    // ==========================================
                    int applicantId = sql.saveTaxpayer(taxpayer);

                    if (applicantId != -1) {
                        if (spouse != null) {
                            spouse.setApplicant_id(applicantId);
                            sql.saveSpouse(spouse);
                        }

                        if (newEmp != null && empRel != null) {
                            sql.saveEmployerIfNotExists(newEmp);
                            empRel.setApplicant_id(applicantId);
                            sql.saveEmployeeRelationship(empRel);
                        }

                        for (model.Dependent dep : dependentsToSave) {
                            dep.setApplicant_id(applicantId);
                            sql.saveDependent(dep);
                        }

                        JOptionPane.showMessageDialog(AddApplication.this, "Application successfully saved! Applicant ID: " + applicantId, "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AddApplication.this, "Failed to save to database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddApplication.this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Title panel at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel titleLabel = new JLabel("Add New Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 65, 150));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);
        add(tabbedpane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true); //Make the frame visible
    }

    private void preValidateDependent(List<model.Dependent> list, JTextField name, JSpinner m, JSpinner d, JSpinner y, JComboBox inc) {
        if (!name.getText().trim().isEmpty()) {
            model.Dependent dep = new model.Dependent();
            dep.setDependent_fullname(name.getText());
            String month = getMonthNum(m.getValue().toString());
            String day = String.format("%02d", (int)d.getValue());
            String year = y.getValue().toString();
            dep.setDependent_dob(year + "-" + month + "-" + day);
            dep.setIs_incapacitated(inc.getSelectedItem().toString());
            list.add(dep);
        }
    }

    private String getMonthNum(String mName) {
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        for(int i=0; i<months.length; i++) {
            if(months[i].equals(mName)) {
                return String.format("%02d", i+1);
            }
        }
        return mName;
    }

    private void addValidationListeners(JPanel panel, Runnable onUpdate) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { onUpdate.run(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { onUpdate.run(); }
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { onUpdate.run(); }
                });
            } else if (c instanceof JPanel) {
                addValidationListeners((JPanel) c, onUpdate);
            }
        }
    }

    private void checkSubmit(JButton submitButton, TaxpayerPanel taxpayerPanel, SpousePanel spousePanel, EmployerPanel employerPanel) {
        boolean tpComplete = !taxpayerPanel.taxpayername.getText().trim().isEmpty() &&
                             !taxpayerPanel.placeobJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.mothernameJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.fathernameJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.citizenshipJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.emailaddJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.localaddressJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.municipalityJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.zipCodeJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.idnumJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.idissuerJtextfield.getText().trim().isEmpty() &&
                             !taxpayerPanel.idplaceJtextfield.getText().trim().isEmpty();

        boolean empComplete = !employerPanel.emptinJtextfield.getText().trim().isEmpty() &&
                              !employerPanel.empameJtextfield.getText().trim().isEmpty();

        boolean spComplete = true;
        if ("Married".equals(taxpayerPanel.civilstatusComboBox.getSelectedItem())) {
            spComplete = !spousePanel.spousenameJtextfield.getText().trim().isEmpty();
        }

        submitButton.setEnabled(tpComplete && empComplete && spComplete);
    }
}
