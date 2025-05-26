package backend;

import java.sql.*;
import java.util.Date;
import javax.swing.JOptionPane;

public class Import {
    // Database connection
    private Connection con;

    // Fields corresponding to database columns (excluding Page ID since it's auto-incremented)
    private String ministry;
    private String office;
    private String product;
    private double quantity;
    private String unit;
    private double price;
    private Date dateOfOrder;
    private Date dateOfSale;
    private String recipient;
    private String note;

    // Constructor to establish database connection
    public Import() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory management system", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), 
                                          "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Setter methods for each field
    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Method to add a new record to the database
    public void addImport() {
        PreparedStatement pst = null;
        try {
            String sql = "INSERT INTO `accounting 71 a` (`Ministry`, `Office`, `Product`, `Quantity`, `Unit`, `Price`, " +
                         "`Date of Order`, `Date of Sale`, `Current Date`, `Recipient`, `Note`) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, ministry);
            pst.setString(2, office);
            pst.setString(3, product);
            pst.setDouble(4, quantity);
            pst.setString(5, unit);
            pst.setDouble(6, price);
            pst.setDate(7, new java.sql.Date(dateOfOrder.getTime()));
            pst.setDate(8, new java.sql.Date(dateOfSale.getTime()));
            // Current Date is set to NOW() in the SQL query, no parameter needed
            pst.setString(9, recipient);
            pst.setString(10, note);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Import added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding import: " + e.getMessage(), 
                                          "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                // Ignore closing errors
            }
        }
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }
}