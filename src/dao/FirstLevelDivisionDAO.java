package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class for FirstLevelDivisionDAO. This handles database queries related to First Level Divisions. */
public abstract class FirstLevelDivisionDAO
{
    static ObservableList<FirstLevelDivision> allFLD = FXCollections.observableArrayList();
    static ObservableList<String> allFLDStrings = FXCollections.observableArrayList();

    /** A query is made to the database to fill an ObservableList and the results are used to create First Level
     *  Division objects, which are then loaded into the ObservableList. This is used to populate First Level Division
     *  combo boxes.
     *  @param countryIdFK The int used in the database query.
     *  @return Returns an ObservableList of First Level Divisions.
     *   */
    public static ObservableList<FirstLevelDivision> getAllFLD(int countryIdFK) throws SQLException {

        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE country_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryIdFK);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            FirstLevelDivision fld = new FirstLevelDivision(divisionId, divisionName, countryIdFK);
            allFLD.add(fld);
        }
        return allFLD;
    }

    /** The method that is called to clear the ObservableLists. These clear both ObservableLists of this class to prevent
     *  the possibility of duplicating the lists and increasing their sizes. */
    public static void clearLists()
    {
        allFLD.clear();
        allFLDStrings.clear();
    }

    /** The method used to get the divisionId for the INSERT statement for customers.
     *  @param division The String comes for the user's selection of the First Level Division combo box. It is used to
     *                  query the database for the matching divisionId.
     *  @return Returns int of the divisionId, */
    public static int getMatchingDivisionId(String division) throws SQLException
    {
        int divisionId = -1;
        String sql = "SELECT DIVISION_ID FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }

    /** This method is used to assist in filling the First Level Division ComboBox in the UpdateCustomer view with
     *  matching First Level Division by using a divisionId. This method is only used in getCountryForBox.
     *  @param divisionId This is obtained from the user's selection in the customer TableView. The matching
     *                    First Level Division name is returned to be able to fill the combobox in UpdateCustomer.
     *  @return Returns a FirstLevelDivision object.
     *   */
    public static FirstLevelDivision returnDivision(int divisionId) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String division = rs.getString("Division");
        FirstLevelDivision updateDivision = new FirstLevelDivision(division);
        return updateDivision;
    }
}
