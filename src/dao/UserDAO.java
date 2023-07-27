package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class for UserDAO. This handles database queries related to Users. */
public abstract class UserDAO {

    /** A query is made to the database to fill an ObservableList of String objects with the results. This is used to
     *  populate the userId combo boxes on Add and Update Appointment views.
     *  @return Returns an ObservableList of Strings, containing User ID's.
     *   */
    public static ObservableList<String> getUsers() throws SQLException {
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

    /** This method helps populate combo boxes that have userId's. A query is made to the database for a list of the users.
     *  @return Returns an ObservableList of Strings of userId's.
     *   */
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

    /** This is used to bring the userId to the Update Customer view when a user selects a customer to update from the
     *  TableView.
     *  @param userId Obtained from the user's selection of a customer to update from the TableView. It then
     *                    matches it's location in the array and ultimately determines the value in the combo box.
     *  @return Returns the index location of a userId of the userId combo box.
     *   */
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
