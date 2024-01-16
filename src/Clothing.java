// Clothing class, extends Product
public class Clothing extends Product {

    // Additional attributes for Clothing
    private String size;
    private String colour;

    // Constructor to initialize Clothing attributes and call the superclass constructor
    public Clothing(String productId, String productName, int numberofavailableitems, double price, String productType, String size, String colour) {
        // Call the constructor of the superclass (Product)
        super(productId, productName, numberofavailableitems, price, productType);

        // Initialize Clothing-specific attributes
        this.colour = colour;
        this.size = size;
    }

    // Getter method for size
    public String getSize() {
        return size;
    }

    // Getter method for colour
    public String getColour() {
        return colour;
    }

    // Method to display Clothing product information
    @Override
    public String displayProducts() {
        // Format and concatenate product information
        return (
                getProductId() + "|" +
                        getProductName() + "|" +
                        getNumberofavailableitems() + "|" +
                        getPrice() + "|" +
                        getSize() + "|" +
                        getColour() + "|" +
                        getProductType()
        );
    }
}
