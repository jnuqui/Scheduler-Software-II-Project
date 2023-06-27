package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchedulerDashboardController
{
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
