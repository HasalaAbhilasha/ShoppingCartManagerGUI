
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
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Westminster Shopping Center");
        mainFrame.setSize(800, 900);

        mainFrame.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel(new GridLayout(2, 1));
        JPanel panel2 = new JPanel(new GridLayout(1, 3));

        JPanel panel3 = new JPanel(new GridBagLayout()); // Centered GridBagLayout
        JPanel panel4 = new JPanel(new GridBagLayout()); // Centered GridBagLayout
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Centered GridBagLayout

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Select Product Category");

        JLabel displayLabel1 = new JLabel("Selected Product Details");

        int marginSize = 10;
        EmptyBorder emptyBorder = new EmptyBorder(marginSize, marginSize*2, marginSize, 0);

        JLabel productIDLabel = new JLabel();
        JLabel categoryLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel noItemsLabel = new JLabel();
        JLabel miscLabel1 = new JLabel();
        JLabel miscLabel2 = new JLabel();

        displayLabel1.setBorder(emptyBorder);
        productIDLabel.setBorder(emptyBorder);
        categoryLabel.setBorder(emptyBorder);
        nameLabel.setBorder(emptyBorder);
        noItemsLabel.setBorder(emptyBorder);
        miscLabel1.setBorder(emptyBorder);
        miscLabel2.setBorder(emptyBorder);



        String[] s1 = { "All", "Electronic", "Clothing"};
        JComboBox<String> dropdown = new JComboBox<>(s1);
        dropdown.setPrototypeDisplayValue("XXXXXXXXXXXXXXXX");

        System.out.println("before actionlistner - "+dropdown.getSelectedItem());

        selectedValue = (String) dropdown.getSelectedItem();

        JButton shoppingCartButton = new JButton("Shopping Cart");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel3.add(label1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel4.add(dropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel5.add(shoppingCartButton, gbc);

        panel2.add(panel3);
        panel2.add(panel4);
        panel2.add(panel5);

//        JTable table;
        table = createJTable(getProductDropdown(WestminsterShoppingManager.shoppingCart.getProductList(), selectedValue));
        JScrollPane scrollPane = new JScrollPane(table);

        panel1.add(panel2);
        panel1.add(scrollPane);
//        System.out.println("just before actionlistner - "+dropdown.getSelectedItem());
        dropdown.addActionListener(e -> {
            selectedValue = (String) dropdown.getSelectedItem();
            System.out.println("Selected item: " + selectedValue);

            panel1.removeAll();

//            JTable table;
            table = createJTable(getProductDropdown(WestminsterShoppingManager.shoppingCart.getProductList(), selectedValue));
//             scrollPane = new JScrollPane(table);

            panel1.add(table);
            panel1.add(scrollPane);


        });

//        panel1.add(scrollPane);

        mainFrame.add(panel1);

//        System.out.println("after actionlistner - "+dropdown.getSelectedItem());
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the selected product from the row
//                        Product selectedProduct = getProductFromSelectedRow(selectedRow, WestminsterShoppingManger.shoppingCart.getProductList());
                        Product selectedProduct = WestminsterShoppingManager.shoppingCart.getProductList().get(selectedRow);

                        productIDLabel.setText("Product ID - "+ selectedProduct.getProductId());
                        categoryLabel.setText("Category - "+ selectedProduct.getObjecttype());
                        nameLabel.setText("Name - " + selectedProduct.getProductName());
                        noItemsLabel.setText("Items available - "+selectedProduct.getNumberofItems());

                        switch (selectedProduct.getObjecttype()){
                            case "electronics":
                                miscLabel1.setText("Brand - "+ selectedProduct.getBrand());
                                displayPanel.add(miscLabel1);
                                miscLabel2.setText("Warrenty Period - "+ selectedProduct.getWarrantyPeriod());
                                displayPanel.add(miscLabel1);
                                break;
                            case "clothing":
                                miscLabel1.setText("Size - "+ selectedProduct.getSize());
                                displayPanel.add(miscLabel1);
                                miscLabel1.setText("Color - " + selectedProduct.getColor());
                                displayPanel.add(miscLabel2);
                                break;

                        }
//                        displayPanel.setBackground(Color.green);
                        displayPanel.add(displayLabel1);
                        displayPanel.add(productIDLabel);
                        displayPanel.add(categoryLabel);
                        displayPanel.add(nameLabel);
                        displayPanel.add(miscLabel1);
                        displayPanel.add(miscLabel2);
                        displayPanel.add(noItemsLabel);

                    }
                }
            }
        });


        mainFrame.add(displayPanel);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static JTable createJTable(ArrayList<Product> productList){
        String[] columnNames = {"Product ID" , "Name", "Category", "Price($)", "Info"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0);

        for (Product product : productList) {
            switch (product.getObjecttype()){

                case "electronics":
                    Object[] rowDataElectronic = {product.getProductId(), product.getProductName(),product.getObjecttype(), product.getPrice(), product.getBrand() + ", "+product.getWarrantyPeriod() +" months warrenty"};
                    model.addRow(rowDataElectronic);
                    break;
                case "clothing":
                    Object[] rowDataClothing = {product.getProductId(), product.getProductName(),product.getObjecttype(), product.getPrice(), product.getSize()+ ", "+ product.getColor()};
                    model.addRow(rowDataClothing);
                    break;
            }
        }
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);

        return  table;
    }
//    private static Product getProductFromSelectedRow(int selectedRow, ArrayList<Product> productList) {
//        // You need to map the row index to the actual index in your product list
//        // Assuming the JTable and productList have the same order
//        return productList.get(selectedRow);
//    }

    private static ArrayList<Product> getProductDropdown (ArrayList<Product> productList, String selectedType ) {
        ArrayList<Product> newproductList = new ArrayList<>();
        switch (selectedType){
            case "All":
                newproductList = productList;
                break;
            case "Electronic":
                for (Product product: productList) {
                    if (product.getObjecttype().equals("electronics")){
                        newproductList.add(product);
                    }
                }
                break;
            case "Clothing":
                for (Product product: productList) {
                    if (product.getObjecttype().equals("clothing")){
                        newproductList.add(product);
                    }
                }
                break;
        }
        System.out.println(selectedType);
        return newproductList;
    }
}


