package controller;

import dao.*;
import helper.CollectionLists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;


/** This is the controller class for the "AddAppointment" view. It handles the business logic for inserting an
 *  appointment to the database. */
public class AddAppointmentController implements Initializable
{
    @FXML
    public ComboBox typeComboBox;
    @FXML
    public DatePicker endDatePicker;
    @FXML
    private ComboBox locationComboBox;
    @FXML
    public ComboBox contactComboBox;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public ComboBox startTimeComboBox;
    @FXML
    public ComboBox endTimeComboBox;
    @FXML
    public TextField titleTextfield;
    public TextField descriptionTextfield;
    @FXML
    public ComboBox customerIdComboBox;
    @FXML
    public ComboBox userIdComboBox;

    /** The initialize method for the Add Appointment view. This method is called when the view is launched
     *  and contains methods for populating the combo boxes for the form.
     *  @param resourceBundle The resourceBundle for initialize
     *  @param url The url for initialize */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method populates the combo boxes for the form. Each combo box uses a method to get an ObservableList for
     *  each respective combo box. */
    public void populateComboBoxes() throws SQLException {
        locationComboBox.setItems(CollectionLists.getPlaces());
        contactComboBox.setItems(ContactDAO.getContactNames());
        typeComboBox.setItems(CollectionLists.getTypes());
        startTimeComboBox.setItems(CollectionLists.getTimes());
        endTimeComboBox.setItems(CollectionLists.getTimes());
        customerIdComboBox.setItems(CustomerDAO.getCustomerIds());
        userIdComboBox.setItems(UserDAO.getUsers());
    }

    /** This method checks if each field in the form is filled, and returns true or false. If any one of the
     *  fields is empty when user tries to add an appointment, an alert window launches.
     *  @return Returns boolean of the logical checks.
     *  */
    public boolean inputCheck()
    {
        boolean good = true;
        if (titleTextfield.getText() == "" ||
                descriptionTextfield.getText() == "" ||
                locationComboBox.getValue() == null ||
                typeComboBox.getValue() == null ||
                startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
                endDatePicker.getValue() == null ||
                endTimeComboBox.getValue() == null ||
                customerIdComboBox.getValue() == null ||
                userIdComboBox.getValue() == null ||
                contactComboBox.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Inputs");
            alert.setContentText("Please complete all fields.");
            good = false;
        }
        return good;
    }

    /** This method checks if the user's Start and End Times are appropriate.
     *  It checks the following time possibilities:
     *  - End time is before Start time
     *  - Start and End Time is the same
     *  - Compares against open office hours (Eastern Time)
     *  - Overlapping appointments of the same customer.
     *
     *  This method uses the Errors interface to use a lambda expression to shorten the code needed to launch custom
     *  error messages. Since launching alert windows take 4 lines, it is efficient to make one block of code to
     *  replicate and pass the custom messages into the lambda. It also makes the method visually look better and
     *  easier to scroll through.
     *  @return Returns boolean if the times are appropriate. */
    public boolean goodAppointmentTime() throws SQLException {

        boolean good = true;
        //Start
        LocalDate ldStart = startDatePicker.getValue();
        LocalTime ltStart = LocalTime.parse(startTimeComboBox.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = endDatePicker.getValue();
        LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);

        int customerId = Integer.parseInt((String) customerIdComboBox.getSelectionModel().getSelectedItem());

        Errors error = (d, m) ->
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText(d);
            alert.setContentText(m);
        };

        if(startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
                endDatePicker.getValue() == null ||
                endTimeComboBox.getValue() == null )
        {
            good = false;
        }
        else if(ldtEnd.isBefore(ldtStart))
        {
            error.makeAlert("Check Time", "End time must be after Start Time");
            good = false;
        }
        else if (ldtStart.isEqual(ldtEnd))
        {
            error.makeAlert("Check Time", "Start and end time cannot be the same.");
            good = false;
        }
        else if (!CollectionLists.checkTimeRange(ldtStart, ldtEnd))
        {
            error.makeAlert("Check Time", "Time is not within 8:00AM - 10:00PM ET");
            good = false;
        }
        else if (!AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd, customerId).equals("No"))
        {
            error.makeAlert("Conflicting Time", AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd, customerId));
            good = false;
        }
        return good;
    }

    /** This method gets the values from each field of the form and sends it to the AppointmentDAO to an INSERT
     *  statement to the database. First, two methods (inputCheck and goodAppointmentTime) check if inputs are filled
     *  and if the Start and End Times are appropriate. A confirmation window is launched if successful. */
    public void insertAppointment() throws SQLException {

        try{
            boolean inputWorks = inputCheck();
            boolean timeWorks = goodAppointmentTime();
            if(inputWorks && timeWorks)
            {
                String title = titleTextfield.getText();
                String description = descriptionTextfield.getText();
                String location = locationComboBox.getValue().toString();
                String type = typeComboBox.getValue().toString();

                //Start
                LocalDate ldStart = startDatePicker.getValue();
                LocalTime ltStart = LocalTime.parse(startTimeComboBox.getValue().toString());
                LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);
                Timestamp tsStart = Timestamp.valueOf(ldtStart);

                //End
                LocalDate ldEnd = endDatePicker.getValue();
                LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
                LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
                Timestamp tsEnd = Timestamp.valueOf(ldtEnd);

                int customerId = Integer.parseInt((String) customerIdComboBox.getSelectionModel().getSelectedItem());
                int userId = Integer.parseInt((String) userIdComboBox.getSelectionModel().getSelectedItem());
                int contactId = ContactDAO.getContactId(contactComboBox.getValue().toString());

                AppointmentDAO.insertAppointment(title, description, location, type, tsStart, tsEnd, customerId, userId, contactId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Successfully added.");
                alert.setContentText("Appointment for customer " + customerId + " successfully added.");
            }
        }
        catch (Exception e)
        {

        }
    }

    /** This method clears all the fields. Setting the form empty gives the option to the user to quickly start over
     *  the entry. */
    public void resetFields()
    {
        titleTextfield.setText("");
        descriptionTextfield.setText("");
        locationComboBox.setValue(null);
        typeComboBox.setValue(null);
        startDatePicker.setValue(null);
        startTimeComboBox.setValue(null);
        endDatePicker.setValue(null);
        endTimeComboBox.setValue(null);
        customerIdComboBox.setValue(null);
        userIdComboBox.setValue(null);
        contactComboBox.setValue(null);
    }

    /** This method brings the user back to the main scheduling dashboard.
     * @param actionEvent The action when the button is clicked. */
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
