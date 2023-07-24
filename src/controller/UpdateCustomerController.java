package controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable
{

    @FXML
    public TextField customerIdTextfield;
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

    public void populateCountriesv2() throws SQLException {
        CountryDAO.clearCountries();
        countryComboBox.setItems(CountryDAO.getCountries());
        // countryComboBox.setValue(CountryDAO.getCountries().get(0));
    }

    public void setCustomer(int customerId, String customerName, String address, String postalCode, String phone, Country country, FirstLevelDivision division)
    {
        customerIdTextfield.setText(String.valueOf(customerId));
        customerNameTextField.setText(customerName);
        addressTextField.setText(address);
        postalCodeTextField.setText(postalCode);
        phoneTextField.setText(phone);
        countryComboBox.setValue(country);
        firstLevelDivisionComboBox.setValue(division);
    }

    public void passCountry() throws SQLException {
        try {
            firstLevelDivisionComboBox.getItems().removeAll();
            populateFirstLevelDivisions(countryComboBox.getValue().getCountryId());
        }
        catch (Exception e)
        {

        }
    }

    public void populateFirstLevelDivisions(int countryId) throws SQLException {
        FirstLevelDivisionDAO.clearLists();
        //FirstLevelDivisionDAO.getAllFLD(countryId);
        firstLevelDivisionComboBox.setItems(FirstLevelDivisionDAO.getAllFLD(countryId));
    }

    public void updateCustomer() throws SQLException {

try{
    if (inputCheck() == true) {
        int customerId = Integer.parseInt(customerIdTextfield.getText());
        String customerName = customerNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        int divisionIdFK = FirstLevelDivisionDAO.getMatchingDivisionId(firstLevelDivisionComboBox.getSelectionModel().getSelectedItem().toString());

        CustomerDAO.updateCustomer(customerName, address, postalCode, phone, divisionIdFK, customerId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.show();
        alert.setHeaderText("Success.");
        alert.setContentText("Customer " + customerId + " successfully updated.");
    }
    }
        catch (Exception e)
        {

        }
    }

    public void testPrint() throws SQLException {
        System.out.println(FirstLevelDivisionDAO.getMatchingDivisionId(firstLevelDivisionComboBox.getSelectionModel().getSelectedItem().toString()));
    }

    public void toCustomerGUI(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/CustomerGUI.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 400);
        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public boolean inputCheck()
    {
        boolean good = true;
        if(customerNameTextField.getText() == "" ||
                addressTextField.getText() == "" ||
                postalCodeTextField.getText() == "" ||
                phoneTextField.getText() == "" ||
                countryComboBox.getValue() == null ||
                firstLevelDivisionComboBox.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Inputs");
            alert.setContentText("Please complete all fields.");
            good = false;
        }
        return good;
    }

}
