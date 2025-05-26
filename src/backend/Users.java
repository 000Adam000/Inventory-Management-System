package backend;

import javax.swing.JTable;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Users class - Handles all database operations for user management
 * This is a pure data access class that doesn't extend any UI components
 */
public class Users {
    // User properties - these hold the data for database operations
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String role;
    private Date birthdate;
    private String mobileNumber;
    private String address;
    private String gender;

    // Database connection components
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    /**
     * Constructor - Establishes database connection when Users object is created
     */
    public Users() {
        try {
            // Connect to MySQL database - make sure your database exists and credentials are correct
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory management system", "root", "");
        } catch (SQLException e) {
            // Show error if connection fails
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), 
                                        "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new user to the database
     * Uses all the current property values to insert a new record
     */
    public void addUser() {
        try {
            // SQL INSERT statement - note the order matches our setter calls
            String sql = "INSERT INTO `user`(`Name`, `Username`, `Birthdate`, `Mobile Number`, `Email`, `Gender`, `Password`, `Address`, `Role Selection`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            
            // Set parameters using current object properties
            pst.setString(1, name);
            pst.setString(2, username);
            // Format date for MySQL (YYYY-MM-DD format)
            pst.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(birthdate));
            pst.setString(4, mobileNumber);
            pst.setString(5, email);
            pst.setString(6, gender);
            pst.setString(7, password);
            pst.setString(8, address);
            pst.setString(9, role);
            
            // Execute the insert
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            // Show detailed error message for debugging
            JOptionPane.showMessageDialog(null, "Error adding user: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Always close PreparedStatement to prevent memory leaks
            closePreparedStatement();
        }
    }

    /**
     * Updates an existing user in the database
     * Uses the id property to identify which user to update
     */
    public void updateUser() {
        try {
            // SQL UPDATE statement - updates all fields for the user with matching id
            String sql = "UPDATE `user` SET `Name`=?, `Username`=?, `Birthdate`=?, `Mobile Number`=?, `Email`=?, `Gender`=?, `Password`=?, `Address`=?, `Role Selection`=? WHERE `ID`=?";
            pst = con.prepareStatement(sql);
            
            // Set all the new values
            pst.setString(1, name);
            pst.setString(2, username);
            pst.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(birthdate));
            pst.setString(4, mobileNumber);
            pst.setString(5, email);
            pst.setString(6, gender);
            pst.setString(7, password);
            pst.setString(8, address);
            pst.setString(9, role);
            // The WHERE condition - which user to update
            pst.setString(10, id);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No user found with the specified ID", "Update Failed", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating user: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closePreparedStatement();
        }
    }

    /**
     * Deletes a user from the database
     * Uses the id property to identify which user to delete
     */
    public void deleteUser() {
        try {
            // Confirm deletion with user
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete this user?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm != JOptionPane.YES_OPTION) {
                return; // User cancelled deletion
            }
            
            String sql = "DELETE FROM `user` WHERE `ID`=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, id);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User Deleted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No user found with the specified ID", "Delete Failed", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closePreparedStatement();
        }
    }

    /**
     * Refreshes the JTable with current data from database
     * This method populates the table with fresh data from the users table
     * @param table
     */
    public void refreshTable(JTable table) {
        try {
            String sql = "SELECT `ID`, `Name`, `Username`, `Birthdate`, `Mobile Number`, `Email`, `Gender`, `Password`, `Address`, `Role Selection` FROM `user`";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            // Use DbUtils to convert ResultSet to TableModel - this automatically populates the table
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading user data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closePreparedStatement();
        }
    }

    /**
     * Helper method to safely close PreparedStatement
     * This prevents memory leaks and database connection issues
     */
    private void closePreparedStatement() {
        try {
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
        }
    }

    /**
     * Closes database connection when the Users object is no longer needed
     * Call this method when you're done with database operations
     */
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
        }
    }

    // Setter methods - these set the properties that will be used in database operations
    public void setId(String id) { 
        this.id = id; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public void setRole(String role) { 
        this.role = role; 
    }
    
    public void setBirthdate(Date birthdate) { 
        this.birthdate = birthdate; 
    }
    
    public void setMobileNumber(String mobileNumber) { 
        this.mobileNumber = mobileNumber; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }
    
    public void setGender(String gender) { 
        this.gender = gender; 
    }

    // Getter methods - these retrieve the current property values
    public String getId() { 
        return id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public String getRole() { 
        return role; 
    }
    
    public Date getBirthdate() { 
        return birthdate; 
    }
    
    public String getMobileNumber() { 
        return mobileNumber; 
    }
    
    public String getAddress() { 
        return address; 
    }
    
    public String getGender() { 
        return gender; 
    }
}