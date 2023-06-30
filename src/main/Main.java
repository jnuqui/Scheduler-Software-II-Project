package main;

import controller.LoginController;
import dao.DatabaseAccess;
import helper.JDBC;
import helper.LanguageConversion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

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


    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();
        Locale.setDefault(new Locale("fr","FR"));

       /* Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a language(es, de, or fr. Or en): ");
        String languageCode = keyboard.nextLine();
        if(languageCode.equals("fr"))
            LanguageConversion.changeToFrench();
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        System.out.println(rb.getString("Username"));*/

        DatabaseAccess.select(3);
        launch(args);
        JDBC.closeConnection();
    }
}
