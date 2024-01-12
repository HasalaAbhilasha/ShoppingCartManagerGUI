public class Electronics extends Product{
    private String brand;
    private int warrentyperiod;

    public Electronics(String productId, String productName, int numberofavailableitems, double price, String productType, String brand, int warrentyperiod){
        super(productId,productName,numberofavailableitems,price,productType);
        this.brand =brand;
        this.warrentyperiod =warrentyperiod;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrentyperiod;
    }
    public String displayProducts() {
        return (getProductId()+"|"+getProductName()+"|"+getNumberofavailableitems()+"|"+getPrice()+"|"+getBrand()+"|"+getWarrantyPeriod()+"|"+getProductType());
    }

}
