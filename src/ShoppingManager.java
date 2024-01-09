import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingManager extends JFrame {

    private JFrame frame;
    private JComboBox<String> comboBox;
    private JTable table;
    private JButton shoppingCartButton;
    private JButton addToShoppingCartButton;

    public void createGUI() {
        frame = new JFrame("Westminster Shopping Center");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a sample array for JComboBox items
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
        comboBox = new JComboBox<>(items);

        // Create a sample JTable (replace this with your actual table model)
        Object[][] data = {
                {"Product 1", 10.0},
                {"Product 2", 20.0},
                {"Product 3", 15.0},
        };
        String[] columnNames = {"Product Name", "Price"};
        table = new JTable(data, columnNames);

        // Create buttons
        shoppingCartButton = new JButton("View Shopping Cart");
        addToShoppingCartButton = new JButton("Add to Shopping Cart");

        // Add ActionListener to the "Add to Shopping Cart" button
        addToShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your logic for adding the selected item to the shopping cart
                System.out.println("Item added to shopping cart: " + comboBox.getSelectedItem());
            }
        });
// Add ActionListener to the "shoppingCart" button
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                shoppingCart();
            }
        });

        // Set layout manager for the frame (using BorderLayout in this example)
        frame.setLayout(new BorderLayout());

        // Add components to the frame
        frame.add(comboBox, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(shoppingCartButton, BorderLayout.SOUTH);
        frame.add(addToShoppingCartButton, BorderLayout.EAST);

        frame.setVisible(true);
    }

    public void shoppingCart() {
        frame = new JFrame("Westminster Shopping Center");
        frame.setSize(300, 300);
        frame.setBackground(Color.blue);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // Create buttons
        addToShoppingCartButton = new JButton("Back to Shopping Center");

        // Add ActionListener to the "Add to Shopping Cart" button
        addToShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                createGUI();
            }
        });

        // Set layout manager for the frame (using BorderLayout in this example)
        frame.setLayout(new BorderLayout());


        frame.add(addToShoppingCartButton, BorderLayout.EAST);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShoppingManager().createGUI();
            }
        });
    }
}
