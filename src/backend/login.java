package backend;

import java.sql.*;

public class login {
    private Connection con;
    private String username;
    private String password;
    private String role;

    // Constructor to establish database connection
    public login() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory management system", "root", "");
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    // Method to verify credentials and populate user data
    public boolean verifyCredentials(String username, String password, String role) {
        if (con == null) {
            System.out.println("No database connection");
            return false;
        }
        try {
            String sql = "SELECT * FROM `user` WHERE `Username` = ? AND `Password` = ? AND `Role Selection` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Set user data using setters
                setUsername(rs.getString("Username"));
                setPassword(rs.getString("Password"));
                setRole(rs.getString("Role Selection"));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error verifying credentials: " + e.getMessage());
            return false;
        }
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Setter methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}