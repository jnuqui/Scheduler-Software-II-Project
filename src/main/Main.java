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

        //Change Locale to French
        //Locale.setDefault(new Locale("fr","FR"));

        //(String name, int number) ->

        launch(args);
        JDBC.closeConnection();
    }
}
