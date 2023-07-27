package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;

/** This is the class for CollectionLists. It contains variables and methods that populates combo boxes and helps with
 *  time-related operations. */
public abstract class CollectionLists {

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

    /** The method compares LocalDateTimes passed into it against 8:00AM - 10:00PM ET.
     *  @param startLDT Obtained from either Add or Update Appointment view, this is the start time of a potential
     *                  or updated appointment.
     *  @param endLDT Obtained from either Add or Update Appointment view, this is the end time of a potential
     *                 or updated appointment.
     *  @return Returns a boolean that checks the Eastern Time availability. */
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

    /** The method that adds LocalTime objects into an ObservableList. Times from 00:00 to 23:30 are created in
     *  30 minute intervals. */
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

    /** This is used to populate the Start and End combo boxes in Add and Update Appointment. Contains boolean
     *  flag so that the static list is only loaded once.
     *  @return Returns the ObservableList of LocalTime objects created from the loadTimes method.
     *   */
    public static ObservableList<LocalTime> getTimes() {
        if(!timesLoaded) {
            loadTimes();
            timesLoaded = true;
        }
        return myLT;
    }

    /** The method that adds the officeLocation String array into an ObservableList. The locations are a
     *  predetermined list from the Task Assessment. */
    public static void loadPlaces() {
        for (int i = 0; i <= 3; i++) {
            places.add(officeLocation[i]);
        }
    }

    /** This method is used to populate the location combo boxes in the main appointment dashboard, as well as Add and
     *  Update Appointment views.
     *  @return Returns an ObservableList that is filled with the 4 locations.
     *    */
    public static ObservableList<String> getPlaces() {
        if(!locationLoaded) {
            loadPlaces();
            locationLoaded = true;
        }
        return places;
    }

    /** This is used to set the location combo box in the Update Customer view when a customer is selected to update.
     *  @return Returns the index location of the given location in the location ObservableList.
     *   */
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

    /** The method that adds the typeAppointment String array into an ObservableList. The types are a
     *  predetermined list that are fictional, but utilizes two that are listed in the provided database. */
    public static void loadTypes() {
        for (int i = 0; i <= 3; i++) {
            types.add(typeAppointment[i]);
        }
    }

    /** This method is used to populate the location combo boxes in the main appointment dashboard, as well as Add and
     *  Update Appointment views.
     *  @return Returns an ObservableList that is filled with the 4 locations.
     *   */
    public static ObservableList<String> getTypes() {
        if(!typesLoaded) {
            loadTypes();
            typesLoaded = true;
        }
        return types;
    }

    /** The method that adds the months String array into an ObservableList. All 12 months are listed in this
     *  ObservableList and are used to populate the month combo box to use with the month and type report on the
     *  main dashboard. */
    public static void loadMonths() {
        for (int i = 0; i <= 11; i++) {
            allMonths.add(months[i]);
        }
    }

    /** This method is used to populate the month combo boxes in the main appointment dashboard. A boolean flag is
     *  used to load the ObservableList once.
     *  @return Returns an ObservableList, filled with months of the year. */
    public static ObservableList<String> getMonths() {
        if(!monthsLoaded) {
            loadMonths();
            monthsLoaded = true;
        }
        return allMonths;
    }

    /** This method is used to display a formatted date and time for showing the next appointment upon login, and for
     *  the data recorded in login_activity.txt.
     * @param myLDT A LocalDateTie object representing the current time.
     *  @return Returns a String that uses a DateTimeFormatter to format a LocalDateTime object.
     *   */
    public static String myFormattedDTF (LocalDateTime myLDT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String formattedDate = myLDT.format(format);
        return formattedDate;
    }

    /** This method is used to display a formatted time for showing overlapping time details in Add and Update
     *  Appointment views.
     * @param myLT LocalTime object that comes from the database, converted from a timestamp.
     *  @return Returns a String that uses a DateTimeFormatter to format a LocalTime object.
     *   */
    public static String myFormattedTF (LocalTime myLT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = myLT.format(format);
        return formattedDate;
    }
}
