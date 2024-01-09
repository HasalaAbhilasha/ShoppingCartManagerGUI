import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ShoppingCart {
    private ArrayList<Product> productList = new ArrayList<>();
    public void addProduct(Product product){
        productList.add(product);
    }
    public void removeProduct(Product product) {
        productList.remove(product);
    }
    public double totalCost(){
        double totalCost=0;
        for (int i = 0; i < productList.size(); i++)
            totalCost=totalCost+productList.get(i).getPrice()*productList.get(i).getNumberofavailableitems();
        return totalCost;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }
}