package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.time.LocalTime;

public class CollectionLists {
    private static ObservableList<String> places = FXCollections.observableArrayList();
    static String[] officeLocation = {"Phoenix, Arizona", "White Plains, New York", "Montreal, Canada", "London, England"};

    public static void loadPlaces() {
        for (int i = 0; i <= 3; i++) {
            places.add(officeLocation[i]);
        }
    }

    public static ObservableList<String> getPlaces() {
        loadPlaces();
        return places;
    }

}
