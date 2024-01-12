import java.util.ArrayList;
import java.util.Collections;

public class ShoppingCart {
    private ArrayList<Product> productList;
    private ArrayList<Product> selectedItems = new ArrayList<>();

    public ShoppingCart() {
        this.productList = new ArrayList<>();
    }
    public void addToCart(Product product) {
        selectedItems.add(product);
    }
    public ArrayList<Product> getSelectedItems() {
        return selectedItems;
    }
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product product : selectedItems) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }


    public void addProduct(Product product) {
        productList.add(product);
    }

    public ArrayList<Product> getProductList() {
        // Ensure the productList is sorted based on your Product class implementation
        Collections.sort(productList);
        return productList;
    }

    public void deleteProduct(Product product) {
        productList.remove(product);
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }


}
