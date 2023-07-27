package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import helper.CollectionLists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This is the controller class for the "SchedulerDashboard" view. It displays all appointments in a TableView,
 *  including the results for different views and the reports. There are buttons for navigating to the Customer view
 *  and Add/Update Appointment views. */
public class SchedulerDashboardController implements Initializable
{
    @FXML
    public TableView<Appointment> reportContactTable;
    @FXML
    public TableView<Appointment> reportTypeTable;
    @FXML
    public ComboBox typeComboBox;
    @FXML
    public ComboBox monthComboBox;
    @FXML
    public ComboBox contactComboBox;
    public Button typeMonthButton;
    @FXML
    public TableView<Appointment> reportCustomTable;
    @FXML
    public TableColumn appointmentIdContactColumn;
    @FXML
    public TableColumn titleContactColumn;
    @FXML
    public TableColumn descriptionContactColumn;
    @FXML
    public TableColumn locationContactColumn;
    @FXML
    public TableColumn contactContactColumn;
    @FXML
    public TableColumn typeContactColumn;
    @FXML
    public TableColumn startContactColumn;
    @FXML
    public TableColumn endContactColumn;
    @FXML
    public TableColumn customerIdContactColumn;
    @FXML
    public TableColumn userIdContactColumn;
    @FXML
    public TableColumn monthReportColumn;
    @FXML
    public TableColumn typeReportColumn;
    @FXML
    public TableColumn countColumn;
    @FXML
    public RadioButton allAppointmentsRadio;
    @FXML
    public RadioButton monthAppointmentsRadio;
    @FXML
    public RadioButton weekAppointmentRadio;
    @FXML
    public ComboBox locationComboBox;
    @FXML
    public TableColumn appointmentIdCustomColumn;
    @FXML
    public TableColumn titleCustomColumn;
    @FXML
    public TableColumn descriptionCustomColumn;
    @FXML
    public TableColumn locationCustomColumn;
    @FXML
    public TableColumn contactCustomColumn;
    @FXML
    public TableColumn typeCustomColumn;
    @FXML
    public TableColumn startCustomColumn;
    @FXML
    public TableColumn endCustomColumn;
    @FXML
    public TableColumn customerIdCustomColumn;
    @FXML
    public TableColumn userIdCustomColumn;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    public TableColumn appointmentIdColumn;
    @FXML
    public TableColumn titleColumn;
    @FXML
    public TableColumn descriptionColumn;
    @FXML
    public TableColumn locationColumn;
    @FXML
    public TableColumn contactColumn;
    @FXML
    public TableColumn typeColumn;
    @FXML
    public TableColumn<Appointment, String> startColumn = new TableColumn<>("startTime");
    @FXML
    public TableColumn<Appointment, String> endColumn = new TableColumn<>("endTime");
    @FXML
    public TableColumn customerIdColumn;
    @FXML
    public TableColumn userIdColumn;

