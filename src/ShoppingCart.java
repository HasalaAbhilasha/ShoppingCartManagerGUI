import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShoppingCart {
    private ArrayList<Product> productList;
    private double firstDisVal;
    private double threeItemDisVal;

    public ShoppingCart() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public double totalCost() {
        double totalCost = 0;
        for (Product product : productList) {
            totalCost += product.getPrice() * product.getNumberofavailableitems();
        }
        return totalCost;
    }

    public double firstDiscount(boolean newAccount) {
        if (newAccount) {
            firstDisVal = totalCost() * 0.1;
        }
        return firstDisVal;
    }

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

    public double finalTotalValue() {
        double finalTotalValue = totalCost() - firstDisVal - threeItemDisVal;
        return finalTotalValue;
    }

    public ArrayList<Product> getProductList() {
        Collections.sort(productList, Comparator.comparing(Product::getProductName));
        return productList;
    }

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
