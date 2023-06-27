package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerController
{

    public void toAddCustomer (ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void toUpdateCustomer (ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 650, 500);
        stage.setTitle("Update Customer");
        stage.setScene(scene);
        stage.show();
    }


    public void toSchedulerDashboard(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/SchedulerDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Scheduler Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
