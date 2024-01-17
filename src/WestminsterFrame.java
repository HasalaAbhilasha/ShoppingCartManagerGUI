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
    // Shopping cart for the user
    public static ShoppingCart usersShoppingCart = new ShoppingCart();

    public static JPanel topPanel, bottomPanel, mediaPanel, mediaPanel1, mediaPanel2, mediaPanel3, bottomPanel1, bottomPanel2;

    public static JTable table;
    public static JScrollPane scrollPane;
    public static String dropdownOption;
    public static Product selectedProduct;
    public static JLabel idLabel, categoryLabel, nameLabel, availItemsLabel, extraLabel1, extraLabel2;

    // Constructor for the WestminsterFrame
    public WestminsterFrame() {
        // Set up the main frame
        setTitle("Westminster Shopping Center");
        setSize(800, 700);
        setLayout(new GridLayout(2, 1));
        setLocationRelativeTo(null);

        // Create and configure panels for layout
        topPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel = new JPanel(new GridLayout(1, 2));
        mediaPanel = new JPanel(new GridLayout(1, 3));
        mediaPanel1 = new JPanel(new GridBagLayout());
        mediaPanel2 = new JPanel(new GridBagLayout());
        mediaPanel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel1 = new JPanel();
        bottomPanel2 = new JPanel();

        // Configure border for better spacing
        int marginSize = 7;
        EmptyBorder emptyBorder = new EmptyBorder(marginSize, marginSize * 4, marginSize, 0);

        // Labels for displaying product details
        idLabel = new JLabel("Product ID - ");
        nameLabel = new JLabel("Name - ");
        categoryLabel = new JLabel("Category -");
        availItemsLabel = new JLabel("Available Items - ");
        extraLabel1 = new JLabel("Extra Info 1 -");
        extraLabel2 = new JLabel("Extra Info 2 - ");

        // Apply the empty border to the labels for consistent spacing
        bottomPanel1.setBorder(emptyBorder);
        idLabel.setBorder(emptyBorder);
        nameLabel.setBorder(emptyBorder);
        categoryLabel.setBorder(emptyBorder);
        availItemsLabel.setBorder(emptyBorder);
        extraLabel1.setBorder(emptyBorder);
        extraLabel2.setBorder(emptyBorder);

        // Text labels and dropdown menu for filtering products
        JLabel topPanelText = new JLabel("Select Product Category");
        JLabel bottomPanelText = new JLabel("Selected Product - Details");
        String[] dropdownList = {"All", "Electronic", "Clothing"};
        JComboBox<String> dropdownMenu = new JComboBox<>(dropdownList);
        dropdownMenu.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");

        // Initialize dropdown option
        dropdownOption = (String) dropdownMenu.getSelectedItem();

        // Buttons for shopping cart and adding to cart
        JButton shoppingCartButton = new JButton("Shopping Cart");
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        bottomPanel2.add(addToCartButton);

        // Organize components in media panel
        mediaPanel1.add(topPanelText);
        mediaPanel2.add(dropdownMenu);
        mediaPanel3.add(shoppingCartButton);

        mediaPanel.add(mediaPanel1);
        mediaPanel.add(mediaPanel2);
        mediaPanel.add(mediaPanel3);

        // Create and configure the product table
        table = createTable(WestminsterShoppingManager.productList.getProductList());
        scrollPane = new JScrollPane(table);

        // Update dropdown option when selected
        dropdownMenu.addActionListener(e -> {
            dropdownOption = (String) dropdownMenu.getSelectedItem();
            updateTableModel();
            topPanel.revalidate();
        });

        // Add components to top panel
        topPanel.add(mediaPanel);
        topPanel.add(scrollPane);

        // Add a selection listener to the table
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int modelRow = table.convertRowIndexToModel(selectedRow);
                        selectedProduct = getProductList(WestminsterShoppingManager.productList.getProductList(), dropdownOption).get(modelRow);
                        updateDisplayPanel(selectedProduct);
                    }
                }
            }
        });

        // Open shopping cart frame when the button is clicked
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShoppingCartFrame().updateInformation();
            }
        });

        // Add product to the shopping cart when the button is clicked
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct != null) {
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

        // Configure the panel to display product details
        bottomPanel1.setLayout(new GridLayout(0, 1)); // Set GridLayout with one column

        bottomPanel1.add(bottomPanelText);
        bottomPanel1.add(idLabel);
        bottomPanel1.add(categoryLabel);
        bottomPanel1.add(nameLabel);
        bottomPanel1.add(extraLabel1);
        bottomPanel1.add(extraLabel2);
        bottomPanel1.add(availItemsLabel);

        bottomPanel2.setLayout(new BorderLayout());
        bottomPanel2.add(BorderLayout.SOUTH,  addToCartButton);



        // Add panels to the bottom panel
        bottomPanel.add(bottomPanel1);
        bottomPanel.add(bottomPanel2);

        // Add top and bottom panels to the main frame
        add(topPanel);
        add(bottomPanel);

        // Set the frame visible
        setVisible(true);
    }

    // Helper method to create a table with product data
    public static JTable createTable(ArrayList<Product> productList) {
        String[] columnNames = {"Product ID", "Name", "Category", "Price ($)", "Info"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Product product : productList) {
            Object[] rowData;

            // Check if the item availability is less than 3 and set the color to red
            if (product.getNumberofavailableitems() < 3) {
                rowData = new Object[]{    //reference from stackoverflow
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

    // method to update the table model based on the selected category
    private void updateTableModel() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ArrayList<Product> productList = getProductList(WestminsterShoppingManager.productList.getProductList(), dropdownOption);

        for (Product product : productList) {
            Object[] rowData;

            if (product.getNumberofavailableitems() < 3) {
                rowData = new Object[]{//reference from stackoverflow
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

    // method to update the display panel with selected product details
    // method to update the display panel with selected product details
    private void updateDisplayPanel(Product product) {
        // Clear existing labels
        idLabel.setText("Product ID - ");
        nameLabel.setText("Name - ");
        categoryLabel.setText("Category - ");
        availItemsLabel.setText("Items available - ");
        extraLabel1.setText("");
        extraLabel2.setText("");

        // Update labels with selected product details
        idLabel.setText(idLabel.getText() + product.getProductId());
        nameLabel.setText(nameLabel.getText() + product.getProductName());
        categoryLabel.setText(categoryLabel.getText() + product.getProductType());
        availItemsLabel.setText(availItemsLabel.getText() + product.getNumberofavailableitems());

        switch (product.getProductType()) {
            case "Electronics":
                extraLabel1.setText(extraLabel1.getText() + "Brand - " + product.getBrand());
                extraLabel2.setText(extraLabel2.getText() + "Warranty Period - " + product.getWarrantyPeriod());
                break;
            case "Clothing":
                extraLabel1.setText(extraLabel1.getText() + "Size - " + product.getSize());
                extraLabel2.setText(extraLabel2.getText() + "Color - " + product.getColour());
                break;
        }
    }


    // method to filter products based on the selected category
    private ArrayList<Product> getProductList(ArrayList<Product> productList, String dropdownOption) {
        ArrayList<Product> selectedProductList = new ArrayList<>();
        switch (dropdownOption) {
            case "All":
                selectedProductList = productList;
                break;
            case "Electronic":
                for (Product product : productList) {
                    if (product.getProductType().equals("Electronics")) {
                        selectedProductList.add(product);
                    }
                }
                break;
            case "Clothing":
                for (Product product : productList) {
                    if (product.getProductType().equals("Clothing")) {
                        selectedProductList.add(product);
                    }
                }
                break;
        }
        return selectedProductList;
    }
}
