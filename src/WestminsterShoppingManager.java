import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class WestminsterShoppingManager {
    public static ShoppingCart productList = new ShoppingCart();
    // Scanner for user input
    private static Scanner input = new Scanner(System.in);


    // Method to initiate the shopping cart application
    public static void runShoppingCart() {
        loadFile(); // Load existing products from the file

        while (true) {
            try {
                // Display menu options for user or manager
                System.out.println("Welcome to Westminster Shopping Manager!");
                System.out.println("----------------------------------------");
                System.out.println("What is your role?");
                System.out.println("1. User");
                System.out.println("2. Manager");
                System.out.println("0. Exit");
                int number = input.nextInt();

                switch (number) {
                    case 0:
                        return;
                    case 1:
                        // Start the user interface for shopping
                        SwingUtilities.invokeLater(() -> {
                            HashMap<String, User> userCredentials = User.loadCredentialsFromFile();
                            LoginFrame loginFrame = new LoginFrame(userCredentials);
                            loginFrame.setVisible(true);
                        });
                        break;
                    case 2:
                        // Access manager functionalities
                        manager();
                        break;
                    default:
                        System.out.println("Invalid input. Please enter 1, 2, or 0.");
                }
            } catch (java.util.InputMismatchException e) {
                // Handle non-integer input
                System.out.println("Invalid input. Please enter a number.");
                input.next(); // Consume invalid input to avoid an infinite loop
            }
        }
    }


    // Method for manager functionalities
    private static void manager() {
        while (true) {//Display menu options for manager
            System.out.println("1.Add Product");
            System.out.println("2.remove Product");
            System.out.println("3.display all Products");
            System.out.println("4.save");
            System.out.println("5. GUI");
            System.out.println("0.exit");


            int number = input.nextInt();
            switch (number) {
                case 0:
                    return;
                case 1:
                    addProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    displayProduct();
                    break;
                case 4:
                    savefile();
                    break;
                case 5:
                    SwingUtilities.invokeLater(() -> {
                        HashMap<String, User> userCredentials = User.loadCredentialsFromFile();
                        LoginFrame loginFrame = new LoginFrame(userCredentials);
                        loginFrame.setVisible(true);
                    });
                    break;

                default:
                    System.out.println("wrong input");
            }
        }
    }


    private static void savefile() {//save the products in the file
        try {
            FileWriter productFile = new FileWriter("savedProduct.txt");
            for (int i = 0; i < productList.getProductList().size(); i++) {
                productFile.write(productList.getProductList().get(i).displayProducts() + "\n");
            }
            productFile.close();
            System.out.println("Products saved successfully in saveProducts.txt");
        } catch (IOException e) {
            System.out.println("An error occurred!");
        }

    }


    private static void deleteProduct() {//delete the product

        System.out.println("Choose the product type");
        System.out.println("1.Clothing");
        System.out.println("2.Electronics");
        int productType = input.nextInt();

        System.out.println("Enter Product ID: ");
        String productToDelete = input.next();

        boolean deletedItem = false;

        switch (productType) {
            case 1:
                for (int i = 0; i < productList.getProductList().size(); i++) {
                    if (productToDelete.equals(productList.getProductList().get(i).getProductId()) && productList.getProductList().get(i).getProductType().equals("Clothing")) {
                        System.out.println(productList.getProductList().get(i).displayProducts());
                        productList.removeProduct(productList.getProductList().get(i));
                        deletedItem = true;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < productList.getProductList().size(); i++) {
                    if (productToDelete.equals(productList.getProductList().get(i).getProductId()) && productList.getProductList().get(i).getProductType().equals("Electronic")) {
                        System.out.println(productList.getProductList().get(i).displayProducts());
                        productList.removeProduct(productList.getProductList().get(i));
                        deletedItem = true;
                    }
                }
                break;
            default:
                System.out.println("invalid product type");
        }
        if (deletedItem) {
            System.out.println("Product deleted successfully!");
            System.out.println("Items Left in the Cart : " + productList.getProductList().size());

        } else {
            System.out.println("Product not found!");
        }


    }

    private static void addProduct() {//add the product
        try {
            System.out.println("Enter the productID, eg: #001");
            String productId = input.next();

            if (productId.charAt(0) != '#') {
                System.out.println("Invalid Product ID. The first letter must start with #");
                return;
            }

            System.out.println("Enter the product Name");
            String productName = input.next();
            System.out.println("Enter the number of items");
            int numberOfAvailableItems = input.nextInt();
            System.out.println("Enter the Price");
            double price = input.nextDouble();

            System.out.println("Enter the product type");
            System.out.println("1. Clothing");
            System.out.println("2. Electronics");
            int productType = input.nextInt();

            if (productType == 1) {
                System.out.println("Enter the size");
                String size = input.next();
                System.out.println("Enter the color");
                String color = input.next();

                Clothing clothing = new Clothing(productId, productName, numberOfAvailableItems, price, "Clothing", size, color);
                productList.addProduct(clothing);
            } else if (productType == 2) {
                System.out.println("Enter the brand");
                String brand = input.next();
                System.out.println("Enter the warranty Period");
                int warrantyPeriod = input.nextInt();

                Electronics electronics = new Electronics(productId, productName, numberOfAvailableItems, price, "Electronics", brand, warrantyPeriod);
                productList.addProduct(electronics);
            } else {
                System.out.println("Invalid product type");
            }
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }


    private static void displayProduct() {//display the product
        if (productList.getProductList().isEmpty()) {
            System.out.println("Product cart is Empty");
        } else {
            for (int i = 0; i < productList.getProductList().size(); i++) {
                System.out.println(productList.getProductList().get(i).displayProducts());
            }
        }
    }

    public static void loadFile() {//load the products from the file
        try {
            File saveFile = new File("savedProduct.txt");
            Scanner fileReader = new Scanner(saveFile);
            while (fileReader.hasNext()){
                String dataLine = fileReader.nextLine();
                String[] dataArray = dataLine.split("\\|");

                if (dataArray.length == 7){
                    if (dataArray[6].equals("Clothing")){
                        Clothing clothing = new Clothing(dataArray[0],dataArray[1],Integer.parseInt(dataArray[2]),Double.parseDouble(dataArray[3]),"Clothing",dataArray[4],dataArray[5]);
                        productList.addProduct(clothing);
                    } else if (dataArray[6].equals("Electronics")) {
                        Electronics electronics = new Electronics(dataArray[0],dataArray[1],Integer.parseInt(dataArray[2]),Double.parseDouble(dataArray[3]),"Electronics",dataArray[4],Integer.parseInt(dataArray[5]));
                        productList.addProduct(electronics);
                    }
                }else{
                    System.out.println("Invalid file format.");
                }
            }
            System.out.println("Loaded products from the saved File");
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
