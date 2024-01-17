public class  Clothing extends Product {
    private String size;    // Size of the clothing item
    private String colour;  // Colour of the clothing item

    // Constructor for Clothing
    public Clothing(String productId, String productName, int numberofavailableitems, double price, String productType, String size, String colour) {
        // Call the constructor parent class
        super(productId, productName, numberofavailableitems, price, productType);
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
        return (
                getProductId() + "|" + getProductName() + "|" + getNumberofavailableitems() + "|" + getPrice() + "|" + getSize() + "|" + getColour() + "|" + getProductType()
        );
    }
}
