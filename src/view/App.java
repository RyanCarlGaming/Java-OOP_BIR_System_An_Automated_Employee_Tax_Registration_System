package view;

//import javax.swing.JPanel;

public class App {
    public static void main(String[] args) {
        //Create GUI components in EDT not in the Main so its safe to make the GUI. 
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);

        });

    }
}


