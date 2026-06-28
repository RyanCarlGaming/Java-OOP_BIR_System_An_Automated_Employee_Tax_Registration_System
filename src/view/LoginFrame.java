package view;

import javax.swing.*;
import dao.Sql;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private int failedatt = 0;
    private CardLayout cardLayout;
    private JPanel containerPanel;

    public LoginFrame() {
        setTitle("BIR Form 1902 Login");
        setSize(500, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 
        setResizable(false); 

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        // Create panels
        JPanel loginPanel = createLoginPanel();
        JPanel signupPanel = createSignupPanel();

        containerPanel.add(loginPanel, "LOGIN");
        containerPanel.add(signupPanel, "SIGNUP");

        add(containerPanel);
        cardLayout.show(containerPanel, "LOGIN");
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("BIR Form 1902", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setForeground(new Color(30, 65, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Sign in label
        JLabel signinLabel = new JLabel("Sign In", SwingConstants.CENTER);
        signinLabel.setFont(new Font("Inter", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(signinLabel, gbc);

        // Error Label
        JLabel errorLabel = new JLabel("Invalid Username or Password", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 5, 10);
        panel.add(errorLabel, gbc);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 2, 10);
        panel.add(usernameLabel, gbc);

        // Username Field
        JTextField usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        panel.add(usernameField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        gbc.gridy = 5;
        panel.add(passwordLabel, gbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        panel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 65, 150));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 35));
        gbc.gridy = 7;
        gbc.insets = new Insets(15, 10, 5, 10);
        panel.add(loginButton, gbc);

        // Toggle to Signup
        JButton toggleButton = new JButton("Sign Up");
        toggleButton.setForeground(new Color(30, 65, 150));
        toggleButton.setBackground(Color.WHITE);
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 10, 10, 10);
        panel.add(toggleButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                Sql db = new Sql();
                if (db.validateLogin(username, password)) {
                    errorLabel.setVisible(false);
                    dispose();
                    new MainMenuFrame().setVisible(true);
                } else {
                    failedatt++;
                    errorLabel.setVisible(true);

                    if (failedatt >= 3) {
                        JOptionPane.showMessageDialog(null, "Too many failed attempts. Try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(containerPanel, "SIGNUP");
            }
        });

        return panel;
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("BIR Form 1902", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel.setForeground(new Color(30, 65, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Sign Up title label
        JLabel signupLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        signupLabel.setFont(new Font("Inter", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 10, 5, 10);
        panel.add(signupLabel, gbc);

        // Message Label (Error / Success)
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Inter", Font.BOLD, 12));
        messageLabel.setVisible(false);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 5, 10);
        panel.add(messageLabel, gbc);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(2, 10, 2, 10);
        panel.add(usernameLabel, gbc);

        // Username Field
        JTextField usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        panel.add(usernameField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        gbc.gridy = 5;
        panel.add(passwordLabel, gbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        panel.add(passwordField, gbc);

        // Confirm Password Label
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        gbc.gridy = 7;
        panel.add(confirmPasswordLabel, gbc);

        // Confirm Password Field
        JPasswordField confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 8;
        panel.add(confirmPasswordField, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(30, 65, 150));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(200, 35));
        gbc.gridy = 9;
        gbc.insets = new Insets(15, 10, 5, 10);
        panel.add(registerButton, gbc);

        // Toggle to Login
        JButton toggleButton = new JButton("Sign In");
        toggleButton.setForeground(new Color(30, 65, 150));
        toggleButton.setBackground(Color.WHITE);
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 10;
        gbc.insets = new Insets(5, 10, 10, 10);
        panel.add(toggleButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Fields cannot be empty.");
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setVisible(true);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    messageLabel.setText("Passwords do not match.");
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setVisible(true);
                    return;
                }

                Sql db = new Sql();
                if (db.checkUserExists(username)) {
                    messageLabel.setText("Username already taken.");
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setVisible(true);
                } else {
                    boolean success = db.registerUser(username, password);
                    if (success) {
                        messageLabel.setText("Registration successful!");
                        messageLabel.setForeground(new Color(40, 120, 40));
                        messageLabel.setVisible(true);
                        
                        // Clear fields
                        usernameField.setText("");
                        passwordField.setText("");
                        confirmPasswordField.setText("");
                        
                        // Switch back to Login after a short delay
                        Timer timer = new Timer(1500, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                messageLabel.setVisible(false);
                                cardLayout.show(containerPanel, "LOGIN");
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        messageLabel.setText("Error registering user.");
                        messageLabel.setForeground(Color.RED);
                        messageLabel.setVisible(true);
                    }
                }
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setVisible(false);
                cardLayout.show(containerPanel, "LOGIN");
            }
        });

        return panel;
    }
}