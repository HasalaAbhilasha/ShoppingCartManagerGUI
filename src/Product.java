public abstract class Product {
    private String ProductId;
    private String ProductName;
    private int Numberofavailableitems;
    private double price;
    private String productType;

    public Product(String productId,String productName,int numberofavailableitems,double price,String productType){
        this.ProductId =productId;
        this.ProductName =productName;
        this.Numberofavailableitems =numberofavailableitems;
        this.price = price;
        this.productType = productType;
    }

    public double getPrice(){
        return price;
    }

    public String getProductId() {
        return ProductId;
    }
    public String getProductType() {
        return productType;
    }

    public int getNumberofavailableitems() {
        return Numberofavailableitems;
    }

    public String getProductName() {
        return ProductName;
    }

    abstract void displayProducts();

}