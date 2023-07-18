package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionDAO
{
    static ObservableList<FirstLevelDivision> allFLD = FXCollections.observableArrayList();
    static ObservableList<String> allFLDStrings = FXCollections.observableArrayList();

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

    public static ObservableList<String> getFLDStrings() throws SQLException {
        for (int i = 0; i <= (allFLD.size() - 1); i++)
        {
            allFLDStrings.add(allFLD.get(i).getDivisionName());
        }
        //System.out.println(allCountriesStrings);
        return allFLDStrings;
    }

    public static void clearLists()
    {
        allFLD.clear();
        allFLDStrings.clear();
    }


}
