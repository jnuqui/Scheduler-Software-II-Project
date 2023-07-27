package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The Abstract Class for CountryDAO. This handles database queries related to countries. */
public abstract class CountryDAO {
    static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    static ObservableList<String> allCountriesStrings = FXCollections.observableArrayList();

    /** Gets query results from countries from the database to fill an ObservableList. The results are loaded into
     *  country objects, which then fill the ObservableList. This is used to populate country combo boxes.
     *  @return Returns an ObservableList of Countries.
     *   */
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

    /** Method that is called to clear the ObservableLists. These clear both ObservableLists of this class to prevent
     *  the possibility of duplicating the lists and increasing their sizes. */
    public static void clearCountries()
    {
        allCountries.clear();
        allCountriesStrings.clear();
    }

    /** This method is used to assist in populating the countryComboBox in the UpdateCustomer view. It gets the
     *  customer's country when selected from the TableView to set the Update form with the matching country.
     *  @param divisionId This value comes from the customer selection in the customer TableView. Used for the query
     *                    to find the matching countryId.
     *  @return Returns the int id of a country. */
    public static int returnUpdateCountryId(int divisionId) throws SQLException {
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countryId = rs.getInt("Country_ID");
        return countryId;
    }

    /** This method is used to assist in filling the countryComboBox in the UpdateCustomer view with matching
     *   country of the customer's first level division.
     *  @param countryUpdateId This value comes from returnUpdateCountryId, which executes another query to return
     *                          the matching country name. Country_ID and Country are used to make a Country object.
     *  @return Returns a Country object, given a countryId. */
    public static Country returnUpdateCountry(int countryUpdateId) throws SQLException {
        String sql = "SELECT Country_ID, Country FROM Countries WHERE Country_Id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryUpdateId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countryId = rs.getInt("Country_ID");
        String countryName = rs.getString("Country");
        Country country = new Country(countryId, countryName);
        return country;
    }

    /** This method is used to assist in filling the countryComboBox in the UpdateCustomer view with matching country
     *  of the customer's first level division by passing its parameter to the returnUpdateCountry method, ultimately
     *  returning the matching Country name.
     *  @param divisionId This value comes from the customer selection in the customer TableView, which
     *                    which eventually obtains the matching country name.
     *  @return Returns a Country object. */
    public static Country getCountryForBox(int divisionId) throws SQLException {
        return returnUpdateCountry(returnUpdateCountryId(divisionId));
    }
}
