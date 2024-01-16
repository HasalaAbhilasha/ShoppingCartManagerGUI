import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;

    private HashMap<String, User> userCredentials;

    public LoginFrame(HashMap<String, User> userCredentials) {
        this.userCredentials = userCredentials;

        setTitle("User Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameLabel)
                                .addComponent(passwordLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameField)
                                .addComponent(passwordField)
                                .addComponent(loginButton)
                                .addComponent(createAccountButton)))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField))
                .addComponent(loginButton)
                .addComponent(createAccountButton)
        );
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userCredentials.containsKey(username)) {
            String hashedPassword = userCredentials.get(username).getPassword();

            if (hashPassword(password).equals(hashedPassword)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose(); // Close the login frame
                WestminsterFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect Password. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username not found. Please try again.");
        }
    }

    private void createAccount() {
        String newUsername = JOptionPane.showInputDialog(this, "Enter a new username:");
        if (newUsername != null && !newUsername.trim().isEmpty()) {
            String newPassword = JOptionPane.showInputDialog(this, "Enter a new password:");
            if (newPassword != null) {
                // Hash the new password and create a new user
                String hashedPassword = hashPassword(newPassword);
                User newUser = new User(newUsername, hashedPassword, true);

                // Add the new user to the userCredentials HashMap
                userCredentials.put(newUsername, newUser);

                // Save the user credentials to the file
                User.saveCredentialsToFile(userCredentials);

                JOptionPane.showMessageDialog(this, "Account created successfully!");
                dispose(); // Close the login frame
                WestminsterFrame();
            }
        }
    }

    private void WestminsterFrame() {
        // Assuming ShoppingCartFrame has a constructor that takes no arguments

        WestminsterFrame westminsterFrame=new WestminsterFrame();
        westminsterFrame.setVisible(true);
        this.dispose(); // Close the LoginFrame
    }



    private String hashPassword(String password) {
        // In a real system, you would use a secure hashing algorithm, such as bcrypt or SHA-256
        // For simplicity, this example uses a basic hashing approach (not secure for production)
        return String.valueOf(password.hashCode());
    }

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
