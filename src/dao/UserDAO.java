package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {

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
