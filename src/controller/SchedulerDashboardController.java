package controller;

import dao.AppointmentDAO;
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
import javafx.util.Callback;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SchedulerDashboardController implements Initializable
{   @FXML
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
    public TableColumn<Appointment, String> endColumn = new TableColumn<>("endTime");;
    @FXML
    public TableColumn customerIdColumn;
    //@FXML
    //public TableColumn contactIdColumn;
    @FXML
    public TableColumn userIdColumn;

    public ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public ObservableList<Contact>  allContacts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            allAppointments = AppointmentDAO.getAppointmentsv2();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateTablev2();

        //Random print
    }

    /*public void populateTable()
    {
        try {
            appointmentsTable.setItems(AppointmentDAO.getAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        //contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));

    }*/

    public void populateTablev2()
    {
        try {
            appointmentsTable.setItems(AppointmentDAO.getAppointmentsv2());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //Use with Callback method below.
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //startColumn.setCellFactory(createCellFactory(formatter));
        //endColumn.setCellFactory(createCellFactory(formatter));
    }

    public void populateTableMonth()
    {
        try {
            appointmentsTable.setItems(AppointmentDAO.getAppointmentsByMonth());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));


        //Use with Callback method below
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //startColumn.setCellFactory(createCellFactory(formatter));
        //endColumn.setCellFactory(createCellFactory(formatter));
    }

    public void populateTableWeek() {
        {
            try {
                appointmentsTable.setItems(AppointmentDAO.getAppointmentsByWeek());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            //contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));


            //Use with Callback method below
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            //startColumn.setCellFactory(createCellFactory(formatter));
            //endColumn.setCellFactory(createCellFactory(formatter));
        }
    }

    /*
    private <T> Callback<TableColumn<Appointment, T>, javafx.scene.control.TableCell<Appointment, T>> createCellFactory(DateTimeFormatter formatter) {
        return column -> new javafx.scene.control.TableCell<Appointment, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format((LocalDateTime) item));
                }
            }
        };
    }*/


    public void toCustomerGUI(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/CustomerGUI.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

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

    public void toUpdateAppointment(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 700);
        stage.setTitle("Update Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public String sendLocation()
    {
        return appointmentsTable.getSelectionModel().getSelectedItem().getLocation();
    }


    public void toUpdateAppointmentv2(ActionEvent actionEvent) throws IOException, SQLException {
        if( appointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select an appointment to update.");
            return;
        }
        else {
            //int appointmentId = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController myUpdate = loader.getController();
            myUpdate.setAppointmentId(String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId()));
            myUpdate.setTitle(appointmentsTable.getSelectionModel().getSelectedItem().getTitle());
            myUpdate.setDescription(appointmentsTable.getSelectionModel().getSelectedItem().getDescription());
            myUpdate.setLocation(sendLocation());
            myUpdate.setContact(appointmentsTable.getSelectionModel().getSelectedItem().getContactName());
            myUpdate.setType(appointmentsTable.getSelectionModel().getSelectedItem().getType());
            myUpdate.setStartDate(appointmentsTable.getSelectionModel().getSelectedItem().getStartTime().toLocalDate());
            myUpdate.setStartTime(appointmentsTable.getSelectionModel().getSelectedItem().getStartTime().toLocalTime());
            myUpdate.setEndTime(appointmentsTable.getSelectionModel().getSelectedItem().getEndTime().toLocalTime());
            myUpdate.setCustomerId(String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getCustomerId()));
            myUpdate.setUserId(String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getUserId()));

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

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
            String appointmentId = String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());
            String type = appointmentsTable.getSelectionModel().getSelectedItem().getType();
            AppointmentDAO.deleteAppointment(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.show();
            alert.setHeaderText("Delete Successful");
            alert.setContentText("Appointment (" + "ID:" + appointmentId + ", Type: " + type + ") deleted!");
            populateTablev2();
        }
    }

    public void testPrint()
    {
        System.out.println(allAppointments.get(1).getStartTime());
    }


}
