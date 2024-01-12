import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingCartFrame {
    private static JFrame shoppingCartFrame;
    private static JTable shoppingCartTable;
    private static JPanel informationPanel, infoPanel1, infoPanel2;
    private static JLabel totalLabel, finalTotalLabel, firstDisLabel, threeItemsDisLabel,
            totalNumLabel, finalTotalNumLabel, firstDisNumLabel, threeItemsDisNumLabel;
    public ShoppingCartFrame(){
        shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(600,500);
        shoppingCartFrame.setLayout(new GridLayout(2,1));

        shoppingCartTable = createTable(WestminsterFrame.usersShoppingCart.getProductList());
        JScrollPane shoppingCartPane = new JScrollPane(shoppingCartTable);
        shoppingCartPane.setBorder(new EmptyBorder(28, 28, 7, 28));

        informationPanel = new JPanel(new GridLayout(1,2));
        infoPanel1 = new JPanel();
        infoPanel2 = new JPanel();

//        infoPanel1.setLayout();
        infoPanel1.setLayout(new BoxLayout(infoPanel1, BoxLayout.Y_AXIS));
        infoPanel2.setLayout(new BoxLayout(infoPanel2, BoxLayout.Y_AXIS));

//        infoPanel1.setBackground(Color.green);
//        infoPanel2.setBackground(Color.blue);

        totalLabel = new JLabel("Total: ");
        finalTotalLabel = new JLabel("Final Total: ");
        firstDisLabel = new JLabel("First Purchase Discount (10%)");
        threeItemsDisLabel = new JLabel("Three Items in same Category Discount (20%)");
        totalNumLabel = new JLabel("88.79$");
        finalTotalNumLabel = new JLabel("60.05$");
        firstDisNumLabel = new JLabel("8.58$");
        threeItemsDisNumLabel = new JLabel("17.20$");


        infoPanel1.add(totalLabel);
        infoPanel1.add(firstDisLabel);
        infoPanel1.add(threeItemsDisLabel);
        infoPanel1.add(finalTotalLabel);

        infoPanel2.add(totalNumLabel);
        infoPanel2.add(firstDisNumLabel);
        infoPanel2.add(threeItemsDisNumLabel);
        infoPanel2.add(finalTotalNumLabel);

        informationPanel.add(infoPanel1);
        informationPanel.add(infoPanel2);

        shoppingCartFrame.add(shoppingCartPane);
        shoppingCartTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 1) { // Check if the edited column is the Quantity column
                DefaultTableModel model1 = (DefaultTableModel) shoppingCartTable.getModel();
                Object quantity =  model1.getValueAt(row, column);
                System.out.println(quantity.toString());
                // Get the relative Product object and update the quantity using the setter method
                Product product = WestminsterFrame.usersShoppingCart.getProductList().get(row);
                product.setNoOfItems(Integer.parseInt(quantity.toString()));
                updateTableModel();
                updateInformation();
//                System.out.println(product.displayProducts());
            }
        });
        shoppingCartFrame.add(informationPanel);
        shoppingCartFrame.setVisible(true);
    }

    public static JTable createTable(ArrayList<Product> productList) {
        String[] columnNames = {"Product", "Quantity", "Price ($)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Product product : productList) {
            switch (product.getProductType()) {
                case "Electronics":
                    Object[] rowDataElectronic = {
                            product.getProductId() + " " +
                                    product.getProductName() + " " +
                                    product.getBrand() + ", " + product.getWarrantyPeriod(),
                            product.getNumberofavailableitems(),
                            product.getPrice(),};
                    model.addRow(rowDataElectronic);
                    break;
                case "Clothing":
                    Object[] rowDataClothing = {
                            product.getProductId() + " " +
                                    product.getProductName() + " " +
                                    product.getSize() + ", " + product.getColour(),
                            product.getNumberofavailableitems(),
                            product.getPrice()*product.getNumberofavailableitems(),
                    };
                    model.addRow(rowDataClothing);
                    break;
            }
        }
        JTable table = new JTable(model);
//        table.setDefaultEditor(Object.class, null);
        return table;
    }
    public static void updateTableModel() {
        DefaultTableModel model = (DefaultTableModel) shoppingCartTable.getModel();
        model.setRowCount(0);
        ArrayList<Product> productList = WestminsterFrame.usersShoppingCart.getProductList();

        for (Product product : productList) {
            switch (product.getProductType()) {
                case "Electronics":
                    Object[] rowDataElectronic = {
                            product.getProductId() + " " +
                                    product.getProductName() + " " +
                                    product.getBrand() + ", " + product.getWarrantyPeriod(),
                            product.getNumberofavailableitems(),
                            product.getPrice(),};
                    model.addRow(rowDataElectronic);
                    break;
                case "Clothing":
                    Object[] rowDataClothing = {
                            product.getProductId() + " " +
                                    product.getProductName() + " " +
                                    product.getSize() + ", " + product.getColour(),
                            product.getNumberofavailableitems(),
                            product.getPrice()*product.getNumberofavailableitems(),
                    };
                    model.addRow(rowDataClothing);
                    break;
            }
        }
    }

    public static void updateInformation(){
        int electronicCount = WestminsterFrame.getProductList(WestminsterFrame.usersShoppingCart.getProductList(), "Electronics").size();
        int clothingCount = WestminsterFrame.getProductList(WestminsterFrame.usersShoppingCart.getProductList(), "Clothing").size();
        totalNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.totalCost()));
//        firstDisNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.firstDiscount(User.newAccount));
        firstDisNumLabel.setText("Hehe");

        if(electronicCount > 3 || clothingCount > 3){
            threeItemsDisNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.threeItemsDiscount()));
        }else {
            threeItemsDisNumLabel.setText("0");
        }
        finalTotalNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.finalTotalValue()));
    }
}
