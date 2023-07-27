package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The Abstract Class for AppointmentDAO. This handles database queries related to appointments. */
public abstract class ContactDAO {

    /** This method uses a given String contactName to retrieve its matching id in the database.
     *  @param contactName Obtained from a contactComboBox. This is used to get the matching contactId of the
     *                      contact name.
     *  @return Returns the int value of the matching id for a contact */
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

    /** This is used to populate combo boxes that require contact names.
     * @return Returns an ObservableList of Strings of contact names. */
    public static ObservableList<String> getContactNames() throws SQLException {
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
}
