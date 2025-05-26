package backend;

import java.sql.*;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;

public class View {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    // Constructor to establish database connection
    public View() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory management system", "root", "");
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), 
                                          "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to refresh the table with all records from `accounting 71 a`
    public void refreshTable(JTable table) {
        try {
            String sql = "SELECT `Ministry`, `Office`, `Page ID`, `Product`, `Quantity`, `Unit`, `Price`, " +
                         "`Date of Order`, `Date of Sale`, `Current Date`, `Recipient`, `Note` " +
                         "FROM `accounting 71 a`";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), 
                                          "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closePreparedStatement();
        }
    }

    // Method to search records by ID and/or Product Name
    public void searchByIdAndProduct(JTable table, String id, String product) {
        try {
            String sql = "SELECT `Ministry`, `Office`, `Page ID`, `Product`, `Quantity`, `Unit`, `Price`, " +
                         "`Date of Order`, `Date of Sale`, `Current Date`, `Recipient`, `Note` " +
                         "FROM `accounting 71 a` WHERE 1=1";
            if (!id.isEmpty()) {
                sql += " AND `Page ID` = ?";
            }
            if (!product.isEmpty()) {
                sql += " AND `Product` LIKE ?";
            }
            pst = con.prepareStatement(sql);
            int paramIndex = 1;
            if (!id.isEmpty()) {
                pst.setString(paramIndex++, id);
            }
            if (!product.isEmpty()) {
                pst.setString(paramIndex, "%" + product + "%");
            }
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching data: " + e.getMessage(), 
                                          "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closePreparedStatement();
        }
    }

    // Helper method to close PreparedStatement and ResultSet
    private void closePreparedStatement() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
        } catch (SQLException e) {
            // Ignore
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

    // Getter for connection (used by ViewBox queries)
    public Connection getConnection() {
        return con;
    }
}