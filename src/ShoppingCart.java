import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShoppingCart {
    private ArrayList<Product> productList;
    private double firstDisVal;
    private double threeItemDisVal;

    // Constructor to initialize the shopping cart
    public ShoppingCart() {
        this.productList = new ArrayList<>();
    }

    // Method to add a product to the shopping cart
    public void addProduct(Product product) {
        productList.add(product);
    }

    // Method to remove a product from the shopping cart
    public void removeProduct(Product product) {
        productList.remove(product);
    }

    // Method to calculate the total cost of products in the shopping cart
    public double totalCost() {
        double totalCost = 0;
        for (Product product : productList) {
            totalCost += product.getPrice() * product.getNumberofavailableitems();
        }
        return totalCost;
    }

    // Method to calculate the first purchase discount
    public double firstDiscount(boolean newAccount) {
        if (newAccount) {
            firstDisVal = totalCost() * 0.1;
        }
        return firstDisVal;
    }

    // Method to calculate the three items discount
    public double threeItemsDiscount() {
        int electronicCount = getProductCount("Electronics");
        int clothingCount = getProductCount("Clothing");

        // Check if the total count of Electronics or Clothing is greater than or equal to 3
        if (electronicCount + clothingCount >= 3) {
            threeItemDisVal = totalCost() * 0.2;
        } else {
            threeItemDisVal = 0;
        }

        return threeItemDisVal;
    }

    // Method to calculate the final total value
    public double finalTotalValue() {
        double finalTotalValue = totalCost() - firstDisVal - threeItemDisVal;
        return finalTotalValue;
    }

    // Method to get the sorted list of products in the shopping cart
    public ArrayList<Product> getProductList() {
        Collections.sort(productList, Comparator.comparing(Product::getProductName));
        return productList;
    }

    // Helper method to get the total count of products in a specific category
    private int getProductCount(String category) {
        int count = 0;
        for (Product product : productList) {
            if (product.getProductType().equals(category)) {
                count += product.getNumberofavailableitems();
            }
        }
        return count;
    }
}
