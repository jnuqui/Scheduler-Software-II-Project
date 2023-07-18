package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    static ObservableList<String> allCountriesStrings = FXCollections.observableArrayList();

    public static ObservableList<Country> getCountries() throws SQLException {

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            allCountries.add(country);
        }
        return allCountries;
    }

    public static ObservableList<String> getCountriesStrings() throws SQLException {
            getCountries();
            for (int i = 0; i <= (allCountries.size() - 1); i++) {
                allCountriesStrings.add(allCountries.get(i).getCountryName());
            }
            return allCountriesStrings;
    }

    public static int getMatchingCountryId(String country) throws SQLException
    {
        int countryId = -1;
        String sql = "SELECT COUNTRY_ID FROM COUNTRIES WHERE COUNTRY = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            countryId = rs.getInt("Country_ID");
        }

        return countryId;
    }
}
