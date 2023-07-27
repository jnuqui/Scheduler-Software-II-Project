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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**This is the controller for the "Login" view. It handles all methods related to login and checking for an upcoming
 * appointment. */
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
    private Label labelUsername;
    @FXML
    private TextField textfieldUsername;
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
    ZoneId myZoneId = ZoneId.systemDefault();

    String error = "Error";
    String errorDetail = "Wrong username or password.";


    /**This is the initialize method for the Login controller. The labels are set according to the Locale of the
     * machine running the Java program. En and Fr are supported.
     *  @param resourceBundle The resourceBundle for initialize
     *  @param url The url for initialize */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelLocation.setText(myZoneId.toString());
        labelLocationDetect.setText(rb.getString(labelLocationDetect.getText()));
        labelUsername.setText(rb.getString(labelUsername.getText()));
        labelPassword.setText(rb.getString(labelPassword.getText()));
        buttonLogin.setText(rb.getString(buttonLogin.getText()));
    }


    /**This method handles the user's login activity and entrance into the program. An if-else statement evaluates
     * the user's input in both textfields and launches the main appointment dashboard. If the username/password
     * combination does not match, an alert appears to inform the user.
     * @param actionEvent The action when the button is clicked. */
    public void login(ActionEvent actionEvent) throws IOException, SQLException {

            if ((textfieldUsername.getText().equals("test") && textfieldPassword.getText().equals("test"))) {
                recordActivity(textfieldUsername.getText(), "successful");
            } else {
                recordActivity(textfieldUsername.getText(), "unsuccessful");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
                alert.setHeaderText(rb.getString(error));
                alert.setContentText(rb.getString(errorDetail));
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

    /** This method records the user's attempts. In both success and failure of login, the
     *  user's attempts are recorded in the login_activity.txt file with timestamps.
     *  @param userAttempt If login is successful, this String is passed from the login method. The String is
     *                     concatenated into a custom message in a popup window.
     *  @param attempt If login is unsucessful, this String is passed from the login method. The String is
     *                          concatenated into a custom message in a popup window.*/
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

    /** This method determines if there is an upcoming appointment within 15 minutes when login is successful.
     *  The current time of the user's machine is used in a query to the database to check for appointments. If there
     *  is an appointment, a custom message in an information popup appears. If not, a popup indicates that there is
     *  not an appointment. */
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
}
