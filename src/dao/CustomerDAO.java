package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerDAO {


    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT customers.*, countries.Country FROM customers JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID  JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionIdFK = rs.getInt("Division_ID");
            String country = rs.getString("Country");
            Customer customer = new Customer(customerId, name, address, postalCode, phone, divisionIdFK,country);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    //The code we want for all customers
    //SELECT customers.*, countries.Country FROM customers JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID  JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID ASC;

    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionIdFK) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,  address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionIdFK);
        ps.executeUpdate();
    }

    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionIdFK, int customerId) throws SQLException {
        String sql = "UPDATE CUSTOMERS set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?," +
                "                Division_ID = ?" +
                "                where Customer_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,  address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionIdFK);
        ps.setInt(6, customerId);
        ps.executeUpdate();
    }



    public static int checkCustomerAppointments(int customerId) throws SQLException
    {
        String sql = "SELECT COUNT(appointment_id) AS RowCount from APPOINTMENTS where customer_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("RowCount");
        return count;
    }


    public static void deleteCustomer(int customerId) throws SQLException {
        //NEED A CHECK TO SEE IF CUSTOMER EXISTS
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }
}



