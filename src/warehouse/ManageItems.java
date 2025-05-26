package warehouse;

import backend.ItemManager;
import backend.Items;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageItems extends javax.swing.JInternalFrame {

    private ItemManager itemManager;
    private JTextField itemNameField;
    private JComboBox<String> categoryBox;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextArea descriptionArea;
    private JTable table;
    private JButton saveButton, updateButton, deleteButton;

    public ManageItems() {
        initComponents();
        itemManager = new Items();
        showData();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTableClick();
            }
        });
    }

    private void initComponents() {
        itemNameField = new JTextField();
        categoryBox = new JComboBox<>(new String[]{"Select a category", "Electronics", "Clothing", "Food"});
        quantityField = new JTextField();
        priceField = new JTextField();
        descriptionArea = new JTextArea();
        table = new JTable();
        saveButton = new JButton("Save");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Layout setup (simplified for brevity)
        setLayout(null);
        itemNameField.setBounds(10, 10, 150, 25);
        categoryBox.setBounds(10, 40, 150, 25);
        quantityField.setBounds(10, 70, 150, 25);
        priceField.setBounds(10, 100, 150, 25);
        descriptionArea.setBounds(10, 130, 150, 50);
        saveButton.setBounds(10, 190, 80, 25);
        updateButton.setBounds(100, 190, 80, 25);
        deleteButton.setBounds(190, 190, 80, 25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300, 10, 400, 300);

        add(itemNameField);
        add(categoryBox);
        add(quantityField);
        add(priceField);
        add(descriptionArea);
        add(saveButton);
        add(updateButton);
        add(deleteButton);
        add(scrollPane);

        saveButton.addActionListener(evt -> saveButtonActionPerformed());
        updateButton.addActionListener(evt -> updateButtonActionPerformed());
        deleteButton.addActionListener(evt -> deleteButtonActionPerformed());

        setSize(720, 400);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Manage Items");
    }

    void showData() {
        try {
            ResultSet rs = itemManager.getItemData();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    private void handleTableClick() {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        itemNameField.setText(dtm.getValueAt(selectedRow, 1).toString());
        categoryBox.setSelectedItem(dtm.getValueAt(selectedRow, 2).toString());
        quantityField.setText(dtm.getValueAt(selectedRow, 3).toString());
        priceField.setText(dtm.getValueAt(selectedRow, 4).toString());
        descriptionArea.setText(dtm.getValueAt(selectedRow, 5).toString());
    }

    private void saveButtonActionPerformed() {
        String itemName = itemNameField.getText().trim();
        String category = categoryBox.getSelectedItem().toString();
        String quantityText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (itemName.isEmpty() || category.equals("Select a category") || quantityText.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        double price;
        try {
            quantity = Integer.parseInt(quantityText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantity and Price must be numeric!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        itemManager.addItem(itemName, category, quantity, price, description);
        showData();
        clearFields();
    }

    private void updateButtonActionPerformed() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select an item to update!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String itemName = itemNameField.getText().trim();
        String category = categoryBox.getSelectedItem().toString();
        String quantityText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (itemName.isEmpty() || category.equals("Select a category") || quantityText.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        double price;
        try {
            quantity = Integer.parseInt(quantityText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantity and Price must be numeric!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        String itemId = dtm.getValueAt(selectedRow, 0).toString();

        itemManager.updateItem(itemId, itemName, category, quantity, price, description);
        showData();
        clearFields();
    }

    private void deleteButtonActionPerformed() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select an item to delete!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        String itemId = dtm.getValueAt(selectedRow, 0).toString();

        itemManager.deleteItem(itemId);
        showData();
        clearFields();
    }

    private void clearFields() {
        itemNameField.setText("");
        categoryBox.setSelectedIndex(0);
        quantityField.setText("");
        priceField.setText("");
        descriptionArea.setText("");
    }
}