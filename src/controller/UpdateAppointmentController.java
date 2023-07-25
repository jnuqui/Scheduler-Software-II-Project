package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.DatabaseAccess;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

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
    public TextField typeTextfield;
    @FXML
    public ComboBox customerIdComboBox;
    @FXML
    public ComboBox userIdComboBox;

    LocalDateTime originalStart;
    LocalDateTime originalEnd;

    private ObservableList<LocalTime> myLT = FXCollections.observableArrayList();
    LocalTime[] time = new LocalTime[13];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateLocation();

        try {
            populateContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        populateTypes();

        populateTimeComboBoxes();

        try {
            populateCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            populateUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setOriginalApptTime(LocalDateTime start, LocalDateTime end)
    {
        originalStart = start;
        originalEnd = end;
    }

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

    public void populateLocation() {
        locationComboBox.setItems(CollectionLists.getPlaces());
    }

    public void populateContacts() throws SQLException {
        contactComboBox.setItems(DatabaseAccess.getContactNames());
    }

    public void populateTypes()
    {
        typeComboBox.setItems(CollectionLists.getTypes());
    }

    public void populateTimeComboBoxes() {
        startTimeComboBox.setItems(CollectionLists.getTimes());
        endTimeComboBox.setItems(CollectionLists.getTimes());
    }

    public void populateCustomerIds() throws SQLException {
        customerIdComboBox.setItems(DatabaseAccess.getCustomerIds());
    }

    public void populateUsers() throws SQLException {
        userIdComboBox.setItems(DatabaseAccess.getUsers());
    }




    public void insertAppointmentTestFill() {
        titleTextfield.setText("Title Test");
        descriptionTextfield.setText("Description Test");
        locationComboBox.getSelectionModel().select(1);
        typeTextfield.setText("Shooting the breeze");

        LocalDate ldStart = LocalDate.now();
        LocalTime ltStart = LocalTime.now();
        LocalTime ltEnd = LocalTime.now().plusHours(1);
        startDatePicker.setValue(ldStart);
        startTimeComboBox.setValue(ltStart);
        endTimeComboBox.setValue(ltEnd);

        customerIdComboBox.getSelectionModel().select(1);
        userIdComboBox.getSelectionModel().select(1);
        contactComboBox.getSelectionModel().select(1);
    }


    public void setAppointmentId(String appointmentId) {
        appointmentIdTextfield.setText(appointmentId);
    }

    public void setTitle(String title)
    {
        titleTextfield.setText(title);
    }

    public void setDescription(String description)
    {
        descriptionTextfield.setText(description);
    }

    public void setLocation(String location) {
        locationComboBox.getSelectionModel().select(CollectionLists.returnUpdateLocation(location));
    }

    public void setContact(String contact) {
        contactComboBox.setValue(contact);
    }

    public void setType(String type)
    {
        typeComboBox.setValue(type);
    }

    public void setStartDate(LocalDate localDate)
    {
        startDatePicker.setValue(localDate);
    }

    public void setStartTime(LocalTime localTime)
    {
        startTimeComboBox.setValue(localTime);
    }

    public void setEndTime(LocalTime localTime)
    {
        endTimeComboBox.setValue(localTime);
    }



    public void setCustomerId(String customerId) throws SQLException {
        customerIdComboBox.getSelectionModel().select(DatabaseAccess.getMatchingCustomerId(customerId));
    }

    public void setUserId(String userId) throws SQLException {
        userIdComboBox.getSelectionModel().select(DatabaseAccess.getMatchingUserId(userId));
    }


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
        System.out.println("Input check: " + good);
        return good;
    }


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
        else if (ldtStart.isBefore(LocalDateTime.now()) || ldtEnd.isBefore(LocalDateTime.now()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Time");
            alert.setContentText("Appointment cannot be set in the past.");
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
            //System.out.println(CollectionLists.myFormattedDTF(ldtStart) + " " + CollectionLists.myFormattedDTF(ldtEnd));
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
        System.out.println("goodAppointmentTime: " + good);
        return good;
    }

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

                //(String title, String description, String location, String type,
                //      Timestamp tsStart, Timestamp tsEnd, int customerId, int userId, int contactId)
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
             System.out.println("Update didn't work");
        }

        }

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


    public void testPrint() throws SQLException {
        try{
            inputCheck();
            goodAppointmentTime();
           // System.out.println("Is the update time the same as before? - " + updateTimeSame());
        }
        catch (Exception e)
        { }

    }
}
