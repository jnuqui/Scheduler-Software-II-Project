package controller;

import dao.AppointmentDAO;
import dao.CountryDAO;
import dao.CustomerDAO;
import dao.FirstLevelDivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable
{

    @FXML
    public ComboBox<Country> countryComboBox;
    @FXML
    public ComboBox<FirstLevelDivision> firstLevelDivisionComboBox;
    @FXML
    public TextField customerNameTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField postalCodeTextField;
    @FXML
    public TextField phoneTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //countryComboBox.setPromptText("Select Country");
        //countryComboBox.setSelectionModel();
        try {
            populateCountriesv2();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //countryComboBox.setValue(countryComboBox.getItems().get(0));
    }

    public void populateCountries() throws SQLException {
        CountryDAO.clearCountries();
        //countryComboBox.setItems(CountryDAO.getCountriesStrings());
    }

    public void populateCountriesv2() throws SQLException {
            CountryDAO.clearCountries();
            countryComboBox.setItems(CountryDAO.getCountries());
    }

    public void passCountry() throws SQLException {
        try {
            firstLevelDivisionComboBox.getItems().removeAll();
            populateFirstLevelDivisions(countryComboBox.getValue().getCountryId());
        }
        catch (Exception e) {
        }
    }

    public void populateFirstLevelDivisions(int countryId) throws SQLException {
        FirstLevelDivisionDAO.clearLists();
        //FirstLevelDivisionDAO.getAllFLD(countryId);
        firstLevelDivisionComboBox.setItems(FirstLevelDivisionDAO.getAllFLD(countryId));
    }

    public void addCustomer() throws SQLException {
        String customerName = customerNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        int divisionIdFK = FirstLevelDivisionDAO.getMatchingDivisionId(firstLevelDivisionComboBox.getSelectionModel().getSelectedItem().toString());

        /* in case I want to test more printing stuff
        System.out.println(customerName);
        System.out.println(address);
        System.out.println(postalCode);
        System.out.println(phone);
        System.out.println(divisionIdFK);
        */

        CustomerDAO.insertCustomer(customerName, address, postalCode, phone, divisionIdFK);
    }

    public void testPrint() throws SQLException {
        //System.out.println(FirstLevelDivisionDAO.getMatchingDivisionId(firstLevelDivisionComboBox.getSelectionModel().getSelectedItem().toString()));

    }

    public void toCustomerGUI(ActionEvent actionEvent) throws IOException
    {
        //firstLevelDivisionComboBox.getItems().removeAll();
        Parent root = FXMLLoader.load(getClass().getResource("../view/CustomerGUI.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 400);
        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


}
