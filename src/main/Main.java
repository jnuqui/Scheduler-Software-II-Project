package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

/** This is the Main class for the Java program. It is responsible for launching the application. */
public class Main extends Application {

    /** The method that launches the login view. It also sets the title and dimensions of the window. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("Scheduler Login");
        primaryStage.setScene(new Scene(root, 400, 375));
        primaryStage.show();
    }

    /** The main method for this program which launches the application. It also contains methods for opening
     *  and closing the connection to the SQL database. */
    public static void main(String[] args) throws SQLException, IOException {

        //Open Database connection
        JDBC.openConnection();

        //Change Locale to French
        //Locale.setDefault(new Locale("fr","FR"));

        launch(args);
        JDBC.closeConnection();
    }
}
