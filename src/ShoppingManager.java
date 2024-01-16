public interface ShoppingManager {
    // Adds a product to the product list
    void addProduct(Product product);

    // Deletes a product from the product list
    void deleteProduct(Product product);

    // Prints the list of products
    void printProductList();

    // Sorts the product list
    void sortProductList();

    // Writes the product list to a file
    void writeToFile();

    // Reads the product list from a file
    void readFromFile();

    // Generates a report for the product list
    void generateReport();

    // Runs the shopping cart functionality
    void runShoppingCart();
}
