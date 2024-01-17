import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShoppingCartFrame extends JFrame {
    private static JFrame shoppingCartFrame;
    private static JTable shoppingCartTable;
    private static JPanel informationPanel, infoPanel1, infoPanel2;
    private static JLabel totalLabel, finalTotalLabel, firstDisLabel, threeItemsDisLabel,
            totalNumLabel, finalTotalNumLabel, firstDiscNumLabel, threeItemsDisNumLabel;
    private JButton removeItemButton; // New Remove Item button
    JButton purchaseButton = new JButton("Purchase");


    public ShoppingCartFrame() {
        shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(600, 500);
        shoppingCartFrame.setLayout(new GridLayout(2, 1));
        setLocationRelativeTo(null);

        shoppingCartTable = createTable(WestminsterFrame.usersShoppingCart.getProductList());
        JScrollPane shoppingCartPane = new JScrollPane(shoppingCartTable);
        shoppingCartPane.setBorder(new EmptyBorder(28, 28, 7, 28));

        informationPanel = new JPanel(new GridLayout(1, 2));
        infoPanel1 = new JPanel();
        infoPanel2 = new JPanel();

        infoPanel1.setLayout(new BoxLayout(infoPanel1, BoxLayout.Y_AXIS));
        infoPanel2.setLayout(new BoxLayout(infoPanel2, BoxLayout.Y_AXIS));

        totalLabel = new JLabel("Total: ");
        finalTotalLabel = new JLabel("Final Total: ");
        firstDisLabel = new JLabel("First Purchase Discount (10%)");
        threeItemsDisLabel = new JLabel("Three Items in the same Category Discount (20%)");
        totalNumLabel = new JLabel("88.79$");
        finalTotalNumLabel = new JLabel("60.05$");
        firstDiscNumLabel = new JLabel("8.58$");
        threeItemsDisNumLabel = new JLabel("17.20$");

        infoPanel1.add(totalLabel);
        infoPanel1.add(firstDisLabel);
        infoPanel1.add(threeItemsDisLabel);
        infoPanel1.add(finalTotalLabel);
        infoPanel1.add(new JLabel("")); // Placeholder for spacing

        infoPanel2.add(totalNumLabel);
        infoPanel2.add(firstDiscNumLabel);
        infoPanel2.add(threeItemsDisNumLabel);
        infoPanel2.add(finalTotalNumLabel);
        infoPanel2.add(new JLabel("")); // Placeholder for spacing

        // Add Remove Item button
        removeItemButton = new JButton("Remove from Cart");
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedItem();
            }
        });
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseItems();
            }
        });
        infoPanel1.add(purchaseButton);
        infoPanel1.add(removeItemButton); // Add the button to the infoPanel1

        informationPanel.add(infoPanel1);
        informationPanel.add(infoPanel2);

        shoppingCartFrame.add(shoppingCartPane);
        shoppingCartTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 1) { // Check if the edited column is the Quantity column
                DefaultTableModel model1 = (DefaultTableModel) shoppingCartTable.getModel();
                Object quantity = model1.getValueAt(row, column);
                System.out.println(quantity.toString());
                Product product = WestminsterFrame.usersShoppingCart.getProductList().get(row);
                product.setNoOfItems(Integer.parseInt(quantity.toString()));
                updateTableModel();
                updateInformation();
            }
        });
        shoppingCartFrame.add(informationPanel);
        shoppingCartFrame.setVisible(true);
    }

    private void removeSelectedItem() {
        int selectedRow = shoppingCartTable.getSelectedRow();

        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) shoppingCartTable.getModel();
            Product selectedProduct = WestminsterFrame.usersShoppingCart.getProductList().get(selectedRow);

            // Update the quantity in the cart
            int newQuantity = selectedProduct.getNumberofavailableitems() - 1;
            if (newQuantity <= 0) {
                WestminsterFrame.usersShoppingCart.removeProduct(selectedProduct);
            } else {
                // Update the quantity
                selectedProduct.setNoOfItems(newQuantity);
            }

            // Update the table model and information
            updateTableModel();
            updateInformation();
        } else {
            JOptionPane.showMessageDialog(this, "Select an item to remove from the cart.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                            product.getPrice() * product.getNumberofavailableitems(),
                    };
                    model.addRow(rowDataClothing);
                    break;
            }
        }
        JTable table = new JTable(model);
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
                            product.getPrice() * product.getNumberofavailableitems(),
                    };
                    model.addRow(rowDataClothing);
                    break;
            }
        }
    }

    public static void updateInformation() {
        totalNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.totalCost()));
        firstDiscNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.firstDiscount(User.isNewUser)));
        threeItemsDisNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.threeItemsDiscount()));

        finalTotalNumLabel.setText(Double.toString(WestminsterFrame.usersShoppingCart.finalTotalValue()));
    }

    private void purchaseItems() {
        // Iterate through the products in the cart and update the available quantity
        for (Product cartProduct : WestminsterFrame.usersShoppingCart.getProductList()) {
            Product availableProduct = getProductById(cartProduct.getProductId());
            if (availableProduct != null) {
                int newAvailableQuantity = availableProduct.getNumberofavailableitems() - cartProduct.getNumberofavailableitems();
                availableProduct.setNoOfItems(newAvailableQuantity);
            }
        }

        // Clear the cart after purchase
        WestminsterFrame.usersShoppingCart.getProductList().clear();

        // Update the table model and information
        updateTableModel();
        updateInformation();
    }

    private Product getProductById(String productId) {
        for (Product availableProduct : WestminsterShoppingManager.productList.getProductList()) {
            if (availableProduct.getProductId().equals(productId)) {
                return availableProduct;
            }
        }
        return null;
    }
}
