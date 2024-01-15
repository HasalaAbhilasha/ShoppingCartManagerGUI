import java.io.*;
import java.util.HashMap;

public class User implements Serializable {
    public static Boolean isNewUser;
    private String username;
    private String password;


    public User(String username, String password, boolean isNewUser) {
        this.username = username;
        this.password = password;
        this.isNewUser = isNewUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }

    public static HashMap<String, User> loadCredentialsFromFile() {
        HashMap<String, User> userCredentials = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user_credentials.ser"))) {
            userCredentials = (HashMap<String, User>) ois.readObject();

            // When loading existing users, set isNewUser to false
            for (User user : userCredentials.values()) {
                user.setNewUser(false);
            }

        } catch (IOException | ClassNotFoundException e) {
            // File does not exist or an error occurred during deserialization
            // It's okay, we'll return an empty HashMap
        }

        return userCredentials;
    }

    public static void saveCredentialsToFile(HashMap<String, User> userCredentials) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_credentials.ser"))) {
            oos.writeObject(userCredentials);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
}
