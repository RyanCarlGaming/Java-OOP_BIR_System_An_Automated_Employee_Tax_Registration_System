package view; 

//import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.*;
//import java.awt.*;


public class RegistrationForm extends JFrame {
    //Make the constructor for Jframe or the window for GUI
    public RegistrationForm() {

        setTitle("BIR Form 1902"); //Set the title of the frame
        setSize(1920, 1080); //Set the size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the frame when hitting close button
        


        //Intialize the tabbed Pane which holds the different tabs or sections
        JTabbedPane tabbedpane = new JTabbedPane();
        tabbedpane.setBounds(0,0,1920,1080);
       

        //Make the tabs and add the panels of each tab
        tabbedpane.add("Taxpayer", new TaxpayerPanel());
        tabbedpane.add("Spouse", new SpousePanel());
        tabbedpane.add("Dependent", new DependentPanel());
        tabbedpane.add("Employer", new EmployerPanel());

        add(tabbedpane);
        setVisible(true); //Make the frame visible
       

        
    }


}
