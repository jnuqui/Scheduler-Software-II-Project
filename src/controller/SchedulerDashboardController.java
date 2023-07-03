package controller;

import dao.AppointmentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SchedulerDashboardController implements Initializable
{   @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    public TableColumn appointmentIdColumn;
    @FXML
    public TableColumn titleColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentDAO.getAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        appointmentsTable.setItems(AppointmentDAO.populateAppointments());

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    public void toCustomerGUI(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/CustomerGUI.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddAppointment(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 700);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void toUpdateAppointment(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 700);
        stage.setTitle("Update Appointment");
        stage.setScene(scene);
        stage.show();
    }


}
