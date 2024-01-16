// Electronics class, extends Product
public class Electronics extends Product {

    // Additional attributes for Electronics
    private String brand;
    private int warrantyPeriod;

    // Constructor to initialize Electronics attributes and call the superclass constructor
    public Electronics(String productId, String productName, int numberofavailableitems, double price, String productType, String brand, int warrantyPeriod) {
        // Call the constructor of the superclass (Product)
        super(productId, productName, numberofavailableitems, price, productType);

        // Initialize Electronics-specific attributes
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter method for brand
    public String getBrand() {
        return brand;
    }

    // Getter method for warranty period
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // Method to display Electronics product information
    @Override
    public String displayProducts() {
        // Format and concatenate product information
        return (
                getProductId() + "|" +
                        getProductName() + "|" +
                        getNumberofavailableitems() + "|" +
                        getPrice() + "|" +
                        getBrand() + "|" +
                        getWarrantyPeriod() + "|" +
                        getProductType()
        );
    }
}
