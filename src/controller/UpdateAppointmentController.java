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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/** This is the controller class for the "UpdateAppointment" view.*/
public class UpdateAppointmentController implements Initializable {
    @FXML
    public TextField appointmentIdTextfield;
    public Button updateButton;
    @FXML
    public ComboBox typeComboBox;
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

    LocalDateTime originalStart;
    LocalDateTime originalEnd;

    /** The initialize method for the Update Appointment view. This method is called when the view is launched
     *  and contains methods for populating the combo boxes for the form. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**This method sets the class's LocalDateTime objects. This is used to obtain the appointments original start and
     * end times. By recording these, they can be compared in the goodAppointmentTime method so that the appointment
     * can be updated with the same time and not be considered a scheduling conflict.
     * @param start The appointment's start time.
     * @param end  The appointment's end time.*/
    public void setOriginalApptTime(LocalDateTime start, LocalDateTime end)
    {
        originalStart = start;
        originalEnd = end;
    }

    /**@return boolean if the times that the user selects is the same as the original appointment data.
     * This method handles the Start and End times comparison from setOriginalApptTime, and is ultimately used
     * in the logic flow of goodAppointmentTime. */
    public boolean updateTimeSame()
    {
        boolean good = false;
        //Start
        LocalDate ldStart = startDatePicker.getValue();
        LocalTime ltStart = LocalTime.parse(startTimeComboBox.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = startDatePicker.getValue();
        LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
        if(ldtStart.isEqual(originalStart) && ldtEnd.isEqual(originalEnd))
            {
                good = true;
            }
        return good;
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

    /** This method sets appointmentIdTextField. The appointmentId is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param appointmentId This appointmentId is used to set the uneditable textfield that shows what appointment
     *                      is being updated. */
    public void setAppointmentId(String appointmentId) {
        appointmentIdTextfield.setText(appointmentId);
    }

    /** This method sets titleTextfield. The title is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param title This is used to set the textfield for the title of the appointment
     *                      is being updated. */
    public void setTitle(String title)
    {
        titleTextfield.setText(title);
    }

    /** This method sets titleTextfield. The description is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param description This is used to set the textfield for the description of the appointment
     *                      is being updated. */
    public void setDescription(String description)
    {
        descriptionTextfield.setText(description);
    }

    /** This method sets locationComboBox. The location is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param location This is used to set the combo box for the location of the appointment
     *                      is being updated. */
    public void setLocation(String location) {
        locationComboBox.getSelectionModel().select(CollectionLists.returnUpdateLocation(location));
    }

    /** This method sets the value for contactComboBox. The contact is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param contact This is used to set the combo box for the contact of the appointment
     *                      is being updated. */
    public void setContact(String contact) {
        contactComboBox.setValue(contact);
    }

    /** This method sets the value for typeComboBox. The type is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param type This is used to set the combo box for the type of the appointment
     *                      is being updated. */
    public void setType(String type)
    {
        typeComboBox.setValue(type);
    }

    /** This method sets the value for startDatePicker. The LocalDateTime from the user selected appointment gets
     *  converted to a LocalDate object to appropriately set the DatePicker.
     *  @param localDate This is used to set the DatePicker for the date of the appointment
     *                      is being updated. */
    public void setStartDate(LocalDate localDate)
    {
        startDatePicker.setValue(localDate);
    }

    /** This method sets the value for startTimeComboBox. The LocalDateTime from the user selected appointment gets
     *  converted to a LocalTime object to appropriately set the ComboBox.
     *  @param localTime This is used to set the ComboBox for the time of the appointment
     *                      is being updated. */
    public void setStartTime(LocalTime localTime)
    {
        startTimeComboBox.setValue(localTime);
    }

    /** This method sets the value for endTimeComboBox. The LocalDateTime from the user selected appointment gets
     *  converted to a LocalTime object to appropriately set the ComboBox for the end time.
     *  @param localTime This is used to set the ComboBox for the time of the appointment
     *                      is being updated. */
    public void setEndTime(LocalTime localTime)
    {
        endTimeComboBox.setValue(localTime);
    }

    /** This method sets customerIdComboBox. The customerId is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param customerId This customerId is used to set the ComboBox that shows what appointment
     *                      is being updated. */
    public void setCustomerId(String customerId) throws SQLException {
        customerIdComboBox.getSelectionModel().select(CustomerDAO.getMatchingCustomerId(customerId));
    }

    /** This method sets userIdComboBox. The userId is passed from the appointment selection
     *  made by the user from the main dashboard.
     *  @param userId This userId is used to set the ComboBox that shows what appointment
     *                      is being updated. */
    public void setUserId(String userId) throws SQLException {
        userIdComboBox.getSelectionModel().select(UserDAO.getMatchingUserId(userId));
    }

    /** @return - This method checks if each field in the form is filled, and returns true or false. If any one of the fields is empty when user tries to
     *  add an appointment, an alert window launches. */
    public boolean inputCheck()
    {
        boolean good = true;
        if (titleTextfield.getText() == "" ||
                descriptionTextfield.getText() == "" ||
                locationComboBox.getValue() == null ||
                typeComboBox.getValue() == null ||
                startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
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

    /** @return - This method checks if the user's Start and End Times are appropriate and returns true or false.
     *  It checks the following time possibilities:
     *  - End time is before Start time
     *  - Start and End Time is the same
     *  - Time has not changed from the original appointment
     *  - Compares against open office hours (Eastern Time)
     *  - Overlapping appointments of the same customer. */
    public boolean goodAppointmentTime() throws SQLException {

        boolean good = true;
        //Start
        LocalDate ldStart = startDatePicker.getValue();
        LocalTime ltStart = LocalTime.parse(startTimeComboBox.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = startDatePicker.getValue();
        LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);

        int customerId = Integer.parseInt((String) customerIdComboBox.getSelectionModel().getSelectedItem());
        int appointmentId = Integer.parseInt(appointmentIdTextfield.getText());
        if(startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
                endTimeComboBox.getValue() == null )
        {
            good = false;
        }
        else if(ldtEnd.isBefore(ldtStart))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Time");
            alert.setContentText("End time must be after Start Time");
            good = false;
        }
        else if (ldtStart.isEqual(ldtEnd))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Time");
            alert.setContentText("Start and end time cannot be the same.");
            good = false;
        }
        else if (updateTimeSame() == true)
        {
            good = true;
        }
        else if (!CollectionLists.checkTimeRange(ldtStart, ldtEnd))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Time");
            alert.setContentText("Time is not within 8:00AM - 10:00PM ET");
            good = false;
        }
        else if (!AppointmentDAO.checkUpdateAppointmentOverlap(ldtStart, ldtEnd, customerId, appointmentId).equals("No"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Conflicting Time");
            alert.setContentText(AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd, customerId));
            good = false;
        }
        return good;
    }

    /** This method gets the values from each field of the form and sends it to the AppointmentDAO to an INSERT
     *  statement to the database. First, two methods (inputCheck and goodAppointmentTime) check if inputs are filled
     *  and if the Start and End Times are appropriate. */
    public void updateAppointment() throws SQLException {
        try {
            boolean inputWorks = inputCheck();
            boolean timeWorks = goodAppointmentTime();
            if (inputWorks && timeWorks)
            {
                int appointmentId = Integer.parseInt(appointmentIdTextfield.getText());
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
                LocalDate ldEnd = startDatePicker.getValue();
                LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
                LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
                Timestamp tsEnd = Timestamp.valueOf(ldtEnd);

                int customerId = Integer.parseInt((String) customerIdComboBox.getSelectionModel().getSelectedItem());
                int userId = Integer.parseInt((String) userIdComboBox.getSelectionModel().getSelectedItem());
                int contactId = ContactDAO.getContactId(contactComboBox.getValue().toString());

                AppointmentDAO.updateAppointment(title, description, location, type, tsStart, tsEnd, customerId, userId, contactId, appointmentId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Successfully updated.");
                alert.setContentText("Appointment for customer " + customerId + " successfully updated.");

                originalStart = ldtStart;
                originalEnd = ldtEnd;
            }
        }
        catch (Exception e)
        {
        }
        }

    /** This method clears all the fields. Setting the form empty gives the option to the user to completely and
     *  quickly change this update. */
    public void resetFields()
    {
        titleTextfield.setText("");
        descriptionTextfield.setText("");
        locationComboBox.setValue(null);
        typeComboBox.setValue(null);
        startDatePicker.setValue(null);
        startTimeComboBox.setValue(null);
        endTimeComboBox.setValue(null);
        customerIdComboBox.setValue(null);
        userIdComboBox.setValue(null);
        contactComboBox.setValue(null);
    }

    /** This method brings the user back to the main scheduling dashboard. */
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
