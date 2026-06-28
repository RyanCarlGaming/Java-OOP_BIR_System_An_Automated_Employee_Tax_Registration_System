package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import dao.Sql;
import model.Taxpayer;

public class UpdateApplication extends JPanel {

    private JTextField searchField;
    private TaxpayerPanel taxpayerPanel;
    private SpousePanel spousePanel;
    private DependentPanel dependentPanel;
    private EmployerPanel employerPanel;
    private Taxpayer currentTaxpayer;

    public UpdateApplication() {
        setLayout(new BorderLayout());

        // Title panel at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel titleLabel = new JLabel("Update Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 65, 150));
        titlePanel.add(titleLabel);

        // TOP PANEL: Search Bar
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel searchLabel = new JLabel("Applicant ID:");
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(30, 65, 150));
        searchBtn.setForeground(Color.WHITE);
        
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        // Top wrapper
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(Color.WHITE);
        topWrapper.add(titlePanel, BorderLayout.NORTH);
        topWrapper.add(topPanel, BorderLayout.SOUTH);

        // CENTER PANEL: Tabs
        JTabbedPane tabbedpane = new JTabbedPane();
        taxpayerPanel = new TaxpayerPanel();
        spousePanel = new SpousePanel();
        dependentPanel = new DependentPanel();
        employerPanel = new EmployerPanel();

        tabbedpane.add("Taxpayer", taxpayerPanel);
        tabbedpane.add("Spouse", spousePanel);
        tabbedpane.add("Dependent", dependentPanel);
        tabbedpane.add("Employer", employerPanel);

        // BOTTOM PANEL: Update Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton updateBtn = new JButton("Update Application");
        updateBtn.setBackground(new Color(30, 65, 150));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setEnabled(false); // Grayed out until record is searched
        bottomPanel.add(updateBtn);

