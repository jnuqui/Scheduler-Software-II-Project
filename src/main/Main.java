package main;

import controller.LoginController;
import dao.AppointmentDAO;
import dao.DatabaseAccess;
import helper.CollectionLists;
import helper.JDBC;
import helper.LanguageConversion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TimeZone;

import static helper.CollectionLists.officeLocation;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("Scheduler Login");
        primaryStage.setScene(new Scene(root, 400, 375));
        primaryStage.show();
        LoginController.changeLanguage();
    }

    public static void main(String[] args) throws SQLException, IOException {

        //Open Database connection
        JDBC.openConnection();

        //Change language to French
        //Locale.setDefault(new Locale("fr","FR"));

        //Set default system language.
        Locale.setDefault(new Locale("en", "US"));

       /* Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a language(es, de, or fr. Or en): ");
        String languageCode = keyboard.nextLine();
        if(languageCode.equals("fr"))
            LanguageConversion.changeToFrench();
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        System.out.println(rb.getString("Username"));*/

        //DatabaseAccess.select(3);
        //DatabaseAccess.selectAppointment();

        //ZoneId.getAvailableZoneIds().stream().filter(z->z.contains("America")).sorted().forEach(System.out::println);
        LocalDate myLD = LocalDate.of(2020, 10, 11);
        LocalTime myLT = LocalTime.of(22, 0);
        LocalDateTime myLDT = LocalDateTime.of(myLD, myLT);
        ZoneId myZoneId = ZoneId.systemDefault();
        ZonedDateTime myZDT = ZonedDateTime.of(myLDT, myZoneId);
        System.out.println(myZDT);
        System.out.println(AppointmentDAO.getTimestamp());

        //Setting the system to a different timezone
        //TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));

        /*
        // Filename variable
        String fileName = "login_activity.txt";

        // create filewriter object
        FileWriter fWriter = new FileWriter(fileName, true);

        //create and open file
        PrintWriter outputFile = new PrintWriter(fWriter);

        //write
        outputFile.println("testtesttest123");

        // close file
        outputFile.close();
        System.out.println("File written");
*/



        launch(args);
        JDBC.closeConnection();
    }
}
