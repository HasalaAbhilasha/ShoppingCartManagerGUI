import javax.swing.*;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HashMap<String, User> userCredentials = User.loadCredentialsFromFile();

            LoginFrame loginFrame = new LoginFrame(userCredentials);
            loginFrame.setVisible(true);

            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    User.saveCredentialsToFile(userCredentials);
                }
            });
        });
    }
}