        add(topWrapper, BorderLayout.NORTH);
        add(tabbedpane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Validation logic
        Runnable onUpdate = new Runnable() {
            @Override
            public void run() {
                checkSubmit();
            }
        };

        addValidationListeners(taxpayerPanel, onUpdate);
        addValidationListeners(employerPanel, onUpdate);
        addValidationListeners(spousePanel, onUpdate);

        tabbedpane.setEnabledAt(1, false);

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

        // EVENT LISTENERS
        Sql sql = new Sql();

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(searchField.getText().trim());
                    currentTaxpayer = sql.getTaxpayerById(id);
                    
                    if (currentTaxpayer != null) {
                        populateTaxpayerPanel(currentTaxpayer);
                        
                        model.Spouse s = sql.getSpouseByApplicantId(id);
                        populateSpousePanel(s);
                        
                        model.Employee_Relationship er = sql.getEmployeeRelationshipByApplicantId(id);
                        model.Employer emp = null;
                        if(er != null) emp = sql.getEmployerByTin(er.getEmp_tin());
                        populateEmployerPanel(emp, er);
                        
                        List<model.Dependent> deps = sql.getDependentsByApplicantId(id);
                        populateDependentPanel(deps);
                        
                        tabbedpane.setEnabledAt(1, "Married".equals(taxpayerPanel.civilstatusComboBox.getSelectedItem()));
                        checkSubmit();
                        JOptionPane.showMessageDialog(null, "Record found and loaded!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No Applicant found with ID: " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric ID.");
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTaxpayer == null) {
                    JOptionPane.showMessageDialog(null, "Please search for an applicant first.");
                    return;
                }
                
                try {
                    readTaxpayerPanel(currentTaxpayer);
                    
                    model.Spouse spouse = null;
                    if (!spousePanel.spousenameJtextfield.getText().trim().isEmpty()) {
                        spouse = new model.Spouse();
                        spouse.setApplicant_id(currentTaxpayer.getApplicant_id());
                        spouse.setSpouse_employment_status(spousePanel.spouseempstatComboBox.getSelectedItem().toString());
                        spouse.setExemption_claimant(spousePanel.exemptclaimComboBox.getSelectedItem().toString());
                        spouse.setSpouse_fullname(spousePanel.spousenameJtextfield.getText());
                        spouse.setSpouse_tin(spousePanel.spousetinJtextfield.getText());
                        spouse.setSpouse_emp_tin(spousePanel.spouseemptinJtextfield.getText());
                    }

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
                        empRel.setApplicant_id(currentTaxpayer.getApplicant_id());
                        empRel.setEmp_tin(employerPanel.emptinJtextfield.getText());
                        empRel.setEmp_type(employerPanel.typeofempComboBox.getSelectedItem().toString());
                        empRel.setHire_date(employerPanel.year8JSpinner.getValue() + "-" + getMonthNum(employerPanel.month8JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)employerPanel.day8JSpinner.getValue()));
                    }

                    List<model.Dependent> dependentsToSave = new ArrayList<>();
                    preValidateDependent(dependentsToSave, dependentPanel.depename1Jtextfield, dependentPanel.month4JSpinner, dependentPanel.day4JSpinner, dependentPanel.year4JSpinner, dependentPanel.isincapa1ComboBox);
                    preValidateDependent(dependentsToSave, dependentPanel.depename2Jtextfield, dependentPanel.month5JSpinner, dependentPanel.day5JSpinner, dependentPanel.year5JSpinner, dependentPanel.isincapa2ComboBox);
                    preValidateDependent(dependentsToSave, dependentPanel.depename3Jtextfield, dependentPanel.month6JSpinner, dependentPanel.day6JSpinner, dependentPanel.year6JSpinner, dependentPanel.isincapa3ComboBox);
                    preValidateDependent(dependentsToSave, dependentPanel.depename4Jtextfield, dependentPanel.month7JSpinner, dependentPanel.day7JSpinner, dependentPanel.year7JSpinner, dependentPanel.isincapa4ComboBox);

                    boolean success = sql.updateTaxpayer(currentTaxpayer);
                    if (success) {
                        if (spouse != null) sql.upsertSpouse(spouse);
                        if (newEmp != null && empRel != null) {
                            sql.upsertEmployer(newEmp); // Added method to Sql.java implicitly via previous edit but we called it saveEmployerIfNotExists. Wait! I added upsertEmployer to Sql.java? No, wait! I didn't add upsertEmployer to Sql.java in the previous step?
                            sql.upsertEmployeeRelationship(empRel);
                        }
                        sql.upsertDependents(currentTaxpayer.getApplicant_id(), dependentsToSave);
                        JOptionPane.showMessageDialog(null, "Application Updated Successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating application.");
                    }
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, "Validation Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setSpinnerDate(String dateStr, JSpinner y, JSpinner m, JSpinner d) {
        if(dateStr != null && dateStr.contains("-")) {
            String[] p = dateStr.split("-");
            y.setValue(Integer.parseInt(p[0]));
            m.setValue(getMonthName(Integer.parseInt(p[1])));
            d.setValue(Integer.parseInt(p[2]));
        }
    }

