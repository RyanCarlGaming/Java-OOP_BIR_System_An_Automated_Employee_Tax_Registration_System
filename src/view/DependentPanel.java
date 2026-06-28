package view;

import javax.swing.*;

public class DependentPanel extends JPanel {

    public JTextField depename1Jtextfield;
    public JSpinner day4JSpinner;
    public JSpinner month4JSpinner;
    public JSpinner year4JSpinner;
    public JComboBox isincapa1ComboBox;
    
    public JTextField depename2Jtextfield;
    public JSpinner day5JSpinner;
    public JSpinner month5JSpinner;
    public JSpinner year5JSpinner;
    public JComboBox isincapa2ComboBox;
    
    public JTextField depename3Jtextfield;
    public JSpinner day6JSpinner;
    public JSpinner month6JSpinner;
    public JSpinner year6JSpinner;
    public JComboBox isincapa3ComboBox;
    
    public JTextField depename4Jtextfield;
    public JSpinner day7JSpinner;
    public JSpinner month7JSpinner;
    public JSpinner year7JSpinner;
    public JComboBox isincapa4ComboBox;

    public DependentPanel() {

        setLayout(null);

        int rh = 28;
        int rg = 45;
        int y0 = 15;

        JLabel depetitlelabel = new JLabel("Part III - Dependents Information");
        depetitlelabel.setBounds(25, y0, 400, rh);
        add(depetitlelabel);

        // Column positions
        int nameX = 25;
        int nameFieldX = 150;
        int nameFieldW = 250;
        int dobDayX = 430;
        int incaFldX = 780;

        int row = 1;
        JLabel depenamelabel = new JLabel("Name of Qualified Dependent Children");
        depenamelabel.setBounds(nameX, y0 + rg*row, 250, rh);
        add(depenamelabel);

        JLabel depedoblabel = new JLabel("Date of Birth");
        depedoblabel.setBounds(dobDayX, y0 + rg*row, 120, rh);
        add(depedoblabel);

        JLabel depeisincalabel = new JLabel("Incapacitated?");
        depeisincalabel.setBounds(incaFldX, y0 + rg*row, 120, rh);
        add(depeisincalabel);

        // Dependent 1
        row = 2;
        JLabel depename1label = new JLabel("Dependent 1");
        depename1label.setBounds(nameX, y0 + rg*row, 120, rh);
        add(depename1label);
        depename1Jtextfield = new JTextField();
        depename1Jtextfield.setBounds(nameFieldX, y0 + rg*row, nameFieldW, rh);
        add(depename1Jtextfield);

        JLabel day4 = new JLabel("Day");
        day4.setBounds(dobDayX, y0 + rg*row, 28, rh);
        day4JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day4JSpinner.setBounds(dobDayX+28, y0 + rg*row, 45, rh);
        add(day4); add(day4JSpinner);

        JLabel month4 = new JLabel("Month");
        month4.setBounds(dobDayX+80, y0 + rg*row, 40, rh);
        String[] months4 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month4JSpinner = new JSpinner(new SpinnerListModel(months4));
        month4JSpinner.setBounds(dobDayX+120, y0 + rg*row, 90, rh);
        add(month4); add(month4JSpinner);

        JLabel year4 = new JLabel("Year");
        year4.setBounds(dobDayX+215, y0 + rg*row, 30, rh);
        year4JSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2026,1));
        year4JSpinner.setBounds(dobDayX+245, y0 + rg*row, 60, rh);
        add(year4); add(year4JSpinner);

        String[] isincapa1 = {"Yes", "No"};
        isincapa1ComboBox = new JComboBox<>(isincapa1);
        isincapa1ComboBox.setBounds(incaFldX, y0 + rg*row, 80, rh);
        add(isincapa1ComboBox);

        // Dependent 2
        row = 3;
        JLabel depename2label = new JLabel("Dependent 2");
        depename2label.setBounds(nameX, y0 + rg*row, 120, rh);
        add(depename2label);
        depename2Jtextfield = new JTextField();
        depename2Jtextfield.setBounds(nameFieldX, y0 + rg*row, nameFieldW, rh);
        add(depename2Jtextfield);

        JLabel day5 = new JLabel("Day");
        day5.setBounds(dobDayX, y0 + rg*row, 28, rh);
        day5JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day5JSpinner.setBounds(dobDayX+28, y0 + rg*row, 45, rh);
        add(day5); add(day5JSpinner);

        JLabel month5 = new JLabel("Month");
        month5.setBounds(dobDayX+80, y0 + rg*row, 40, rh);
        String[] months5 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month5JSpinner = new JSpinner(new SpinnerListModel(months5));
        month5JSpinner.setBounds(dobDayX+120, y0 + rg*row, 90, rh);
        add(month5); add(month5JSpinner);

        JLabel year5 = new JLabel("Year");
        year5.setBounds(dobDayX+215, y0 + rg*row, 30, rh);
        year5JSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2026,1));
        year5JSpinner.setBounds(dobDayX+245, y0 + rg*row, 60, rh);
        add(year5); add(year5JSpinner);

        String[] isincapa2 = {"Yes", "No"};
        isincapa2ComboBox = new JComboBox<>(isincapa2);
        isincapa2ComboBox.setBounds(incaFldX, y0 + rg*row, 80, rh);
        add(isincapa2ComboBox);

        // Dependent 3
        row = 4;
        JLabel depename3label = new JLabel("Dependent 3");
        depename3label.setBounds(nameX, y0 + rg*row, 120, rh);
        add(depename3label);
        depename3Jtextfield = new JTextField();
        depename3Jtextfield.setBounds(nameFieldX, y0 + rg*row, nameFieldW, rh);
        add(depename3Jtextfield);

        JLabel day6 = new JLabel("Day");
        day6.setBounds(dobDayX, y0 + rg*row, 28, rh);
        day6JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day6JSpinner.setBounds(dobDayX+28, y0 + rg*row, 45, rh);
        add(day6); add(day6JSpinner);

        JLabel month6 = new JLabel("Month");
        month6.setBounds(dobDayX+80, y0 + rg*row, 40, rh);
        String[] months6 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month6JSpinner = new JSpinner(new SpinnerListModel(months6));
        month6JSpinner.setBounds(dobDayX+120, y0 + rg*row, 90, rh);
        add(month6); add(month6JSpinner);

        JLabel year6 = new JLabel("Year");
        year6.setBounds(dobDayX+215, y0 + rg*row, 30, rh);
        year6JSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2026,1));
        year6JSpinner.setBounds(dobDayX+245, y0 + rg*row, 60, rh);
        add(year6); add(year6JSpinner);

        String[] isincapa3 = {"Yes", "No"};
        isincapa3ComboBox = new JComboBox<>(isincapa3);
        isincapa3ComboBox.setBounds(incaFldX, y0 + rg*row, 80, rh);
        add(isincapa3ComboBox);

        // Dependent 4
        row = 5;
        JLabel depename4label = new JLabel("Dependent 4");
        depename4label.setBounds(nameX, y0 + rg*row, 120, rh);
        add(depename4label);
        depename4Jtextfield = new JTextField();
        depename4Jtextfield.setBounds(nameFieldX, y0 + rg*row, nameFieldW, rh);
        add(depename4Jtextfield);

        JLabel day7 = new JLabel("Day");
        day7.setBounds(dobDayX, y0 + rg*row, 28, rh);
        day7JSpinner = new JSpinner(new SpinnerNumberModel(1,1,31,1));
        day7JSpinner.setBounds(dobDayX+28, y0 + rg*row, 45, rh);
        add(day7); add(day7JSpinner);

        JLabel month7 = new JLabel("Month");
        month7.setBounds(dobDayX+80, y0 + rg*row, 40, rh);
        String[] months7 = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month7JSpinner = new JSpinner(new SpinnerListModel(months7));
        month7JSpinner.setBounds(dobDayX+120, y0 + rg*row, 90, rh);
        add(month7); add(month7JSpinner);

        JLabel year7 = new JLabel("Year");
        year7.setBounds(dobDayX+215, y0 + rg*row, 30, rh);
        year7JSpinner = new JSpinner(new SpinnerNumberModel(2000,1900,2026,1));
        year7JSpinner.setBounds(dobDayX+245, y0 + rg*row, 60, rh);
        add(year7); add(year7JSpinner);

        String[] isincapa4 = {"Yes", "No"};
        isincapa4ComboBox = new JComboBox<>(isincapa4);
        isincapa4ComboBox.setBounds(incaFldX, y0 + rg*row, 80, rh);
        add(isincapa4ComboBox);
    }
}
