import java.io.*;
import java.util.HashMap;

public class User implements Serializable {
    public static Boolean isNewUser; // Indicates if the user is a new user
    private String username;
    private String password;

    // Constructor to initialize User attributes
    public User(String username, String password, boolean isNewUser) {
        this.username = username;
        this.password = password;
        this.isNewUser = isNewUser;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Getter method for isNewUser
    public boolean isNewUser() {
        return isNewUser;
    }

    // Setter method for isNewUser
    public void setNewUser(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }

    // Load user credentials from a file and return as a HashMap
    public static HashMap<String, User> loadCredentialsFromFile() {
        HashMap<String, User> userCredentials = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user_credentials.ser"))) {
            userCredentials = (HashMap<String, User>) ois.readObject();

            // When loading existing users, set isNewUser to false
            for (User user : userCredentials.values()) {
                user.setNewUser(false);
            }

        } catch (IOException | ClassNotFoundException e) {
            //
        }

        return userCredentials;
    }

    // Save user credentials to a file
    public static void saveCredentialsToFile(HashMap<String, User> userCredentials) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_credentials.ser"))) {
            oos.writeObject(userCredentials);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
