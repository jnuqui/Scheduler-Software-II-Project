package controller;

import dao.AppointmentDAO;
import helper.CollectionLists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    ZoneId myUTC = ZoneId.of("UTC");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelLocation.setText(myZoneId.toString());
        labelLocationDetect.setText(rb.getString(labelLocationDetect.getText()));
        labelUsername.setText(rb.getString(labelUsername.getText()));
        labelPassword.setText(rb.getString(labelPassword.getText()));
        buttonLogin.setText(rb.getString(buttonLogin.getText()));
    }

    public void login(ActionEvent actionEvent) throws IOException, SQLException {

            if ((textfieldUsername.getText().equals("test") && textfieldPassword.getText().equals("test")))
            {
                recordActivity(textfieldUsername.getText(), "successful");
            }
            else
            {
                recordActivity(textfieldUsername.getText(), "unsuccessful");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
                alert.setHeaderText("Error");
                alert.setContentText("Wrong username or password");
                return;
            }

        Parent root = FXMLLoader.load(getClass().getResource("../view/SchedulerDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("Scheduler Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        showNextAppointment();
    }

    public void recordActivity(String userAttempt, String attempt) throws IOException {
        LocalDateTime myLDT = LocalDateTime.now();
        String loginAttemptTime = CollectionLists.myFormattedDTF(myLDT);
        String loginActivity = "Login by \"" + userAttempt + "\" was " + attempt + " at " + loginAttemptTime + " " + myZoneId.toString();

        // Filename variable
        String fileName = "login_activity.txt";

        // create filewriter object
        FileWriter fWriter = new FileWriter(fileName, true);

        //create and open file
        PrintWriter outputFile = new PrintWriter(fWriter);

        //write
        outputFile.println(loginActivity);

        // close file
        outputFile.close();
    }

    public void showNextAppointment() throws SQLException {
        Appointment nextAppointment = AppointmentDAO.checkAppointment(LocalDateTime.now());
        if (nextAppointment.getStartTime() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.show();
            alert.setHeaderText("Upcoming Appointment");
            alert.setContentText("No upcoming appointments.");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.show();
            alert.setHeaderText("Upcoming Appointment");
            alert.setContentText("Appointment ID: " + nextAppointment.getAppointmentId() + " on " + CollectionLists.myFormattedDTF(nextAppointment.getStartTime()));
        }
    }

    public void toSchedulerDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/SchedulerDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("Scheduler Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void changeLanguage()
    {
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        //labelUsername.setText(rb.getString("Username"));
        //textfieldUsername.setText("hey");
    }
}
