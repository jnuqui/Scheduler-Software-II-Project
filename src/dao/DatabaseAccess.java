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

    public static int getMatchingCustomerId(String customerId) throws SQLException {

        String[] customerIdList = getCustomerIds().toArray(new String[0]);
        int customerIdIndex = 0;
        for (int i = 0; i <= customerIdList.length; i++) {
            if (customerId.equals(customerIdList[i]))
            {
                customerIdIndex = i;
                break;
            }
        }
        return customerIdIndex;
    }

    public static ObservableList <String> getUserIds() throws SQLException {
        String sql = "SELECT User_ID FROM USERS";
        ObservableList<String> userIds = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String userId = rs.getString("User_ID");
            userIds.add(userId);
        }
        return userIds;
    }

    public static int getMatchingUserId(String userId) throws SQLException {

        String[] userIdList = getUserIds().toArray(new String[0]);
        int userIdIndex = 0;
        for (int i = 0; i <= userIdList.length; i++) {
            if (userId.equals(userIdList[i]))
            {
                userIdIndex = i;
                break;
            }
        }
        return userIdIndex;
    }
}

