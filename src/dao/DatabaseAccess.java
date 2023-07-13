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

    public static int insert(String fruitName, int colorId) throws SQLException {
        String sql = "INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, colorId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int fruitId, String fruitName) throws SQLException {
        String sql = "UPDATE FRUITS SET Fruit_Name = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int fruitId) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select(int colorId) throws SQLException {
        String sql = "SELECT * FROM FRUITS WHERE Color_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, colorId);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            int fruitId = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            int colorIdFK = rs.getInt("Color_ID");
            System.out.print(fruitId + " | ");
            System.out.print(fruitName + " | ");
            System.out.print(colorIdFK  + "\n");
        }
    }

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

    public static ObservableList <Integer> getUsersNull() throws SQLException {
        String sql = "SELECT User_ID FROM USERS";
        ObservableList<Integer> users = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            int userId = rs.getInt("User_ID");
            users.add(userId);
        }
        return users;
    }

    public static String getContactName(int contactId) throws SQLException {
        String sql = "SELECT Contact_Name FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        String contactName = "";
        while(rs.next())
        {
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
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

    public static ObservableList <Contact> getContacts() throws SQLException {
        String sql = "SELECT Contact_Name FROM CONTACTS";
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Contact contact = new Contact(rs.getString("Column_Name"));
            contacts.add(contact);
        }
        return contacts;
    }

    //TEST - DELETE LATER
    public static void selectAppointment() throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            int appointmentId = rs.getInt("Appointment_ID");
            System.out.print(appointmentId);
        }
    }


}

