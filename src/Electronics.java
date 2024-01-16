public class Electronics extends Product {

    private String brand;            // Brand of the electronic item
    private int warrantyPeriod;      // Warranty period of the electronic item

    // Constructor for Electronics
    public Electronics(String productId, String productName, int numberofavailableitems, double price, String productType, String brand, int warrantyPeriod) {
        // Call the constructor of parent class
        super(productId, productName, numberofavailableitems, price, productType);

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
        return (
                getProductId() + "|" + getProductName() + "|" + getNumberofavailableitems() + "|" + getPrice() + "|" + getBrand() + "|" + getWarrantyPeriod() + "|" + getProductType()
        );
    }
}
