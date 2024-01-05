import java.util.Scanner;

public class WestminsterShoppingManager {
    private static ShoppingCart shoppingCart = new ShoppingCart();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("1.Add Product");
            System.out.println("2.remove Product");
            System.out.println("3.display all Products");
            System.out.println("4.save");
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
                default:
                    System.out.println("wrong input");
            }
        }

    }

    private static void savefile() {

        // comment2
    }

    private static void deleteProduct() {
        System.out.println("Choose the product type");
        System.out.println("1.Clothing");
        System.out.println("2.Electronics");
        int productType = input.nextInt();

        System.out.println("Enter Product ID :");
        String productToDelete = input.next();

        switch (productType) {
            case 1:
                for (int i = 0; i < shoppingCart.getProductList().size(); i++) {
                    if (productToDelete.equals(shoppingCart.getProductList().get(i).getProductId())
                            && shoppingCart.getProductList().get(i).getProductType().equals("clothing")) {
                        shoppingCart.getProductList().get(i).displayProducts();
                        shoppingCart.removeProduct(shoppingCart.getProductList().get(i));
                        System.out.println("Item deleted successfully");
                        System.out.println("Items Left in the Cart : " + shoppingCart.getProductList().size());
                    }
                }
                break;
            case 2:
                for (int i = 0; i < shoppingCart.getProductList().size(); i++) {
                    if (productToDelete.equals(shoppingCart.getProductList().get(i).getProductId())
                            && shoppingCart.getProductList().get(i).getProductType().equals("electronic")) {
                        shoppingCart.getProductList().get(i).displayProducts();
                        shoppingCart.removeProduct(shoppingCart.getProductList().get(i));
                        System.out.println("Item deleted successfully");
                        System.out.println("Items Left in the Cart : " + shoppingCart.getProductList().size());
                    }
                }
                break;
            default:
                System.out.println("invalid product type");
                break;
        }

    }

    // comment
    private static void addProduct() {
        System.out.println("Choose the product type");
        System.out.println("1.Clothing");
        System.out.println("2.Electronics");
        int productType = input.nextInt();

        System.out.println("Enter the productID");
        String ProductId = input.next();
        System.out.println("Enter the product Name");
        String ProductName = input.next();
        System.out.println("Enter No of Items");
        int Numberofavailableitems = input.nextInt();
        System.out.println("Enter the Price");
        double price = input.nextDouble();

        if (productType == 1) {
            System.out.println("Enter the size");
            String size = input.next();
            System.out.println("Enter the color");
            String color = input.next();

            Clothing clothing = new Clothing(ProductId, ProductName, Numberofavailableitems, price, "clothing", size,
                    color);
            shoppingCart.addProduct(clothing);
        } else if (productType == 2) {
            System.out.println("Enter the brand");
            String brand = input.next();
            System.out.println("Enter the warranty Period");
            int warrentyperiod = input.nextInt();

            Electronics electronics = new Electronics(ProductId, ProductName, Numberofavailableitems, price,
                    "electronic", brand, warrentyperiod);
            shoppingCart.addProduct(electronics);
        } else {
            System.out.println("Invalid product type");
        }

    }

    private static void displayProduct() {
        for (int i = 0; i < shoppingCart.getProductList().size(); i++) {
            shoppingCart.getProductList().get(i).displayProducts();
        }
    }
}
