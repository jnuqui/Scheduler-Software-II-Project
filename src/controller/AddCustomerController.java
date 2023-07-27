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

/** This is the controller class for the "AddCustomer" view. It handles the business logic for inserting a
 *  customer to the database. */
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

    /** The initialize method for the AddCustomer view. This method is called when the view is launched
     *  and contains the method for populating the combo box for Countries on the form. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateCountries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method populates the combo box for countries. It calls a method from the CountryDAO to create a Country
     *  ObservableList for the combo box. */
    public void populateCountries() throws SQLException {
            CountryDAO.clearCountries();
            countryComboBox.setItems(CountryDAO.getCountries());
    }

    /** This method gets the Country that the user selects from the country combo box. The selected country then
     *  gets passed to the populateFirstLevelDivisions method. */
    public void passCountry() throws SQLException {
        try {
            firstLevelDivisionComboBox.getItems().removeAll();
            populateFirstLevelDivisions(countryComboBox.getValue().getCountryId());
        }
        catch (Exception e) {
        }
    }

    /** This method populates the combo box of First Level Divisions. The method is called after a Country is selected
     *  by the user and a database query is made to find the associated First Level Divisions.
     *  @param countryId This value is used to generate the appropriate list of First Level Divisions.
     *   */
    public void populateFirstLevelDivisions(int countryId) throws SQLException {
        FirstLevelDivisionDAO.clearLists();
        firstLevelDivisionComboBox.setItems(FirstLevelDivisionDAO.getAllFLD(countryId));
    }

    /** This method gets the values from each field of the form and sends it to the CustomerDAO to an INSERT
     *  statement to the database. First, inputCheck is called to check if inputs are filled. Custom confirmation
     *  message launches when successful. */
    public void addCustomer() throws SQLException {
        try {
            if (inputCheck() == true) {
                String customerName = customerNameTextField.getText();
                String address = addressTextField.getText();
                String postalCode = postalCodeTextField.getText();
                String phone = phoneTextField.getText();
                int divisionIdFK = FirstLevelDivisionDAO.getMatchingDivisionId(firstLevelDivisionComboBox.getSelectionModel().getSelectedItem().toString());

                CustomerDAO.insertCustomer(customerName, address, postalCode, phone, divisionIdFK);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Success.");
                alert.setContentText("Customer " + customerName + " successfully added.");
            }
        }
        catch (Exception e)
        {

        }
    }

    /** This method brings the user back to the customer dashboard. */
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

    /** This method checks if all fields on the form were completed. Launches an error window if form is not complete.
     * @return Returns boolean of checking if all the fields were completed on the form.
     *  */
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

    /** This method clears all the fields. Setting the form empty gives the option to the user to quickly start over
     *  the entry. */
    public void resetFields()
    {
        customerNameTextField.setText("");
        addressTextField.setText("");
        postalCodeTextField.setText("");
        phoneTextField.setText("");
        countryComboBox.setValue(null);
        firstLevelDivisionComboBox.setValue(null);
    }
}
