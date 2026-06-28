package view;

import javax.swing.*;

public class TaxpayerPanel extends JPanel {

    public JComboBox<String> taxpayerComboBox;
    public JSpinner dayJSpinner;
    public JSpinner monthJSpinner;
    public JSpinner yearJSpinner;
    public PlaceholderTextField taxtin;
    public PlaceholderTextField rdocode;
    public PlaceholderTextField taxpayername;
    public JComboBox<String> civilstatusComboBox;
    public JComboBox<String> genderComboBox;
    public JSpinner day1JSpinner;
    public JSpinner month1JSpinner;
    public JSpinner year1JSpinner;
    public PlaceholderTextField placeobJtextfield;
    public PlaceholderTextField mothernameJtextfield;
    public PlaceholderTextField fathernameJtextfield;
    public PlaceholderTextField citizenshipJtextfield;
    public PlaceholderTextField othercitizenJtextfield;
    public JComboBox<String> idtypeComboBox;
    public PlaceholderTextField idnumJtextfield;
    public PlaceholderTextField idissuerJtextfield;
    public PlaceholderTextField idplaceJtextfield;
    public JSpinner day2JSpinner;
    public JSpinner month2JSpinner;
    public JSpinner year2JSpinner;
    public JSpinner day3JSpinner;
    public JSpinner month3JSpinner;
    public JSpinner year3JSpinner;
    public PlaceholderTextField landlineJtextfield;
    public PlaceholderTextField mobilenumJtextfield;
    public PlaceholderTextField faxnumJtextfield;
    public PlaceholderTextField emailaddJtextfield;
    public PlaceholderTextField localaddressJtextfield;
    public PlaceholderTextField foreignaddressJtextfield;
    public PlaceholderTextField municipalityJtextfield;
    public PlaceholderTextField taxtypeJtextfield;
    public PlaceholderTextField formtypeJtextfield;
    public PlaceholderTextField atcJtextfield;
    public PlaceholderTextField zipCodeJtextfield;