    public ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    TableColumns columnSetter = (a, t, d, l, c, t2, st, et, cId, uId) ->
    {
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>(a));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>(t));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>(d));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>(l));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>(c));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>(t2));
        startColumn.setCellValueFactory(new PropertyValueFactory<>(st));
        endColumn.setCellValueFactory(new PropertyValueFactory<>(et));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>(cId));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>(uId));
    };

    /** This is the initialize method for this view which gets and sets data in the components. The appointments are
     *  retrieved, put in an ObservableList and set in the TableView. The combo boxes are also populated
     *  for the different reports. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allAppointments = AppointmentDAO.getAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateTable();

        typeComboBox.setItems(CollectionLists.getTypes());
        monthComboBox.setItems(CollectionLists.getMonths());
        try {
            contactComboBox.setItems(ContactDAO.getContactNames());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        locationComboBox.setItems(CollectionLists.getPlaces());
    }

    /** This method sets the ObservableList of appointments in the TableView. The TableColumns are also initialized.
     *  The visibility of tables are also set because other TableViews are called for the reports, which makes the
     *  main appointment TableView not visible. This method is called when the All radio button is selected to refresh
     *  this TableView.
     *
     *  This method uses a lambda expression to cut down the wall of code that is typically necessary
     *  to initialize TableColumns. */
    public void populateTable()
    {
        appointmentsTable.setItems(allAppointments);

        columnSetter.setColumns("appointmentId", "title", "description", "location", "contactName",
                "type", "startTime", "endTime", "customerId", "userId");

        appointmentsTable.visibleProperty().setValue(true);
        reportTypeTable.visibleProperty().setValue(false);
        reportContactTable.visibleProperty().setValue(false);
        reportCustomTable.visibleProperty().setValue(false);
    }

    /** This method sets the ObservableList of appointments in the TableView for the current month of the user by the
     *  machine's Time Zone. A query is made to the database to retrieve appointments that match the month that the
     *  user is in. The TableColumns are also initialized. The visibility of tables are also set because other
     *  TableViews could be visible instead of this one.
     *
     *  This method uses a lambda expression to cut down the wall of code that is typically necessary
     *  to initialize TableColumns. */
    public void populateTableMonth()
    {
        try {
            appointmentsTable.setItems(AppointmentDAO.getAppointmentsByMonth());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        columnSetter.setColumns("appointmentId", "title", "description", "location", "contactName",
                "type", "startTime", "endTime", "customerId", "userId");

        appointmentsTable.visibleProperty().setValue(true);
        reportTypeTable.visibleProperty().setValue(false);
        reportContactTable.visibleProperty().setValue(false);
        reportCustomTable.visibleProperty().setValue(false);
    }

    /** This method sets the ObservableList of appointments in the TableView for the current day plus seven days of the
     * user's Time Zone. A query is made to the database to retrieve appointments that match appointments within 7 days
     *  The TableColumns are also initialized. The visibility of tables are also set because other
     *  TableViews could be visible instead of this one.
     *
     *  This method uses a lambda expression to cut down the wall of code that is typically necessary
     *  to initialize TableColumns. */
    public void populateTableWeek() {
        {
            try {
                appointmentsTable.setItems(AppointmentDAO.getAppointmentsByWeek());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            columnSetter.setColumns("appointmentId", "title", "description", "location", "contactName",
                    "type", "startTime", "endTime", "customerId", "userId");

            appointmentsTable.visibleProperty().setValue(true);
            reportTypeTable.visibleProperty().setValue(false);
            reportContactTable.visibleProperty().setValue(false);
            reportCustomTable.visibleProperty().setValue(false);
        }
    }

    /** This method brings the user to the customer dashboard. */
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

    /** This method brings the user to the AddAppointment view. */
    public void toAddAppointment(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 700);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** This method brings the user to the UpdateAppointment view. If an appointment is not selected from the TableView,
     *  an alert will launch an error. When an appointment is selected, the UpdateAppointment view will be launched and
     *  the appointment and its data will be passed into the fields of the new view. */
    public void toUpdateAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        if( appointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select an appointment to update.");
            return;
        }
        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController myUpdate = loader.getController();
            myUpdate.setAppointmentId(String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId()));
            myUpdate.setTitle(appointmentsTable.getSelectionModel().getSelectedItem().getTitle());
            myUpdate.setDescription(appointmentsTable.getSelectionModel().getSelectedItem().getDescription());
            myUpdate.setLocation(appointmentsTable.getSelectionModel().getSelectedItem().getLocation());
            myUpdate.setContact(appointmentsTable.getSelectionModel().getSelectedItem().getContactName());
            myUpdate.setType(appointmentsTable.getSelectionModel().getSelectedItem().getType());
            myUpdate.setStartDate(appointmentsTable.getSelectionModel().getSelectedItem().getStartTime().toLocalDate());
            myUpdate.setStartTime(appointmentsTable.getSelectionModel().getSelectedItem().getStartTime().toLocalTime());
            myUpdate.setEndTime(appointmentsTable.getSelectionModel().getSelectedItem().getEndTime().toLocalTime());
            myUpdate.setCustomerId(String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getCustomerId()));
            myUpdate.setUserId(String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getUserId()));

            myUpdate.setOriginalApptTime(appointmentsTable.getSelectionModel().getSelectedItem().getStartTime(), appointmentsTable.getSelectionModel().getSelectedItem().getEndTime());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    /** This method deletes the appointment that the user selects from the appointment TableView. If an appointment is
     *  not selected, an alert will appear. User is asked for confirmation before a successful delete. A custom
     *  message appears if a delete occurs. */
    public void deleteAppointment() throws SQLException {
        if( appointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select an appointment to delete.");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete appointment (ID:" + appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId() + ") ?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String appointmentId = String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());
                    String type = appointmentsTable.getSelectionModel().getSelectedItem().getType();
                    try {
                        AppointmentDAO.deleteAppointment(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Alert alertDeleted = new Alert(Alert.AlertType.INFORMATION);
                    alertDeleted.show();
                    alertDeleted.setHeaderText("Delete Successful");
                    alertDeleted.setContentText("Appointment (" + "ID:" + appointmentId + ", Type: " + type + ") deleted");
                }
            });
            try {
                allAppointments = AppointmentDAO.getAppointments();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            populateTable();
        }
    }

    /** This method makes the reportTypeTable visible and displays results for the report. A query is made using the
     *  month and type selected by the user. An alert appears if the user did not select values for both combo boxes.
     *  The TableColumns are also initialized. The other TableViews are set to not be visible. The View radio buttons
     *  are also unselected so that they can be chosen to reset the appointment TableView.*/
    public void getTypeMonthReport() throws SQLException {

        try{

        reportTypeTable.setItems(AppointmentDAO.getMonthTypeReport(monthComboBox.getValue().toString(), typeComboBox.getValue().toString()));

        monthReportColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeReportColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        appointmentsTable.visibleProperty().setValue(false);
        reportTypeTable.visibleProperty().setValue(true);
        reportContactTable.visibleProperty().setValue(false);
        reportCustomTable.visibleProperty().setValue(false);

        allAppointmentsRadio.setSelected(false);
        monthAppointmentsRadio.setSelected(false);
        weekAppointmentRadio.setSelected(false);
    }        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Select Month/Type");
            alert.setContentText("Select Month and Type first");
        }
    }

    /** This method makes the reportContactTable visible and displays results for the report. A query is made using the
     *  contact selected by the user. An alert appears if the user did not select a contact from the combo box.
     *  The TableColumns are also initialized. The other TableViews are set to not be visible. The View radio buttons
     *      *  are also unselected so that they can be chosen to reset the appointment TableView.*/
    public void getContactReport() throws SQLException {
        try {
            reportContactTable.setItems(AppointmentDAO.getContactReport(contactComboBox.getValue().toString()));

            appointmentIdContactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleContactColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionContactColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationContactColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeContactColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startContactColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endContactColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customerIdContactColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdContactColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

            appointmentsTable.visibleProperty().setValue(false);
            reportTypeTable.visibleProperty().setValue(false);
            reportContactTable.visibleProperty().setValue(true);
            reportCustomTable.visibleProperty().setValue(false);

            allAppointmentsRadio.setSelected(false);
            monthAppointmentsRadio.setSelected(false);
            weekAppointmentRadio.setSelected(false);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Select Contact");
            alert.setContentText("Select Contact first");
        }
    }

    /** This method makes the reportContactTable visible and displays results for the report. A query is made using the
     *  contact selected by the user. An alert appears if the user did not select a contact from the combo box.
     *  The TableColumns are also initialized. The other TableViews are set to not be visible. The View radio buttons
     *      *  are also unselected so that they can be chosen to reset the appointment TableView.*/
    public void getCustomReport() throws SQLException {

        try {
            reportCustomTable.setItems(AppointmentDAO.getLocationReport(locationComboBox.getValue().toString()));

            appointmentIdCustomColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCustomColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCustomColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCustomColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactCustomColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCustomColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCustomColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endCustomColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customerIdCustomColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdCustomColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

            appointmentsTable.visibleProperty().setValue(false);
            reportTypeTable.visibleProperty().setValue(false);
            reportContactTable.visibleProperty().setValue(false);
            reportCustomTable.visibleProperty().setValue(true);

            allAppointmentsRadio.setSelected(false);
            monthAppointmentsRadio.setSelected(false);
            weekAppointmentRadio.setSelected(false);
            }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Select Location");
            alert.setContentText("Select Location first");
        }
    }
}
