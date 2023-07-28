package dao;

import helper.CollectionLists;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** The Abstract Class for AppointmentDAO. This handles database queries related to appointments. */
public abstract class AppointmentDAO
{
    /** Gets all appointments for the main appointment view. The method uses a
     *  database query to get all the columns of the appointments table, but makes a join
     *  with the contacts table to include Contact's Name.
     *  @return Returns an ObservableList of Appointments.
     *  */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*, contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /** Gets appointments for the main appointment view, filtered by the current month.
     *  The method uses a database query to get the same columns as getAppointments, but the current
     *  date is passed into the query as parameters to retrieve relevant appointments.
     *  @return Returns an ObservableList of Appointments that occur in the current month.
     *   */
    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now();
        Timestamp todayTS = Timestamp.valueOf(today);
        String sql = "SELECT appointments.*, contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE MONTH(`Start`) = MONTH(?) AND YEAR(`Start`) = YEAR(?)\n" +
                "ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, todayTS);
        ps.setTimestamp(2, todayTS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /** Gets appointments for the main appointment view, filtered by the upcoming week of the current
     *  date. The method uses a database query to get the same columns as getAppointments, but both the current
     *  date and current date plus 7 days are passed into the query as parameters to retrieve relevant
     *  appointments.
     *  @return Returns an ObservableList of Appointments that are within the next seven days.
     *  */
    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now();
        String sql = "SELECT appointments.*, contacts.Contact_Name" +
        " FROM appointments" +
        " JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID" +
        " WHERE DATE(`Start`) >= ? AND DATE(`End`) <= (?)" +
        " ORDER BY Appointment_ID ASC";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(today));
        ps.setTimestamp(2, Timestamp.valueOf(today.plusDays(7)));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /** Gathers user input from the AddAppointment view to generate an INSERT statement for appointments. Fields from
     *  AddAppointment view are passed through this method and are used as parameters in the INSERT statement.
     *  @param title This is the title that the user enters into titleTextfield. It will be the value inserted as the
     *               appointment's title.
     *  @param description This is the description that the user enters into descriptionTextField. It will be the
     *                     value inserted as the appointment's description.
     *  @param location This is the location selected in the locationComboBox. It will be inserted as the appointment's
     *                  location.
     *  @param type This is the type of appointment selected in the typeComboBox. It will be inserted as the
     *              appointment's type.
     *  @param tsStart This is the timestamp of the appointment's start time. It is collected from the DatePicker and
     *                 combo box as a LocalDateTime object and then converted into a timestamp for the insert statement.
     *  @param tsEnd This is the timestamp of the appointment's end time. It is collected from the DatePicker and
     *                  combo box as a LocalDateTime object and then converted into a timestamp for the insert statement.
     *  @param customerId This is the customerId selected in the customerIdComboBox. It will be inserted as the
     *                    appointment's customerId.
     *  @param userId This is the userId selected in the userIdComboBox. It will be inserted as the
     *                          appointment's userId.
     *  @param contactId  This is the contactId to insert as the appointment's contactId. This parameter is obtained
     *                   through the selection of a name in the contactComboBox, which corresponds to the matching
     *                   contactId of that contact.
     *   */
    public static void insertAppointment(String title, String description, String location, String type, Timestamp tsStart, Timestamp tsEnd, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,  description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, tsStart);
        ps.setTimestamp(6, tsEnd);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.executeUpdate();
    }

    /** Gathers user input from the UpdateAppointment view to generate an UPDATE statement for appointments. Fields from
     *  UPDATEAppointment view are passed through this method and are used as parameters in the INSERT statement.
     *  @param title This is the title that the user enters into titleTextfield. It will be the value that updates the
     *               appointment's title.
     *  @param description This is the description that the user enters into descriptionTextField. It will be the
     *                     value that updates the appointment's description.
     *  @param location This is the location selected in the locationComboBox. It will be updated as the appointment's
     *                  location.
     *  @param type This is the type of appointment selected in the typeComboBox. It will be updated as the
     *              appointment's type.
     *  @param tsStart This is the timestamp of the appointment's start time. It is collected from the DatePicker and
     *                 combo box as a LocalDateTime object and then converted into a timestamp for the update statement.
     *  @param tsEnd This is the timestamp of the appointment's end time. It is collected from the DatePicker and
     *                  combo box as a LocalDateTime object and then converted into a timestamp for the update statement.
     *  @param customerId This is the customerId selected in the customerIdComboBox. It will be updated as the
     *                    appointment's customerId.
     *  @param userId This is the userId selected in the userIdComboBox. It will be updated as the
     *                          appointment's userId.
     *  @param contactId  This is the contactId to update as the appointment's contactId. This parameter is obtained
     *                   through the selection of a name in the contactComboBox, which corresponds to the matching
     *                   contactId of that contact.
     * @param appointmentId This is the appointmentId of the appointment to update. It is first collected when the
     *                      user selects an appointment from the main view. It populates the appointmentIdTextfield,
     *                      but cannot be changed by the user.
     *   */
    public static void updateAppointment(String title, String description, String location, String type, Timestamp tsStart, Timestamp tsEnd, int customerId, int userId, int contactId, int appointmentId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS\n" +
                "set Title = ?, Description = ?,\n" +
                "Location = ?, Type = ?,\n" +
                "Start = ?, End = ?, \n" +
                "Customer_ID = ?, User_ID = ?,\n" +
                "Contact_ID = ?\n" +
                "where appointment_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,  description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, tsStart);
        ps.setTimestamp(6, tsEnd);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, appointmentId);
        ps.executeUpdate();
    }

    /** This method deletes the appointment that the user selects. It takes the appointmentId of the selected
     *  appointment and executes a DELETE statement to remove it from the database.
     *  @param appointmentId This is the appointmentId from the selected appointment in the appointment TableView.
     *                       It is used as a parameter to help execute the WHERE condition to delete the matching
     *                       appointment from the database. */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    /** Checks if there is an appointment happening within 15 minutes. This method is called after the user
     * successfully logs into the application.
     * @param nowLDT A LocalDateTime object of the date and time of login is used as a parameter of the database
     *              query. It is also used so that the plusMinutes method can be used on it for the query.
     * @return Returns an Appointment to use for a custom message if found; returns a null appointment if not. */
    public static Appointment checkAppointment(LocalDateTime nowLDT) throws SQLException {
        String sql = "SELECT *\n" +
                "FROM appointments\n" +
                "WHERE Start >= ? AND Start <= ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(nowLDT));
        ps.setTimestamp(2, Timestamp.valueOf(nowLDT.plusMinutes(15)));
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            Appointment nullAppointment = new Appointment();
            return nullAppointment;
        } else {
            rs.next();
            int appointmentId = rs.getInt("Appointment_ID");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            Appointment nextAppointment = new Appointment(appointmentId, startTime, endTime);
            return nextAppointment;
        }
    }

    /**  This method checks if a customer has overlapping appointment times against the user's input. It checks if a
     *  customer has an existing appointment that conflicts with a potential INSERT statement. If there are no results,
     *  "No" is returned. Otherwise, a String is concatenated to describe the details of the appointment overlap.
     *  @param startLDT A LocalDateTime that comes from the AddAppointment or UpdateAppointment form, used as the
     *                  starting time range of an overlapping appointment.
     *  @param endLDT A LocalDateTime that comes from the AddAppointment or UpdateAppointment form, used as the
     *                    ending time range of an overlapping appointment.
     *  @param customerId The customerId of the customer to check for. Since the purpose of the method is to check
     *                    for overlapping appointments for the customer only.
     *  @return Returns a String of appointment information that is a scheduling overlap; String "No" if none are found.
     *   */
    public static String checkAppointmentOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int customerId) throws SQLException {
        String sql = "SELECT Customer_ID, Start, End FROM appointments WHERE ((Start < ?) AND (End > ?)) AND Customer_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(endLDT));
        ps.setTimestamp(2, Timestamp.valueOf(startLDT));
        ps.setInt(3, customerId);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerIdFound = rs.getInt("Customer_ID");
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No";
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }
            return "Overlaps with appointment (" + CollectionLists.myFormattedTF(startConflictTime) + " - " + CollectionLists.myFormattedTF(endConflictTime) + ") for Customer_ID: " + customerIdFound + ".";
        }
    }

    /** This method checks if a customer has overlapping appointment times against the user's input. It checks if a
     *  customer has an existing appointment that conflicts with a potential UPDATE statement.
     *  If there are no results, "No" is returned. Otherwise, a String is concatenated to describe the details of the
     *   appointment overlap. This query omits the appointment that was selected to update so that appointment
     *  itself can be updated, but still can be compared to other appointments that the customer may have.
     *  This allows also allows an appointment update that  doesn't have a changed time, but other fields change.
     *  @param startLDT A LocalDateTime that comes from the AddAppointment or UpdateAppointment form, used as the
     *                  starting time range of an overlapping appointment.
     *  @param endLDT A LocalDateTime that comes from the AddAppointment or UpdateAppointment form, used as the
     *      *                  ending time range of an overlapping appointment.
     *  @param customerId The customerId of the customer to check for. Since the purpose of the method is to check
     *                    for overlapping appointments for the customer only.
     *  @param appointmentId The appointmentId that is selected to update. The query uses this value to omit
     *                      in the query results so that the original update times are not considered overlaps.
     *  @return Returns a String of appointment information that is a scheduling overlap.
     *   */
    public static String checkUpdateAppointmentOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int customerId, int appointmentId) throws SQLException {
        String sql = "SELECT Appointment_ID, Customer_ID, Start, End FROM appointments WHERE ((Start < ?) AND (End > ?)) AND Customer_ID = ? AND NOT Appointment_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(endLDT));
        ps.setTimestamp(2, Timestamp.valueOf(startLDT));
        ps.setInt(3, customerId);
        ps.setInt(4, appointmentId);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerIdFound = rs.getInt("Customer_ID");
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No";
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }
            return "Overlaps with appointment (" + CollectionLists.myFormattedTF(startConflictTime) + " - " + CollectionLists.myFormattedTF(endConflictTime) + ") for Customer_ID: " + customerIdFound + ".";
        }
    }
    /** This method queries the database to return a count column of appointments that match a month and type,
     *  which is used to help create appointment objects for the report TableView.
     *  @param month String that is obtained from user selection from the month combo box. Used for the database query.
     *  @param type String that is obtained from user selection from the type combo box. Used for the database query.
     *  @return Returns an ObservableList of Appointments for the month and type report. */
    public static ObservableList<Appointment> getMonthTypeReport(String month, String type) throws SQLException {
        ObservableList<Appointment> monthTypeAppointments = FXCollections.observableArrayList();
        String sql = "SELECT Count(*) FROM APPOINTMENTS WHERE monthname(Start)=? AND Type=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("Count(*)");
        Appointment monthTypeAppointment = new Appointment(month, type, count);
        monthTypeAppointments.add(monthTypeAppointment);
        return monthTypeAppointments;
    }

    /** This method queries the database to return appointments that match the given contact.
     *  @param contactNameBox String that is obtained from user selection from the contact combo box. This is used to
     *                        query the database to retrieve appointments that only match this name.
     *  @return Returns an ObservableList of Appointments for the contact report.
     *   */
    public static ObservableList<Appointment> getContactReport(String contactNameBox) throws SQLException {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        String sql =     "SELECT appointments.*, contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE Contact_Name = ? ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactNameBox);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            contactAppointments.add(appointment);
        }
        return contactAppointments;
    }

    /** This method queries the database to return appointments that match the given location.
     *  @param locationFromBox String that is obtained from user selection from the location combo box. This is used to
     *                        query the database to retrieve appointments that only match this location.
     *  @return Returns an ObservableList of Appointments for the location report.
     *   */
    public static ObservableList<Appointment> getLocationReport(String locationFromBox) throws SQLException {
        ObservableList<Appointment> locationAppointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*, contacts.Contact_Name\n" +
                "                FROM appointments\n" +
                "                JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "                WHERE Location = ? ORDER BY Appointment_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, locationFromBox);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, contactName, type, startTime, endTime, customerId, contactId, userId);
            locationAppointments.add(appointment);
        }
        return locationAppointments;
    }
}