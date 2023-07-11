package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button buttonLogin;
    @FXML
    private Label labelLocationDetect;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelLocation;
    @FXML
    private TextField textfieldPassword;
    @FXML
    private Button testButton;
    @FXML
    private Label labelUsername;
    @FXML
    private TextField textfieldUsername;
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    ZoneId myZoneId = ZoneId.systemDefault();

    public void toSchedulerDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/SchedulerDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1019, 500);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelLocation.setText(myZoneId.toString());
        labelLocationDetect.setText(rb.getString(labelLocationDetect.getText()));
        labelUsername.setText(rb.getString(labelUsername.getText()));
        labelPassword.setText(rb.getString(labelPassword.getText()));
        buttonLogin.setText(rb.getString(buttonLogin.getText()));
    }
}
