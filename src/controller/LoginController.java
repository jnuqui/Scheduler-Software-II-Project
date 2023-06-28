package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController
{
    public static Label labelUsername;
    public static TextField textfieldUsername;

    public void toSchedulerDashboard(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/SchedulerDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 650, 500);
        stage.setTitle("Scheduler Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeLanguage()
    {
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        //labelUsername.setText(rb.getString("Username"));
        //textfieldUsername.setText("hey");
    }

}
