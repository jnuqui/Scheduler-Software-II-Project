package controller;

import dao.AppointmentDAO;
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

public class AddAppointmentController implements Initializable
{
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

    //private ObservableList <LocalTime> myLT = FXCollections.observableArrayList();
    //LocalTime [] time = new LocalTime[46];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void populateComboBoxes() throws SQLException {
        locationComboBox.setItems(CollectionLists.getPlaces());
        contactComboBox.setItems(DatabaseAccess.getContactNames());
        startTimeComboBox.setItems(CollectionLists.getTimes());
        endTimeComboBox.setItems(CollectionLists.getTimes());
        customerIdComboBox.setItems(DatabaseAccess.getCustomerIds());
        userIdComboBox.setItems(DatabaseAccess.getUsers());
    }

    public boolean inputCheck()
    {
        boolean good = true;
        if (titleTextfield.getText() == "" ||
                descriptionTextfield.getText() == "" ||
                locationComboBox.getValue() == null ||
                typeTextfield.getText() == "" ||
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
        else if (!AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd).equals("No"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Conflicting Time");
            alert.setContentText(AppointmentDAO.checkAppointmentOverlap(ldtStart, ldtEnd));
            good = false;
        }
        else if (!CollectionLists.checkTimeRange(ldtStart, ldtEnd))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Time");
            alert.setContentText("Time is not within 8:00AM - 10:00PM ET");
            good = false;
        }
        System.out.println("goodAppointmentTime: " + good);
        return good;
    }

    public void insertAppointment() throws SQLException {

        try{
            boolean inputWorks = inputCheck();
            boolean timeWorks = goodAppointmentTime();
            if(inputWorks && timeWorks)
            {
                String title = titleTextfield.getText();
                String description = descriptionTextfield.getText();
                String location = locationComboBox.getValue().toString();
                String type = typeTextfield.getText();

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
                int contactId = DatabaseAccess.getContactId(contactComboBox.getValue().toString());

                //(String title, String description, String location, String type,
                //      Timestamp tsStart, Timestamp tsEnd, int customerId, int userId, int contactId)
                AppointmentDAO.insertAppointment(title, description, location, type, tsStart, tsEnd, customerId, userId, contactId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Successfully added.");
                alert.setContentText("Appointment for customer " + customerId + " successfully added.");
            }
        }
        catch (Exception e)
        {
            // System.out.println("Check test");
        }
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

    public void insertAppointmentTestFill()
    {
        titleTextfield.setText("Title Test");
        descriptionTextfield.setText("Description Test");
        locationComboBox.getSelectionModel().select(1);
        typeTextfield.setText("Shooting the breeze");

        LocalDate ldStart = LocalDate.now();
        LocalTime ltStart = LocalTime.now();
        LocalTime ltEnd = LocalTime.now().plusHours(1);
        startDatePicker.setValue(ldStart);
        startTimeComboBox.setValue(CollectionLists.myFormattedTF(ltStart));
        endTimeComboBox.setValue(CollectionLists.myFormattedTF(ltEnd));

        customerIdComboBox.getSelectionModel().select(1);
        userIdComboBox.getSelectionModel().select(1);
        contactComboBox.getSelectionModel().select(1);
    }
    public void testPrint() throws SQLException {
        try{
            inputCheck();
            goodAppointmentTime();
        }
        catch (Exception e)
        { }
    }
}
