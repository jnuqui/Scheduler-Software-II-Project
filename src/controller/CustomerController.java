package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This is the controller class for the "Customer" view.*/
public class CustomerController implements Initializable
{
    @FXML
    public TableView<Customer> customersTable;

    public ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    @FXML
    public TableColumn customerIdColumn;
    @FXML
    public TableColumn customerNameColumn;
    @FXML
    public TableColumn addressColumn;
    @FXML
    public TableColumn postalCodeColumn;
    @FXML
    public TableColumn phoneColumn;
    @FXML
    public TableColumn divisionIdColumn;
    @FXML
    public TableColumn countryColumn;


    /** The initialize method for the Customer view. This method is called when the view is launched
     *  and contains the methods for both creating an ObservableList of Customers and then adding them to
     *  the Customer TableView. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            allCustomers = CustomerDAO.getCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateTable();
    }

    /** This is the method that populates the Customer table. The ObservableList created from the initialization of the
     *  view is used to populate the table. The Table Columns are also appropriately initialized. */
    public void populateTable(){
        customersTable.setItems(allCustomers);

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("divisionIdFK"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    /** This method deletes the customer that the user selects from the Customer TableView. If a customer is not
     *  selected, an alert will appear. If a customer is selected but still has appointments, an alert box appears
     *  that informs the user of that. User is asked for confirmation before a successful delete. A custom message
     *  appears after a successful delete.*/
    public void deleteCustomer() throws SQLException {
        if(customersTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select a customer to delete.");
            return;
        }
        if(CustomerDAO.checkCustomerAppointments(customersTable.getSelectionModel().getSelectedItem().getCustomerId()) > 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Delete customer appointments first.");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete customer (ID:" + customersTable.getSelectionModel().getSelectedItem().getCustomerId() + ") ?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CustomerDAO.deleteCustomer(customersTable.getSelectionModel().getSelectedItem().getCustomerId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Alert alertDelete = new Alert(Alert.AlertType.INFORMATION);
                    alertDelete.show();
                    alertDelete.setHeaderText("Delete Successful");
                    alertDelete.setContentText("Customer ID:" + customersTable.getSelectionModel().getSelectedItem().getCustomerId() + " deleted.");
                    try {
                        allCustomers = CustomerDAO.getCustomers();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            populateTable();
        }
    }

    /** This method brings the user to the AddCustomer view. */
    public void toAddCustomer (ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** This method brings the user to the UpdateCustomer view. If a customer is not selected by the user, an
     *  alert appears to inform the user. */
    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        if( customersTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select a customer to update.");
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateCustomer.fxml"));
            loader.load();

            UpdateCustomerController myUpdate = loader.getController();
            myUpdate.setCustomer(customersTable.getSelectionModel().getSelectedItem().getCustomerId(),
                    customersTable.getSelectionModel().getSelectedItem().toString(),
                    customersTable.getSelectionModel().getSelectedItem().getAddress(),
                    customersTable.getSelectionModel().getSelectedItem().getPostalCode(),
                    customersTable.getSelectionModel().getSelectedItem().getPhone(),
                    CountryDAO.getCountryForBox(customersTable.getSelectionModel().getSelectedItem().getDivisionIdFK()),
                    FirstLevelDivisionDAO.returnDivision(customersTable.getSelectionModel().getSelectedItem().getDivisionIdFK())
                    );

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Customer");
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    /** This method brings the user back to the main appointment view. */
    public void toSchedulerDashboard(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/SchedulerDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("Scheduler Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
