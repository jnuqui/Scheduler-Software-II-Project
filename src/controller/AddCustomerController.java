package controller;

import dao.CountryDAO;
import dao.FirstLevelDivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable
{

    @FXML
    public ComboBox countryComboBox;
    @FXML
    public ComboBox firstLevelDivisionComboBox;
    static int countryCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateCountries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void populateCountries() throws SQLException {
        //if(countryCount ==  )
        countryComboBox.setItems(CountryDAO.getCountriesStrings());
        countryCount = countryComboBox.getVisibleRowCount();
    }

    public void passCountry() throws SQLException {
        //testPrint(countryComboBox.getSelectionModel().getSelectedItem().toString());
        firstLevelDivisionComboBox.getItems().removeAll();
        populateFirstLevelDivisions(CountryDAO.getMatchingCountryId(countryComboBox.getSelectionModel().getSelectedItem().toString()));
    }

    public void populateFirstLevelDivisions(int countryId) throws SQLException {
        FirstLevelDivisionDAO.clearLists();
        FirstLevelDivisionDAO.getAllFLD(countryId);
        firstLevelDivisionComboBox.setItems(FirstLevelDivisionDAO.getFLDStrings());
    }

    public void testPrint(String country)
    {
        System.out.println(country);
    }


    public void toCustomerGUI(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/CustomerGUI.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.show();
    }


}
