package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenuFrame extends JFrame{
    
    public MainMenuFrame () {

        //Set the Main Menu Frame
        setTitle("BIR Form 1902"); //Set the title of the frame
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximize the frame to fit the user's screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the frame when hitting close button
        setLocationRelativeTo(null); //Center the JFrame on the screen

        //Make header Panel 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30,65,150));
        headerPanel.setPreferredSize(new Dimension(1920, 54));

        
        //Make Left Panel for the title label
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(30,65,150));
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10)); // Adjusted vertical gap slightly to fit logo
        
        // Add Logo
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("resources/Logo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel(logoIcon);
        leftPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("BIR Form 1902");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        leftPanel.add(titleLabel);

        //Make Center Panel for the Application Navigation Bars

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(30,65,150));
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));

        //Make Add Application Button on the headerpanel
        JButton addButton = new JButton("Add Application");
        addButton.setBackground(new Color(30,65,150));
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        centerPanel.add(addButton);

        //Make View Application Button on the headerpanel
        JButton viewButton = new JButton("View Application");
        viewButton.setBackground(new Color(30,65,150));
        viewButton.setForeground(Color.WHITE);
        viewButton.setBorderPainted(false);
        viewButton.setFocusPainted(false);
        centerPanel.add(viewButton);

        //Make Search Application Button on the headerpanel
        JButton searchButton = new JButton("Search Application");
        searchButton.setBackground(new Color(30,65,150));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        centerPanel.add(searchButton);

        //Make Update Application Button on the headerpanel
        JButton updateButton = new JButton("Update Application");
        updateButton.setBackground(new Color(30,65,150));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        centerPanel.add(updateButton);

        //Make Delete Application Button on the headerpanel
        JButton deleteButton = new JButton("Delete Application");
        deleteButton.setBackground(new Color(30,65,150));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        centerPanel.add(deleteButton);

        //Make Report Button on the headerpanel
        JButton reportButton = new JButton("Report");
        reportButton.setBackground(new Color(30,65,150));
        reportButton.setForeground(Color.WHITE);
        reportButton.setBorderPainted(false);
        reportButton.setFocusPainted(false);
        centerPanel.add(reportButton);


        //Make right Panel forlogout button

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(30,65,150));
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));

        //Make Logout Button on the headerpanel
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(30,65,150));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        rightPanel.add(logoutButton);

        


        //Make CardLayout to store Add, View/Search, Update, Delete, Report Panels 
        //one at a time when use hits their JButton

        CardLayout maincardlayout = new CardLayout();
        JPanel mainPanel = new JPanel(maincardlayout);

        //Make Panels for Homepage, Add, View/Search, Update, Delete, Report with their JLabels
        
        JPanel addapp = new JPanel();
        JLabel addLabel = new JLabel("Add Application");
        addapp.add(addLabel);

        JPanel viewapp = new JPanel();
        JLabel viewLabel = new JLabel("View Application");
        viewapp.add(viewLabel);

        JPanel searchapp = new JPanel();
        JLabel searchLabel = new JLabel("Search Application");
        searchapp.add(searchLabel);

        JPanel updateapp = new JPanel();
        JLabel updateLabel = new JLabel("Update Application");
        updateapp.add(updateLabel);

        JPanel deleteapp = new JPanel();
        JLabel deleteLabel = new JLabel("Delete Application");
        deleteapp.add(deleteLabel);

        JPanel reportapp = new JPanel();
        JLabel reportLabel = new JLabel("Report");
        reportapp.add(reportLabel);

        //Change this after making the Panels
        //Add the Add, View/Search, Update, Delete, Report Panels to the MainPanel
        mainPanel.add(new AddApplication(), "ADD");
        mainPanel.add(new ViewApplication(), "VIEW");
        mainPanel.add(new SearchApplication(), "SEARCH");
        mainPanel.add(new UpdateApplication(), "UPDATE");
        mainPanel.add(new DeleteApplication(), "DELETE");
        mainPanel.add(new ReportPanel(), "REPORT");

        //Make action Listener for Add, View/Search, Update, Delete, Report Buttons;
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maincardlayout.show(mainPanel, "ADD");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maincardlayout.show(mainPanel, "VIEW");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maincardlayout.show(mainPanel, "SEARCH");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maincardlayout.show(mainPanel, "UPDATE");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maincardlayout.show(mainPanel, "DELETE");
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maincardlayout.show(mainPanel, "REPORT");
            }
        });


        //Make action Listener for Logout Button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Dispose to close MainMenu Frame to go back to LoginFrame
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        maincardlayout.show(mainPanel, "HOMEPAGE");



        //Add the leftPanel, centerPanel, and rightPanel into headerPanel
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(centerPanel, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        //Add the headerPanel with leftPanel, leftPanel, and rightPanel inside. 
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

    }
}


