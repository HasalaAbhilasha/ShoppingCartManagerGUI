import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ShoppingManager {

    private static JTable table;
    private static JTextArea detailsTextArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Westminster Shopping Centre");
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the GUI components
        JPanel contentPanel = new JPanel();
        frame.setContentPane(contentPanel);

        // Add a label to display text
        JLabel label1 = new JLabel("Select Product Category");
        label1.setFont(new Font("TimesRoman", Font.PLAIN, 17));
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        contentPanel.add(label1);

        // Create the dropdown menu
        String[] categories = {"All", "Electronics", "Clothing"};
        JComboBox<String> dropdownMenu = new JComboBox<>(categories);

        // Add listener for selection change
        dropdownMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected category
                String selectedCategory = (String) dropdownMenu.getSelectedItem();

                // Update the table based on the selected category
                updateTable(selectedCategory);
            }
        });

        // Add dropdown menu to the panel
        contentPanel.add(dropdownMenu);

        // Create a table with sample data
        String[] columnNames = {"Product Name", "Price", "Category"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane);

        // Create a text area to display details
        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setLineWrap(true);
        detailsTextArea.setWrapStyleWord(true);
        JScrollPane detailsScrollPane = new JScrollPane(detailsTextArea);
        detailsScrollPane.setPreferredSize(new Dimension(300, 100));
        contentPanel.add(detailsScrollPane);

        // Add a button to open the shopping cart window
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartWindow();
            }
        });
        contentPanel.add(viewCartButton);

        // Add a button to add the selected item to the cart
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row and column
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String productName = (String) table.getValueAt(selectedRow, 0);
                    double price = (double) table.getValueAt(selectedRow, 1);

                    // Add your logic to add the selected item to the shopping cart
                    System.out.println("Item added to cart: " + productName + " - $" + price);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a product to add to the cart.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPanel.add(addToCartButton);

        // Add a selection listener to the table to display details
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                displayProductDetails();
            }
        });

        // Make the frame visible
        frame.setVisible(true);

        // Generate random items
        generateRandomItems();
        // Update the table initially with all categories
        updateTable("All");
    }

    // Function to open the shopping cart window
    private static void openShoppingCartWindow() {
        JFrame shoppingCartFrame = new JFrame(" ".repeat(75) + "Shopping Cart " + "".repeat(50));
        shoppingCartFrame.setSize(600, 500);
        shoppingCartFrame.setLocationRelativeTo(null);

        // Create a panel for the shopping cart content
        JPanel shoppingCartPanel = new JPanel();
        shoppingCartFrame.setContentPane(shoppingCartPanel);

        // Add shopping cart content, labels, buttons, etc. as needed

        // Make the shopping cart frame visible
        shoppingCartFrame.setVisible(true);
    }

    // Function to update the table based on the selected category
    private static void updateTable(String selectedCategory) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        // Add rows based on the selected category or show all categories
        for (int i = 0; i < 10; i++) {
            String productName = "Product " + (i + 1);
            double price = new Random().nextDouble() * 100.0;
            String category = i % 2 == 0 ? "Electronics" : "Clothing";

            if (selectedCategory.equals("All") || selectedCategory.equals(category)) {
                model.addRow(new Object[]{productName, price, category});
            }
        }
    }

    // Function to generate random items initially
    private static void generateRandomItems() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < 10; i++) {
            String productName = "Product " + (i + 1);
            double price = new Random().nextDouble() * 100.0;
            String category = i % 2 == 0 ? "Electronics" : "Clothing";
            model.addRow(new Object[]{productName, price, category});
        }
    }

    // Function to display product details when an item in the table is clicked
    private static void displayProductDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String productName = (String) table.getValueAt(selectedRow, 0);
            double price = (double) table.getValueAt(selectedRow, 1);
            String category = (String) table.getValueAt(selectedRow, 2);

            String details = "Product Name: " + productName + "\nPrice: $" + price + "\nCategory: " + category;
            detailsTextArea.setText(details);
        }
    }
}
