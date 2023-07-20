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

        ZoneId myZoneId = ZoneId.systemDefault();

        //Setting the system to a different timezone
        //TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));

        //Perfect working example
        /*
        ZoneId myBusinessZone = ZoneId.of("America/New_York");
        LocalDateTime myTestLDT = LocalDateTime.of(2023, 7, 19, 10, 0);
        ZonedDateTime myTestZDT = myTestLDT.atZone(myZoneId);
        ZonedDateTime myNewZDT = myTestZDT.withZoneSameInstant(myBusinessZone);
        LocalDateTime myNewLDT = myNewZDT.toLocalDateTime();

        System.out.println("10AM from Local time should be 12PM in New York Time. It should be 12 below this line.");
        System.out.println(CollectionLists.myFormattedDTF(myNewLDT));
        System.out.println(myNewLDT.getDayOfWeek());
        */

        ZoneId myBusinessZone = ZoneId.of("America/New_York");

        LocalDate today = LocalDate.now();
        LocalTime openHour = LocalTime.of(8, 0);
        LocalDateTime businessDT = LocalDateTime.of(today, openHour);
        ZonedDateTime zonedBusinessDT = businessDT.atZone(myBusinessZone);
        ZonedDateTime myZonedDT = zonedBusinessDT.withZoneSameInstant(myZoneId);
        LocalDateTime myLDT = myZonedDT.toLocalDateTime();
        //The opening hour from Eastern Time, but displayed in Denver time
        LocalTime myNewLT = myLDT.toLocalTime();

        LocalDate todayAppt = LocalDate.now();
        LocalTime apptStart = LocalTime.of(18, 0);
        LocalDateTime myDT = LocalDateTime.of(todayAppt, apptStart);
        ZonedDateTime myZonedDT2 = myDT.atZone(myZoneId);
        ZonedDateTime zonedBusinessDT2 = myZonedDT2.withZoneSameInstant(myBusinessZone);
        LocalDateTime myLDT2 = zonedBusinessDT2.toLocalDateTime();
        LocalTime myNewLT2 = myLDT2.toLocalTime();

        System.out.println("The time that the Eastern Time office is open, but from Denver local Time: " + CollectionLists.myFormattedTF(myNewLT));
        System.out.println("The time that I'm putting in from Denver, but what is being tested at Eastern, which should be 20: " + CollectionLists.myFormattedTF(myNewLT2));

        launch(args);
        JDBC.closeConnection();
    }
}
