package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CollectionLists {

    //Places for appointments
    private static ObservableList<String> places = FXCollections.observableArrayList();
    public static String[] officeLocation = {"Phoenix, Arizona", "White Plains, New York", "Montreal, Canada", "London, England"};
    public static boolean locationLoaded = false;

    //Times for appointments
    private static ObservableList <LocalTime> myLT = FXCollections.observableArrayList();
    public static LocalTime [] time = new LocalTime[48];
    public static boolean timesLoaded = false;



    public static void loadTimes()
    {
        //for both boxes
        for (int i = 0; i <= 23; i++) {
            {
                time[i] = LocalTime.of((i), 0);
                myLT.add(time[i]);
                time[i + 1] = LocalTime.of((i), 30);
                myLT.add(time[i + 1]);
            }
        }
    }

    public static ObservableList<LocalTime> getTimes() {
        if(!timesLoaded) {
            loadTimes();
            timesLoaded = true;
        }
        return myLT;
    }

    public static void loadPlaces() {
        for (int i = 0; i <= 3; i++) {
            places.add(officeLocation[i]);
        }
    }

    public static ObservableList<String> getPlaces() {
        if(!locationLoaded) {
            loadPlaces();
            locationLoaded = true;
        }
        return places;
    }

    public static int returnUpdateLocation(String location) {
        int locationIndex = 0;
        for (int i = 0; i <= 3; i++) {
            if (location.equals(officeLocation[i]))
            {
                locationIndex = i;
                break;
            }
        }
        return locationIndex;
    }

    public static String myFormattedDTF (LocalDateTime myLDT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String formattedDate = myLDT.format(format);
        return formattedDate;
    }

    public static String myFormattedTF (LocalTime myLT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = myLT.format(format);
        return formattedDate;
    }

    public static String myFormattedHour (LocalTime myLT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH");
        String formattedDate = myLT.format(format);
        return formattedDate;
    }

    public static String myFormattedMin (LocalTime myLT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("mm");
        String formattedDate = myLT.format(format);
        return formattedDate;
    }
}
