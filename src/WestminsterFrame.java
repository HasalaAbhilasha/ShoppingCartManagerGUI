import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WestminsterFrame extends JFrame {
    public static ShoppingCart usersShoppingCart = new ShoppingCart();
    public JFrame westminsterFrame = new JFrame("Westminster Shopping Center");
    public static JPanel topPanel, bottomPanel,
            mediaPanel, mediaPanel1, mediaPanel2, mediaPanel3,
            bottomPanel1, bottomPanel2 ;

    public static JTable table;
    public static JScrollPane scrollPane;
    public static String dropdownOption;
    public static Product selectedProduct;
    public static JLabel idLabel, categoryLabel, nameLabel, availItemsLabel, extraLabel1, extraLabel2;


    public WestminsterFrame(){
        westminsterFrame.setSize(800,700);
        westminsterFrame.setLayout(new GridLayout(2,1));

        topPanel = new JPanel(new GridLayout(2,1));
        bottomPanel = new JPanel(new GridLayout(1,2));
        mediaPanel = new JPanel(new GridLayout(1,3));

        mediaPanel1 = new JPanel(new GridBagLayout());
        mediaPanel2 = new JPanel(new GridBagLayout());
        mediaPanel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel1 = new JPanel();
        bottomPanel2 = new JPanel();

        bottomPanel1.setLayout(new BoxLayout(bottomPanel1, BoxLayout.Y_AXIS));

        int marginSize = 7;
        EmptyBorder emptyBorder = new EmptyBorder(marginSize, marginSize*4, marginSize, 0);

        idLabel = new JLabel("Product ID - ");
        nameLabel = new JLabel("Name - ");
        categoryLabel = new JLabel("category -");
        availItemsLabel = new JLabel("available label - ");
        extraLabel1 = new JLabel("Size -");
        extraLabel2 = new JLabel("Color - ");

        bottomPanel1.setBorder(emptyBorder);
        idLabel.setBorder(emptyBorder);
        nameLabel.setBorder(emptyBorder);
        categoryLabel.setBorder(emptyBorder);
        availItemsLabel.setBorder(emptyBorder);
        extraLabel1.setBorder(emptyBorder);
        extraLabel2.setBorder(emptyBorder);




        JLabel topPanelText = new JLabel("Select Product Category");
        JLabel bottomPanelText = new JLabel("Selected Product - Details");

        String[] dropdownList = {"All", "Electronic", "Clothing"};
        JComboBox<String> dropdownMenu = new JComboBox<>(dropdownList);
        dropdownMenu.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");

        dropdownOption = (String) dropdownMenu.getSelectedItem();

        JButton shoppingCartButton = new JButton("Shopping Cart");
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        bottomPanel2.add(addToCartButton);

        mediaPanel1.add(topPanelText);
        mediaPanel2.add(dropdownMenu);
        mediaPanel3.add(shoppingCartButton);

        mediaPanel.add(mediaPanel1);
        mediaPanel.add(mediaPanel2);
        mediaPanel.add(mediaPanel3);

        table = createTable(WestminsterShoppingManager.productList.getProductList());
        scrollPane = new JScrollPane(table);

        dropdownMenu.addActionListener(e -> {
            dropdownOption = (String) dropdownMenu.getSelectedItem();
            System.out.println(dropdownOption);
            updateTableModel();
            topPanel.revalidate();
        });

        topPanel.add(mediaPanel);
        topPanel.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int modelRow = table.convertRowIndexToModel(selectedRow);
                        selectedProduct = getProductList(WestminsterShoppingManager.productList.getProductList(), dropdownOption).get(modelRow);
                        updateDisplayPanel(selectedProduct);
                    }
                }
            }
        });

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShoppingCartFrame().updateInformation();
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct != null) {
                    // Check if the selected product is already in the cart
                    boolean productExists = false;
                    for (Product cartProduct : usersShoppingCart.getProductList()) {
                        if (cartProduct.getProductId().equals(selectedProduct.getProductId())) {
                            int newQuantity = cartProduct.getNumberofavailableitems() + 1;
                            cartProduct.setNoOfItems(newQuantity);
                            productExists = true;
                            break;
                        }
                    }

                    if (!productExists) {
                        selectedProduct.setNoOfItems(1);
                        usersShoppingCart.addProduct(selectedProduct);
                    }

                    try {
                        ShoppingCartFrame.updateInformation();
                        ShoppingCartFrame.updateTableModel();
                    } catch (NullPointerException ignored) {
                    }
                }
            }
        });

        bottomPanel1.add(bottomPanelText);
        bottomPanel1.add(idLabel);
        bottomPanel1.add(categoryLabel);
        bottomPanel1.add(nameLabel);
        bottomPanel1.add(extraLabel1);
        bottomPanel1.add(extraLabel2);
        bottomPanel1.add(availItemsLabel);

        bottomPanel.add(bottomPanel1);
        bottomPanel.add(bottomPanel2);

        westminsterFrame.add(topPanel);
        westminsterFrame.add(bottomPanel);

        westminsterFrame.setVisible(true);
    }



    public static JTable createTable(ArrayList<Product> productList) {
        String[] columnNames = {"Product ID", "Name", "Category", "Price ($)", "Info"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Product product : productList) {
            Object[] rowData;

            // Check if the item availability is less than 3 and set the color to red
            if (product.getNumberofavailableitems() < 3) {
                rowData = new Object[]{
                        "<html><font color='red'>" + product.getProductId() + "</font></html>",
                        "<html><font color='red'>" + product.getProductName() + "</font></html>",
                        "<html><font color='red'>" + product.getProductType() + "</font></html>",
                        "<html><font color='red'>" + product.getPrice() + "</font></html>",
                        "<html><font color='red'>" + product.displayProducts() + "</font></html>"
                };
            } else {
                rowData = new Object[]{
                        product.getProductId(),
                        product.getProductName(),
                        product.getProductType(),
                        product.getPrice(),
                        product.displayProducts()
                };
            }

            model.addRow(rowData);
        }

        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        return table;
    }

    private void updateTableModel() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ArrayList<Product> productList = getProductList(WestminsterShoppingManager.productList.getProductList(), dropdownOption);

        for (Product product : productList) {
            Object[] rowData;

            if (product.getNumberofavailableitems() < 3) {
                rowData = new Object[]{
                        "<html><font color='red'>" + product.getProductId() + "</font></html>",
                        "<html><font color='red'>" + product.getProductName() + "</font></html>",
                        "<html><font color='red'>" + product.getProductType() + "</font></html>",
                        "<html><font color='red'>" + product.getPrice() + "</font></html>",
                        "<html><font color='red'>" + product.displayProducts() + "</font></html>"
                };
            } else {
                // No red color formatting for items with stock 3 or more
                rowData = new Object[]{
                        product.getProductId(),
                        product.getProductName(),
                        product.getProductType(),
                        product.getPrice(),
                        product.displayProducts()
                };
            }

            model.addRow(rowData);
        }
    }


    public static void updateDisplayPanel(Product product){

        idLabel.setText("Product ID - "+ product.getProductId());
        nameLabel.setText("Name - "+ product.getProductName());
        categoryLabel.setText("Category - "+ product.getProductType());
        availItemsLabel.setText("Items available - "+ product.getNumberofavailableitems());

        switch (product.getProductType()){
            case "Electronics":
                extraLabel1.setText("Brand - "+ product.getBrand());
                extraLabel2.setText("Warranty Period - "+ product.getWarrantyPeriod());
                break;
            case "Clothing":
                extraLabel1.setText("Size - "+ product.getSize());
                extraLabel2.setText("Colour - "+ product.getColour());
                break;
        }

    }
    public static ArrayList<Product> getProductList(ArrayList<Product> productList, String dropdownOption){
        ArrayList<Product> selectedProductList = new ArrayList<>();
        switch (dropdownOption){
            case "All":
                selectedProductList = productList;
                break;
            case "Electronic":
                for (Product product: productList){
                    if (product.getProductType().equals("Electronics")){
                       selectedProductList.add(product);
                    }
                }
                break;
            case "Clothing":
                for (Product product: productList){
                    if (product.getProductType().equals("Clothing")){
                        selectedProductList.add(product);
                    }
                }
                break;
        }
        return selectedProductList;
    }
}