    public TaxpayerPanel() {

        setLayout(null);

        // Adjusted to prevent overlapping. Col1: 20, Col2: 570, Col3: 1110
        int rh = 28;
        int rg = 36;
        int y0 = 12;

        // ===== COLUMN 1: Taxpayer Info =====
        int c1lbl = 20;
        int c1lblW = 205;
        int c1fld = 230;
        int c1w = 250;

        JLabel taxptitlelabel = new JLabel("Part I - Taxpayer Information");
        taxptitlelabel.setBounds(c1lbl, y0, 400, rh);
        add(taxptitlelabel);

        int row = 1;
        JLabel taxpayerLabel = new JLabel("Taxpayer Type");
        taxpayerLabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(taxpayerLabel);
        String[] taxtype = {"Local Employee", "Resident Alien Employee"};
        taxpayerComboBox = new JComboBox<>(taxtype);
        taxpayerComboBox.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(taxpayerComboBox);

        row = 2;
        JLabel BIRregdateLabel = new JLabel("BIR Registration Date (MM/DD/YYYY)");
        BIRregdateLabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(BIRregdateLabel);

        JLabel day = new JLabel("Day");
        day.setBounds(c1fld, y0 + rg*row, 30, rh);
        dayJSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        dayJSpinner.setBounds(c1fld+30, y0 + rg*row, 45, rh);
        add(day); add(dayJSpinner);

        JLabel month = new JLabel("Month");
        month.setBounds(c1fld+85, y0 + rg*row, 45, rh);
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        monthJSpinner = new JSpinner(new SpinnerListModel(months));
        monthJSpinner.setBounds(c1fld+130, y0 + rg*row, 90, rh);
        add(month); add(monthJSpinner);

        JLabel year = new JLabel("Year");
        year.setBounds(c1fld+230, y0 + rg*row, 35, rh);
        yearJSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2026,1));
        yearJSpinner.setBounds(c1fld+265, y0 + rg*row, 60, rh);
        add(year); add(yearJSpinner);

        row = 3;
        JLabel taxpayertinlabel = new JLabel("Taxpayer TIN");
        taxpayertinlabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(taxpayertinlabel);
        taxtin = new PlaceholderTextField("e.g., 123-456-789-000");
        taxtin.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(taxtin);

        row = 4;
        JLabel rdocodelabel = new JLabel("RDO Code");
        rdocodelabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(rdocodelabel);
        rdocode = new PlaceholderTextField("Auto-filled based on Municipality");
        rdocode.setBounds(c1fld, y0 + rg*row, c1w, rh);
        rdocode.setEditable(false);
        add(rdocode);

        row = 5;
        JLabel taxpayernamelabel = new JLabel("Taxpayer FullName");
        taxpayernamelabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(taxpayernamelabel);
        taxpayername = new PlaceholderTextField("Last Name, First Name, Middle Name");
        taxpayername.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(taxpayername);

        row = 6;
        JLabel civilstatuslabel = new JLabel("Civil Status");
        civilstatuslabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(civilstatuslabel);
        String[] civilStatusOptions = {"Single", "Married", "Widow/er", "Legally Separated", "With Qualified Dependent Child/ren"};
        civilstatusComboBox = new JComboBox<>(civilStatusOptions);
        civilstatusComboBox.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(civilstatusComboBox);

        row = 7;
        JLabel genderlabel = new JLabel("Gender");
        genderlabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(genderlabel);
        String[] gender = {"Male", "Female"};
        genderComboBox = new JComboBox<>(gender);
        genderComboBox.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(genderComboBox);

        row = 8;
        JLabel dobLabel = new JLabel("Date of Birth (MM/DD/YYYY)");
        dobLabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(dobLabel);

        JLabel day1 = new JLabel("Day");
        day1.setBounds(c1fld, y0 + rg*row, 30, rh);
        day1JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day1JSpinner.setBounds(c1fld+30, y0 + rg*row, 45, rh);
        add(day1); add(day1JSpinner);

        JLabel month1 = new JLabel("Month");
        month1.setBounds(c1fld+85, y0 + rg*row, 45, rh);
        String[] months1 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month1JSpinner = new JSpinner(new SpinnerListModel(months1));
        month1JSpinner.setBounds(c1fld+130, y0 + rg*row, 90, rh);
        add(month1); add(month1JSpinner);

        JLabel year1 = new JLabel("Year");
        year1.setBounds(c1fld+230, y0 + rg*row, 35, rh);
        year1JSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2026,1));
        year1JSpinner.setBounds(c1fld+265, y0 + rg*row, 60, rh);
        add(year1); add(year1JSpinner);

        row = 9;
        JLabel placeoblabel = new JLabel("Place of Birth");
        placeoblabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(placeoblabel);
        placeobJtextfield = new PlaceholderTextField("e.g., Quezon City");
        placeobJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(placeobJtextfield);

        row = 10;
        JLabel mothernamelabel = new JLabel("Mother's Maiden Name");
        mothernamelabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(mothernamelabel);
        mothernameJtextfield = new PlaceholderTextField("Last Name, First Name, Middle Name");
        mothernameJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(mothernameJtextfield);

        row = 11;
        JLabel fathernamelabel = new JLabel("Father's Name");
        fathernamelabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(fathernamelabel);
        fathernameJtextfield = new PlaceholderTextField("Last Name, First Name, Middle Name");
        fathernameJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(fathernameJtextfield);

        row = 12;
        JLabel citizenshiplabel = new JLabel("Citizenship");
        citizenshiplabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(citizenshiplabel);
        citizenshipJtextfield = new PlaceholderTextField("e.g., Filipino");
        citizenshipJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(citizenshipJtextfield);

        row = 13;
        JLabel othercitizenlabel = new JLabel("Other Citizenship");
        othercitizenlabel.setBounds(c1lbl, y0 + rg*row, c1lblW, rh);
        add(othercitizenlabel);
        othercitizenJtextfield = new PlaceholderTextField("e.g., None");
        othercitizenJtextfield.setBounds(c1fld, y0 + rg*row, c1w, rh);
        add(othercitizenJtextfield);


        // ===== COLUMN 2: Identification Details =====
        int c2lbl = 580;
        int c2lblW = 205;
        int c2fld = 790;
        int c2w = 250;

        JLabel iddetailslabel = new JLabel("Identification Details");
        iddetailslabel.setBounds(c2lbl, y0, 220, rh);
        add(iddetailslabel);

        row = 1;
        JLabel idtypelabel = new JLabel("Identification Type");
        idtypelabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(idtypelabel);
        String[] idTypes = {"Passport", "Driver's License", "UMID", "PhilID", "TIN Card", "Voter's ID", "Other"};
        idtypeComboBox = new JComboBox<>(idTypes);
        idtypeComboBox.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(idtypeComboBox);

        row = 2;
        JLabel idnumlabel = new JLabel("Identification Number");
        idnumlabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(idnumlabel);
        idnumJtextfield = new PlaceholderTextField("e.g., P1234567A");
        idnumJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(idnumJtextfield);

        row = 3;
        JLabel idissuerlabel = new JLabel("Identification Issuer");
        idissuerlabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(idissuerlabel);
        idissuerJtextfield = new PlaceholderTextField("e.g., DFA");
        idissuerJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(idissuerJtextfield);

        row = 4;
        JLabel idplacelabel = new JLabel("ID Place/Country of Issue");
        idplacelabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(idplacelabel);
        idplaceJtextfield = new PlaceholderTextField("e.g., Manila");
        idplaceJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(idplaceJtextfield);

        row = 5;
        JLabel effectdatelabel = new JLabel("Effective Date (MM/DD/YYYY)");
        effectdatelabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(effectdatelabel);

        JLabel day2 = new JLabel("Day");
        day2.setBounds(c2fld, y0 + rg*row, 30, rh);
        day2JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day2JSpinner.setBounds(c2fld+30, y0 + rg*row, 45, rh);
        add(day2); add(day2JSpinner);

        JLabel month2 = new JLabel("Month");
        month2.setBounds(c2fld+85, y0 + rg*row, 45, rh);
        String[] months2 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month2JSpinner = new JSpinner(new SpinnerListModel(months2));
        month2JSpinner.setBounds(c2fld+130, y0 + rg*row, 90, rh);
        add(month2); add(month2JSpinner);

        JLabel year2 = new JLabel("Year");
        year2.setBounds(c2fld+230, y0 + rg*row, 35, rh);
        year2JSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2026,1));
        year2JSpinner.setBounds(c2fld+265, y0 + rg*row, 60, rh);
        add(year2); add(year2JSpinner);

        row = 6;
        JLabel expirydatelabel = new JLabel("Expiry Date (MM/DD/YYYY)");
        expirydatelabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(expirydatelabel);

        JLabel day3 = new JLabel("Day");
        day3.setBounds(c2fld, y0 + rg*row, 30, rh);
        day3JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day3JSpinner.setBounds(c2fld+30, y0 + rg*row, 45, rh);
        add(day3); add(day3JSpinner);

        JLabel month3 = new JLabel("Month");
        month3.setBounds(c2fld+85, y0 + rg*row, 45, rh);
        String[] months3 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month3JSpinner = new JSpinner(new SpinnerListModel(months3));
        month3JSpinner.setBounds(c2fld+130, y0 + rg*row, 90, rh);
        add(month3); add(month3JSpinner);

        JLabel year3 = new JLabel("Year");
        year3.setBounds(c2fld+230, y0 + rg*row, 35, rh);
        year3JSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2035,1));
        year3JSpinner.setBounds(c2fld+265, y0 + rg*row, 60, rh);
        add(year3); add(year3JSpinner);

        row = 7;
        JLabel prefercontacttypelabel = new JLabel("Preferred Contact Type");
        prefercontacttypelabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(prefercontacttypelabel);

        row = 8;
        JLabel landlinelabel = new JLabel("Landline");
        landlinelabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(landlinelabel);
        landlineJtextfield = new PlaceholderTextField("e.g., 02-8123-4567");
        landlineJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(landlineJtextfield);

        row = 9;
        JLabel mobilenumlabel = new JLabel("Mobile Number");
        mobilenumlabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(mobilenumlabel);
        mobilenumJtextfield = new PlaceholderTextField("e.g., 09123456789");
        mobilenumJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(mobilenumJtextfield);

        row = 10;
        JLabel faxnumlabel = new JLabel("Fax Number");
        faxnumlabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(faxnumlabel);
        faxnumJtextfield = new PlaceholderTextField("e.g., 02-8123-4568");
        faxnumJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(faxnumJtextfield);

        row = 11;
        JLabel emailaddlabel = new JLabel("Email Address (required)");
        emailaddlabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(emailaddlabel);
        emailaddJtextfield = new PlaceholderTextField("e.g., example@domain.com");
        emailaddJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(emailaddJtextfield);

        row = 12;
        JLabel localaddresslabel = new JLabel("Local Residence Address");
        localaddresslabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(localaddresslabel);
        localaddressJtextfield = new PlaceholderTextField("House/Unit No., Street, Subd., Brgy.");
        localaddressJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(localaddressJtextfield);

        row = 13;
        JLabel foreignaddresslabel = new JLabel("Foreign Address");
        foreignaddresslabel.setBounds(c2lbl, y0 + rg*row, c2lblW, rh);
        add(foreignaddresslabel);
        foreignaddressJtextfield = new PlaceholderTextField("e.g., N/A");
        foreignaddressJtextfield.setBounds(c2fld, y0 + rg*row, c2w, rh);
        add(foreignaddressJtextfield);


        // ===== COLUMN 3: Municipality / Tax / Form / ATC =====
        int c3lbl = 1130;
        int c3lblW = 85;
        int c3fld = 1220;
        int c3w = 120;

        row = 1;
        JLabel municipalitylabel = new JLabel("Municipality");
        municipalitylabel.setBounds(c3lbl, y0 + rg*row, c3lblW, rh);
        add(municipalitylabel);
        municipalityJtextfield = new PlaceholderTextField("e.g. Manila");
        municipalityJtextfield.setBounds(c3fld, y0 + rg*row, c3w, rh);
        add(municipalityJtextfield);

        // Add focus listener for auto-filling RDO code and mun code
        municipalityJtextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String input = municipalityJtextfield.getText().trim();
                if (!input.isEmpty()) {
                    dao.Sql sql = new dao.Sql();
                    for (model.Location loc : sql.getLocations()) {
                        if (loc.getMun().equalsIgnoreCase(input) || loc.getMun_code().equalsIgnoreCase(input)) {
                            rdocode.setText(loc.getRdo_code());
                            municipalityJtextfield.setText(loc.getMun_code());
                            break;
                        }
                    }
                }
            }
        });

        row = 2;
        JLabel zipCodelabel = new JLabel("Zip Code");
        zipCodelabel.setBounds(c3lbl, y0 + rg*row, c3lblW, rh);
        add(zipCodelabel);
        zipCodeJtextfield = new PlaceholderTextField("e.g. 1000");
        zipCodeJtextfield.setBounds(c3fld, y0 + rg*row, c3w, rh);
        add(zipCodeJtextfield);

        row = 3;
        JLabel taxtypelabel = new JLabel("Tax Type");
        taxtypelabel.setBounds(c3lbl, y0 + rg*row, c3lblW, rh);
        add(taxtypelabel);
        taxtypeJtextfield = new PlaceholderTextField("");
        taxtypeJtextfield.setBounds(c3fld, y0 + rg*row, c3w, rh);
        taxtypeJtextfield.setText("Income Tax");
        taxtypeJtextfield.setEditable(false);
        add(taxtypeJtextfield);

        row = 4;
        JLabel formtypelabel = new JLabel("Form Type");
        formtypelabel.setBounds(c3lbl, y0 + rg*row, c3lblW, rh);
        add(formtypelabel);
        formtypeJtextfield = new PlaceholderTextField("");
        formtypeJtextfield.setBounds(c3fld, y0 + rg*row, c3w, rh);
        formtypeJtextfield.setText("1700");
        formtypeJtextfield.setEditable(false);
        add(formtypeJtextfield);

        row = 5;
        JLabel atclabel = new JLabel("ATC");
        atclabel.setBounds(c3lbl, y0 + rg*row, c3lblW, rh);
        add(atclabel);
        atcJtextfield = new PlaceholderTextField("");
        atcJtextfield.setBounds(c3fld, y0 + rg*row, c3w, rh);
        atcJtextfield.setText("II011");
        atcJtextfield.setEditable(false);
        add(atcJtextfield);
    }
}
