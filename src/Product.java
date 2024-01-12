public abstract class Product implements Comparable<Product>{
    private String ProductId;
    private String ProductName;
    private int Numberofavailableitems;
    private double Price;
    private String productType;

    public Product(String productId,String productName,int numberofavailableitems,double price, String productType){
        this.ProductId = productId;
        this.ProductName = productName;
        this.Numberofavailableitems = numberofavailableitems;
        this.Price = price;
        this.productType = productType;
    }
    public double getPrice(){
        return Price;
    }
    public String getProductId(){
        return ProductId;
    }
    public String getProductType(){
        return productType;
    }
    public String getProductName(){
        return ProductName;
    }
    public int getNumberofavailableitems(){
        return Numberofavailableitems;
    }

    public String getBrand(){
        return getBrand();
    }
    public int getWarrantyPeriod(){
        return getWarrantyPeriod();
    }
    public String getSize(){
        return getSize();
    }
    public String getColour(){
        return getColour();
    }
    abstract String displayProducts();

    public int compareTo(Product product){
        return ProductId.compareTo(product.ProductId);
    }

    public void setNoOfItems(int quantity) {
        this.Numberofavailableitems = quantity;
    }
}
