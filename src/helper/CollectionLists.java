package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CollectionLists {
    private static ObservableList<String> places = FXCollections.observableArrayList();
    public static String[] officeLocation = {"Phoenix, Arizona", "White Plains, New York", "Montreal, Canada", "London, England"};
    public static boolean locationLoaded = false;

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

    //might not need this
    public static String myFormattedDTF (LocalDateTime myLDT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String formattedDate = myLDT.format(format);
        return formattedDate;
    }
}
