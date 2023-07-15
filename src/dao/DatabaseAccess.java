package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class DatabaseAccess {

    //Later to edit for UpdateDAO
    public static void getAppointmentId(int appointmentId) throws SQLException {
        String sql = "SELECT Appointment_ID FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
    }

    public static ObservableList <String> getContactNames() throws SQLException {
        String sql = "SELECT Contact_Name FROM CONTACTS";
        ObservableList<String> contacts = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String contactName = rs.getString("Contact_Name");
            contacts.add(contactName);
        }
        return contacts;
    }

    public static ObservableList <String> getUsers() throws SQLException {
        String sql = "SELECT User_ID FROM USERS";
        ObservableList<String> users = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String userId = rs.getString("User_ID");
            users.add(userId);
        }
        return users;
    }

    public static ObservableList <String> getCustomerIds() throws SQLException {
        String sql = "SELECT Customer_ID FROM CUSTOMERS";
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String customerId = rs.getString("Customer_ID");
            customerIds.add(customerId);
        }
        return customerIds;
    }

    public static int getContactId(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM CONTACTS WHERE Contact_Name = ?" ;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        int contactId = 0;
        while(rs.next())
        {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }
}