    private void populateTaxpayerPanel(Taxpayer t) {
        taxpayerPanel.taxpayerComboBox.setSelectedItem(t.getTaxpayer_type());
        setSpinnerDate(t.getBir_reg_date(), taxpayerPanel.yearJSpinner, taxpayerPanel.monthJSpinner, taxpayerPanel.dayJSpinner);
        taxpayerPanel.taxtin.setText(t.getTaxpayer_tin() != null ? t.getTaxpayer_tin() : "");
        taxpayerPanel.rdocode.setText(t.getPcn() != null ? t.getPcn() : "");
        taxpayerPanel.taxpayername.setText(t.getTaxpayer_fullname() != null ? t.getTaxpayer_fullname() : "");
        taxpayerPanel.civilstatusComboBox.setSelectedItem(t.getCivil_status());
        taxpayerPanel.genderComboBox.setSelectedItem(t.getGender());
        setSpinnerDate(t.getDate_of_birth(), taxpayerPanel.year1JSpinner, taxpayerPanel.month1JSpinner, taxpayerPanel.day1JSpinner);
        taxpayerPanel.placeobJtextfield.setText(t.getPlace_of_birth() != null ? t.getPlace_of_birth() : "");
        taxpayerPanel.mothernameJtextfield.setText(t.getMother_fullname() != null ? t.getMother_fullname() : "");
        taxpayerPanel.fathernameJtextfield.setText(t.getFather_fullname() != null ? t.getFather_fullname() : "");
        taxpayerPanel.citizenshipJtextfield.setText(t.getCitizenship() != null ? t.getCitizenship() : "");
        taxpayerPanel.othercitizenJtextfield.setText(t.getOther_citizenship() != null ? t.getOther_citizenship() : "");
        taxpayerPanel.idtypeComboBox.setSelectedItem(t.getId_type());
        taxpayerPanel.idnumJtextfield.setText(t.getId_number() != null ? t.getId_number() : "");
        taxpayerPanel.idissuerJtextfield.setText(t.getId_issuer() != null ? t.getId_issuer() : "");
        taxpayerPanel.idplaceJtextfield.setText(t.getId_place() != null ? t.getId_place() : "");
        setSpinnerDate(t.getId_effectivity(), taxpayerPanel.year2JSpinner, taxpayerPanel.month2JSpinner, taxpayerPanel.day2JSpinner);
        setSpinnerDate(t.getId_expiry(), taxpayerPanel.year3JSpinner, taxpayerPanel.month3JSpinner, taxpayerPanel.day3JSpinner);
        taxpayerPanel.landlineJtextfield.setText(t.getLandline() != null ? t.getLandline() : "");
        taxpayerPanel.mobilenumJtextfield.setText(t.getMobile() != null ? t.getMobile() : "");
        taxpayerPanel.faxnumJtextfield.setText(t.getFax() != null ? t.getFax() : "");
        taxpayerPanel.emailaddJtextfield.setText(t.getEmail() != null ? t.getEmail() : "");
        taxpayerPanel.localaddressJtextfield.setText(t.getFull_address() != null ? t.getFull_address() : "");
        taxpayerPanel.foreignaddressJtextfield.setText(t.getForeign_address() != null ? t.getForeign_address() : "");
        taxpayerPanel.municipalityJtextfield.setText(t.getMun_code() != null ? t.getMun_code() : "");
        taxpayerPanel.zipCodeJtextfield.setText(t.getZip_code() != null ? t.getZip_code() : "");
        taxpayerPanel.taxtypeJtextfield.setText(t.getTax_type() != null ? t.getTax_type() : "");
        taxpayerPanel.formtypeJtextfield.setText(t.getForm_type() != null ? t.getForm_type() : "");
        taxpayerPanel.atcJtextfield.setText(t.getAtc() != null ? t.getAtc() : "");
    }

