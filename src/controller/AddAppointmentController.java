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

        populateLocation();

        try {
            populateContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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



    public void insertTest() throws SQLException {
        LocalDate startDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
        LocalDateTime startLDT = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = LocalDate.now();
        LocalTime endTime = LocalTime.now().plusHours(1);
        LocalDateTime endLDT = LocalDateTime.of(endDate, endTime);

        Timestamp tsStart = Timestamp.valueOf(startLDT);
        Timestamp tsEnd = Timestamp.valueOf(endLDT);
        AppointmentDAO.insertTest(tsStart, tsEnd);
    }

    public void populateLocation()
    {
            locationComboBox.setItems(CollectionLists.getPlaces());
    }

    public void populateContacts() throws SQLException {
        contactComboBox.setItems(DatabaseAccess.getContactNames());
    }

    public void populateTimeComboBoxes()
    {
        //for both boxes
        /*for (int i = 0; i <= 23; i++) {
            {
                time[i] = LocalTime.of((i), 0);
            myLT.add(time[i]);
                time[i + 1] = LocalTime.of((i), 30);
                myLT.add(time[i + 1]);
            }
        }*/
        startTimeComboBox.setItems(CollectionLists.getTimes());
        endTimeComboBox.setItems(CollectionLists.getTimes());
    }

    public void populateCustomerIds() throws SQLException {
        customerIdComboBox.setItems(DatabaseAccess.getCustomerIds());
    }

    public void populateUsers() throws SQLException{
        userIdComboBox.setItems(DatabaseAccess.getUsers());
    }

    public void testPrint() throws SQLException {
        //System.out.print(startDatePicker.getValue().toString() + " " + startTimeComboBox.getValue().toString());
    System.out.println(DatabaseAccess.getContactId(contactComboBox.getValue().toString()));
    }

    public void insertAppointmentTestFill()
    {
        titleTextfield.setText("Title Test");
        descriptionTextfield.setText("Description Test");
        locationComboBox.getSelectionModel().select(1);
        typeTextfield.setText("Shooting the breeze");

        /*/Start
        LocalDate ldStart = startDatePicker.getValue();
        LocalTime ltStart = (LocalTime) startTimeComboBox.getValue();
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);
        Timestamp tsStart = Timestamp.valueOf(ldtStart);

        //End
        LocalDate ldEnd = endDatePicker.getValue();
        LocalTime ltEnd = (LocalTime) endTimeComboBox.getValue();
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
        Timestamp tsEnd = Timestamp.valueOf(ldtEnd);*/

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

    public void insertAppointment() throws SQLException {
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
}
