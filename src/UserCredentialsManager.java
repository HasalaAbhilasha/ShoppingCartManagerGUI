import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class UserCredentialsManager {
    private static final String CREDENTIALS_FILE = "user_credentials.ser";
    private static final String SECRET_KEY = "secretKey"; // Change this to a secure key

    public static HashMap<String, User> loadCredentialsFromFile() {
        HashMap<String, User> credentials = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new CipherInputStream(new FileInputStream(CREDENTIALS_FILE), getCipher(Cipher.DECRYPT_MODE)))) {

            credentials = (HashMap<String, User>) ois.readObject();

        } catch (FileNotFoundException e) {
            // File doesn't exist, create an empty HashMap
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }

        return credentials;
    }

    public static void saveCredentialsToFile(HashMap<String, User> userCredentials) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new CipherOutputStream(new FileOutputStream(CREDENTIALS_FILE), getCipher(Cipher.ENCRYPT_MODE)))) {

            oos.writeObject(userCredentials);

        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }

    private static Cipher getCipher(int cipherMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipherMode, secretKey);
        return cipher;
    }
}