    private void readTaxpayerPanel(Taxpayer t) {
        t.setTaxpayer_type(taxpayerPanel.taxpayerComboBox.getSelectedItem().toString());
        t.setBir_reg_date(taxpayerPanel.yearJSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.monthJSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.dayJSpinner.getValue()));
        t.setTaxpayer_tin(taxpayerPanel.taxtin.getText());
        t.setPcn(taxpayerPanel.rdocode.getText());
        t.setTaxpayer_fullname(taxpayerPanel.taxpayername.getText());
        t.setCivil_status(taxpayerPanel.civilstatusComboBox.getSelectedItem().toString());
        t.setGender(taxpayerPanel.genderComboBox.getSelectedItem().toString());
        t.setDate_of_birth(taxpayerPanel.year1JSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.month1JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.day1JSpinner.getValue()));
        t.setPlace_of_birth(taxpayerPanel.placeobJtextfield.getText());
        t.setMother_fullname(taxpayerPanel.mothernameJtextfield.getText());
        t.setFather_fullname(taxpayerPanel.fathernameJtextfield.getText());
        t.setCitizenship(taxpayerPanel.citizenshipJtextfield.getText());
        t.setOther_citizenship(taxpayerPanel.othercitizenJtextfield.getText());
        t.setId_type(taxpayerPanel.idtypeComboBox.getSelectedItem().toString());
        t.setId_number(taxpayerPanel.idnumJtextfield.getText());
        t.setId_issuer(taxpayerPanel.idissuerJtextfield.getText());
        t.setId_place(taxpayerPanel.idplaceJtextfield.getText());
        t.setId_effectivity(taxpayerPanel.year2JSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.month2JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.day2JSpinner.getValue()));
        t.setId_expiry(taxpayerPanel.year3JSpinner.getValue() + "-" + getMonthNum(taxpayerPanel.month3JSpinner.getValue().toString()) + "-" + String.format("%02d", (int)taxpayerPanel.day3JSpinner.getValue()));
        t.setLandline(taxpayerPanel.landlineJtextfield.getText());
        t.setMobile(taxpayerPanel.mobilenumJtextfield.getText());
        t.setFax(taxpayerPanel.faxnumJtextfield.getText());
        t.setEmail(taxpayerPanel.emailaddJtextfield.getText());
        t.setFull_address(taxpayerPanel.localaddressJtextfield.getText());
        t.setForeign_address(taxpayerPanel.foreignaddressJtextfield.getText());
        t.setMun_code(taxpayerPanel.municipalityJtextfield.getText());
        t.setZip_code(taxpayerPanel.zipCodeJtextfield.getText());
        t.setTax_type(taxpayerPanel.taxtypeJtextfield.getText());
        t.setForm_type(taxpayerPanel.formtypeJtextfield.getText());
        t.setAtc(taxpayerPanel.atcJtextfield.getText());
    }

    private void populateSpousePanel(model.Spouse s) {
        if(s == null) {
            spousePanel.spousenameJtextfield.setText("");
            spousePanel.spousetinJtextfield.setText("");
            spousePanel.spouseemptinJtextfield.setText("");
            return;
        }
        spousePanel.spouseempstatComboBox.setSelectedItem(s.getSpouse_employment_status());
        spousePanel.exemptclaimComboBox.setSelectedItem(s.getExemption_claimant());
        spousePanel.spousenameJtextfield.setText(s.getSpouse_fullname() != null ? s.getSpouse_fullname() : "");
        spousePanel.spousetinJtextfield.setText(s.getSpouse_tin() != null ? s.getSpouse_tin() : "");
        if(s.getSpouse_emp_tin() != null) spousePanel.spouseemptinJtextfield.setText(s.getSpouse_emp_tin());
    }

    private void populateEmployerPanel(model.Employer e, model.Employee_Relationship er) {
        if(e == null || er == null) {
            employerPanel.emptinJtextfield.setText("");
            employerPanel.empameJtextfield.setText("");
            employerPanel.empaddrJtextfield.setText("");
            employerPanel.zipCodeJtextfield.setText("");
            employerPanel.contnumJtextfield.setText("");
            employerPanel.municicodeJtextfield.setText("");
            return;
        }
        employerPanel.typeofempComboBox.setSelectedItem(er.getEmp_type());
        employerPanel.typeofregComboBox.setSelectedItem(e.getRegistering_office_type());
        employerPanel.emptinJtextfield.setText(e.getEmp_tin() != null ? e.getEmp_tin() : "");
        employerPanel.empameJtextfield.setText(e.getEmp_fullname() != null ? e.getEmp_fullname() : "");
        employerPanel.empaddrJtextfield.setText(e.getEmp_full_address() != null ? e.getEmp_full_address() : "");
        employerPanel.zipCodeJtextfield.setText(e.getZip_code() != null ? e.getZip_code() : "");
        employerPanel.contnumJtextfield.setText(e.getEmp_landline() != null ? e.getEmp_landline() : "");
        employerPanel.municicodeJtextfield.setText(e.getEmp_mun_code() != null ? e.getEmp_mun_code() : "");
        setSpinnerDate(er.getHire_date(), employerPanel.year8JSpinner, employerPanel.month8JSpinner, employerPanel.day8JSpinner);
    }

    private void populateDependentPanel(List<model.Dependent> deps) {
        dependentPanel.depename1Jtextfield.setText("");
        dependentPanel.depename2Jtextfield.setText("");
        dependentPanel.depename3Jtextfield.setText("");
        dependentPanel.depename4Jtextfield.setText("");
        
        if(deps == null || deps.size() == 0) return;
        
        if(deps.size() > 0) {
            dependentPanel.depename1Jtextfield.setText(deps.get(0).getDependent_fullname() != null ? deps.get(0).getDependent_fullname() : "");
            dependentPanel.isincapa1ComboBox.setSelectedItem(deps.get(0).getIs_incapacitated());
            setSpinnerDate(deps.get(0).getDependent_dob(), dependentPanel.year4JSpinner, dependentPanel.month4JSpinner, dependentPanel.day4JSpinner);
        }
        if(deps.size() > 1) {
            dependentPanel.depename2Jtextfield.setText(deps.get(1).getDependent_fullname() != null ? deps.get(1).getDependent_fullname() : "");
            dependentPanel.isincapa2ComboBox.setSelectedItem(deps.get(1).getIs_incapacitated());
            setSpinnerDate(deps.get(1).getDependent_dob(), dependentPanel.year5JSpinner, dependentPanel.month5JSpinner, dependentPanel.day5JSpinner);
        }
        if(deps.size() > 2) {
            dependentPanel.depename3Jtextfield.setText(deps.get(2).getDependent_fullname() != null ? deps.get(2).getDependent_fullname() : "");
            dependentPanel.isincapa3ComboBox.setSelectedItem(deps.get(2).getIs_incapacitated());
            setSpinnerDate(deps.get(2).getDependent_dob(), dependentPanel.year6JSpinner, dependentPanel.month6JSpinner, dependentPanel.day6JSpinner);
        }
        if(deps.size() > 3) {
            dependentPanel.depename4Jtextfield.setText(deps.get(3).getDependent_fullname() != null ? deps.get(3).getDependent_fullname() : "");
            dependentPanel.isincapa4ComboBox.setSelectedItem(deps.get(3).getIs_incapacitated());
            setSpinnerDate(deps.get(3).getDependent_dob(), dependentPanel.year7JSpinner, dependentPanel.month7JSpinner, dependentPanel.day7JSpinner);
        }
    }
    
    private void preValidateDependent(List<model.Dependent> deps, JTextField name, JSpinner m, JSpinner d, JSpinner y, JComboBox inc) {
        if (!name.getText().trim().isEmpty()) {
            model.Dependent dependent = new model.Dependent();
            dependent.setApplicant_id(currentTaxpayer.getApplicant_id());
            dependent.setDependent_fullname(name.getText());
            dependent.setIs_incapacitated(inc.getSelectedItem().toString());
            dependent.setDependent_dob(y.getValue() + "-" + getMonthNum(m.getValue().toString()) + "-" + String.format("%02d", (int)d.getValue()));
            deps.add(dependent);
        }
    }

    private String getMonthName(int m) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        if(m >= 1 && m <= 12) return months[m-1];
        return "January";
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

    private void checkSubmit() {
        if (currentTaxpayer == null) {
            Component[] comps = getComponents();
            for (Component c : comps) {
                if (c instanceof JPanel) {
                    JPanel p = (JPanel)c;
                    for (Component inner : p.getComponents()) {
                        if (inner instanceof JButton && ((JButton)inner).getText().equals("Update Application")) {
                            ((JButton)inner).setEnabled(false);
                            return;
                        }
                    }
                }
            }
            return;
        }

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

        Component[] comps = getComponents();
        for (Component c : comps) {
            if (c instanceof JPanel) {
                JPanel p = (JPanel)c;
                for (Component inner : p.getComponents()) {
                    if (inner instanceof JButton && ((JButton)inner).getText().equals("Update Application")) {
                        ((JButton)inner).setEnabled(tpComplete && empComplete && spComplete);
                    }
                }
            }
        }
    }

    private String getMonthNum(String mName) {
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        for(int i=0; i<months.length; i++) {
            if(months[i].equals(mName)) {
                return String.format("%02d", i+1);
            }
        }
        return "01";
    }
}
