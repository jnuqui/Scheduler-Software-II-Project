package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class CollectionLists {

    //Places for appointments
    private static ObservableList<String> places = FXCollections.observableArrayList();
    public static String[] officeLocation = {"Phoenix, Arizona", "White Plains, New York", "Montreal, Canada", "London, England"};
    public static boolean locationLoaded = false;

    //Types for appointments
    private static ObservableList<String> types = FXCollections.observableArrayList();
    public static String[] typeAppointment = {"Finances", "Planning Session", "De-Briefing", "Fun" };
    public static boolean typesLoaded = false;

    //Times for appointments
    private static ObservableList <LocalTime> myLT = FXCollections.observableArrayList();
    public static LocalTime [] time = new LocalTime[48];
    public static boolean timesLoaded = false;

    //Months for report 1
    private static ObservableList <String> allMonths = FXCollections.observableArrayList();
    public static String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static boolean monthsLoaded = false;

    //Creating the appointment time range for the business
    public static boolean checkTimeRange(LocalDateTime startLDT, LocalDateTime endLDT)
    {
        boolean withinTime = false;
        ZoneId myBusinessZone = ZoneId.of("America/New_York");
        ZoneId myZoneId = ZoneId.systemDefault();
        LocalDateTime appointmentDateTime = startLDT;
        LocalDate appointmentDate = appointmentDateTime.toLocalDate();

        //Open hour at ET office, perceived from any timezone
        LocalTime openHour = LocalTime.of(8, 0);
        LocalDateTime businessOpenDT = LocalDateTime.of(appointmentDate, openHour);
        ZonedDateTime zonedBusinessOpenDT = businessOpenDT.atZone(myBusinessZone);
        ZonedDateTime myZonedOpenDT = zonedBusinessOpenDT.withZoneSameInstant(myZoneId);
        LocalDateTime myOpenLDT = myZonedOpenDT.toLocalDateTime();

        //Closed hour at ET office, perceived from any timezone
        LocalTime closedHour = LocalTime.of(22, 0);
        LocalDateTime businessClosedDT = LocalDateTime.of(appointmentDate, closedHour);
        ZonedDateTime zonedBusinessClosedDT = businessClosedDT.atZone(myBusinessZone);
        ZonedDateTime myZonedClosedDT = zonedBusinessClosedDT.withZoneSameInstant(myZoneId);
        LocalDateTime myClosedLDT = myZonedClosedDT.toLocalDateTime();

        if (startLDT.isAfter(myOpenLDT) && (endLDT.isBefore(myClosedLDT)))
        {
            withinTime = true;
        }

        if (startLDT.isEqual(myOpenLDT) || (endLDT.isEqual(myClosedLDT)))
        {
            withinTime = true;
        }
        return withinTime;
    }

    public static void loadTimes()
    {
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

    public static void loadTypes() {
        for (int i = 0; i <= 3; i++) {
            types.add(typeAppointment[i]);
        }
    }

    public static ObservableList<String> getTypes() {
        if(!typesLoaded) {
            loadTypes();
            typesLoaded = true;
        }
        return types;
    }

    //begin month methods
    public static void loadMonths() {
        for (int i = 0; i <= 11; i++) {
            allMonths.add(months[i]);
        }
    }

    public static ObservableList<String> getMonths() {
        if(!monthsLoaded) {
            loadMonths();
            monthsLoaded = true;
        }
        return allMonths;
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
}
