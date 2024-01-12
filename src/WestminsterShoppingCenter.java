import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class WestminsterShoppingCenter extends WestminsterShoppingManager {

    private static JTable table;
    private static String selectedValue;
    private static JButton addToCartButton;
    private static JButton shoppingCartButton; // New button for shopping cart

    private static ShoppingCart shoppingCart = new ShoppingCart(); // Assume ShoppingCart class is available


    public static void main(String[] args) {
        SwingUtilities.invokeLater(WestminsterShoppingCenter::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the main JFrame
        JFrame mainFrame = new JFrame("Westminster Shopping Center");
        mainFrame.setSize(800, 500);

        // Create panels
        JPanel panel1 = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new GridLayout(1, 4));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        // Create labels
        JLabel label1 = new JLabel("Select Product Category");
        JLabel displayLabel1 = new JLabel("Selected Product Details");

        // Create empty border
        int marginSize = 10;
        EmptyBorder emptyBorder = new EmptyBorder(marginSize, marginSize * 2, marginSize, 0);

        // Create labels for displaying product details
        JLabel productIDLabel = new JLabel();
        JLabel categoryLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel noItemsLabel = new JLabel();
        JLabel miscLabel1 = new JLabel();
        JLabel miscLabel2 = new JLabel();

        // Set empty borders for labels
        displayLabel1.setBorder(emptyBorder);
        productIDLabel.setBorder(emptyBorder);
        categoryLabel.setBorder(emptyBorder);
        nameLabel.setBorder(emptyBorder);
        noItemsLabel.setBorder(emptyBorder);
        miscLabel1.setBorder(emptyBorder);
        miscLabel2.setBorder(emptyBorder);

        // Create dropdown menu
        String[] categories = {"All", "Electronic", "Clothing"};
        JComboBox<String> dropdown = new JComboBox<>(categories);
        dropdown.setPrototypeDisplayValue("XXXXXXXXXXXXXXXX");

        // Initialize selected value
        selectedValue = (String) dropdown.getSelectedItem();

        // Create buttons with smaller size
        addToCartButton = new JButton("Add to Cart");
        shoppingCartButton = new JButton("Shopping Cart");

        Dimension buttonSize = new Dimension(120, 30);
        addToCartButton.setPreferredSize(buttonSize);
        shoppingCartButton.setPreferredSize(buttonSize);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addToCartButton);
        buttonPanel.add(shoppingCartButton);

        // Add components to panels
        panel3.add(label1);
        panel4.add(dropdown);
        panel5.add(addToCartButton);
        panel6.add(shoppingCartButton);

        // Create table and scroll pane
        table = createJTable(getProductDropdown(WestminsterShoppingManager.shoppingCart.getProductList(), selectedValue));
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to panels
        panel1.add(panel2, BorderLayout.PAGE_START);
        panel1.add(scrollPane, BorderLayout.CENTER);
        panel1.add(displayPanel, BorderLayout.PAGE_END);

        // Add action listener for dropdown menu
        dropdown.addActionListener(e -> {
            selectedValue = (String) dropdown.getSelectedItem();

            // Clear the existing table and display panel
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            displayPanel.removeAll();

            // Add the new filtered products to the table
            ArrayList<Product> filteredProducts = getProductDropdown(WestminsterShoppingManager.shoppingCart.getProductList(), selectedValue);
            for (Product product : filteredProducts) {
                switch (product.getObjecttype()) {
                    case "electronics":
                        Object[] rowDataElectronic = {product.getProductId(), product.getProductName(), product.getObjecttype(), product.getPrice(), product.getBrand() + ", " + product.getWarrantyPeriod() + " months warranty"};
                        model.addRow(rowDataElectronic);
                        break;
                    case "clothing":
                        Object[] rowDataClothing = {product.getProductId(), product.getProductName(), product.getObjecttype(), product.getPrice(), product.getSize() + ", " + product.getColor()};
                        model.addRow(rowDataClothing);
                        break;
                }
            }

            // Refresh the display panel
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Add table selection listener
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Product selectedProduct = WestminsterShoppingManager.shoppingCart.getProductList().get(selectedRow);

                        // Display product details in the labels
                        productIDLabel.setText("Product ID: " + selectedProduct.getProductId());
                        categoryLabel.setText("Category: " + selectedProduct.getObjecttype());
                        nameLabel.setText("Name: " + selectedProduct.getProductName());

                        // Add labels to the display panel
                        displayPanel.add(displayLabel1);
                        displayPanel.add(productIDLabel);
                        displayPanel.add(categoryLabel);
                        displayPanel.add(nameLabel);

                        // Refresh the display panel
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    }
                }
            }
        });

        // Add action listener for "Add to Cart" button
        addToCartButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Product selectedProduct = WestminsterShoppingManager.shoppingCart.getProductList().get(selectedRow);

                // Add the selected product to the shopping cart
                shoppingCart.addToCart(selectedProduct);

                // Optional: Provide user feedback
                JOptionPane.showMessageDialog(mainFrame, "Item added to the shopping cart.");
            }
        });

        // Add action listener for "Shopping Cart" button
        shoppingCartButton.addActionListener(e -> {
            openShoppingCartFrame(); // Open the shopping cart JFrame when the button is clicked
        });

        // Add panels to the main frame
        panel2.add(panel3);
        panel2.add(panel4);
        panel2.add(panel5);
        panel2.add(panel6);

        mainFrame.add(panel1, BorderLayout.CENTER);

        // Set frame visibility and default close operation
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to create JTable
    public static JTable createJTable(ArrayList<Product> productList) {
        String[] columnNames = {"Product ID", "Name", "Category", "Price($)","Quantity", "Info"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Product product : productList) {
            switch (product.getObjecttype()) {
                case "electronics":
                    Object[] rowDataElectronic = {product.getProductId(), product.getProductName(), product.getObjecttype(), product.getPrice(), product.getBrand() + ", " + product.getWarrantyPeriod() + " months warranty"};
                    model.addRow(rowDataElectronic);
                    break;
                case "clothing":
                    Object[] rowDataClothing = {product.getProductId(), product.getProductName(), product.getObjecttype(), product.getPrice(), product.getSize() + ", " + product.getColor()};
                    model.addRow(rowDataClothing);
                    break;
            }
        }
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);

        return table;
    }

    // Method to filter products based on category
    private static ArrayList<Product> getProductDropdown(ArrayList<Product> productList, String selectedType) {
        ArrayList<Product> newproductList = new ArrayList<>();
        switch (selectedType) {
            case "All":
                newproductList = productList;
                break;
            case "Electronic":
                for (Product product : productList) {
                    if (product.getObjecttype().equals("electronics")) {
                        newproductList.add(product);
                    }
                }
                break;
            case "Clothing":
                for (Product product : productList) {
                    if (product.getObjecttype().equals("clothing")) {
                        newproductList.add(product);
                    }
                }
                break;
        }
        System.out.println(selectedType);
        return newproductList;
    }

    private static void openShoppingCartFrame() {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(600, 400);

        // Create a table to display the selected items in the shopping cart
        JTable cartTable = createJTable(shoppingCart.getSelectedItems());
        JScrollPane cartScrollPane = new JScrollPane(cartTable);

        // Create a label to display the total price
        JLabel totalLabel = new JLabel("Total Price: $" + shoppingCart.getTotalPrice());

        // Create a button to remove selected products
        JButton removeButton = new JButton("Remove from Cart"); // Updated button text
        removeButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                // Remove the selected product from the shopping cart
                shoppingCart.getSelectedItems().remove(selectedRow);

                // Update the table and total price
                ((DefaultTableModel) cartTable.getModel()).removeRow(selectedRow);
                totalLabel.setText("Total Price: $" + shoppingCart.getTotalPrice());
            }
        });

        // Create a panel to hold the components
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        cartPanel.add(totalLabel, BorderLayout.SOUTH);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(removeButton);

        // Add the cart panel and button panel to the shopping cart frame
        shoppingCartFrame.add(cartPanel, BorderLayout.CENTER);
        shoppingCartFrame.add(buttonPanel, BorderLayout.NORTH);

        shoppingCartFrame.setVisible(true);
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }



}
