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
import java.time.ZonedDateTime;

public class AppointmentDAO
{
    //this works - don't want to break it so I'm going to copy and paste it to try a query within the while loop
    /*public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, startTime, endTime, customerId, contactId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }*/

    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, startTime, endTime, customerId, contactId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsv2() throws SQLException {
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

    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*, contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE MONTH(`Start`) = MONTH(current_date()) AND YEAR(`Start`) = YEAR(current_date())\n" +
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

    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*, contacts.Contact_Name" +
        " FROM appointments" +
        " JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID" +
        " WHERE DATE(`Start`) >= NOW() AND DATE(`End`) <= (NOW() + INTERVAL 7 DAY)" +
        " ORDER BY Appointment_ID ASC";
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



    public static Timestamp getTimestamp() throws SQLException {
        String sql = "SELECT Start FROM APPOINTMENTS WHERE Appointment_ID = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Timestamp myTS = null;
        if (rs.next()) {
            myTS = rs.getTimestamp("Start");
        }
        return myTS;
    }

//INSERT INTO appointments VALUES(1, 'title', 'description', 'location', 'Planning Session', '2020-05-28 12:00:00', '2020-05-28 13:00:00', NOW(), 'script', NOW(), 'script', 1, 1, 3);
    public static void insertTest(Timestamp timestamp, Timestamp endstamp) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, "awesome title");
        ps.setString(2, "interesting description");
        ps.setString(3, "remote location");
        ps.setString(4, "very important");
        ps.setTimestamp(5, timestamp);
        ps.setTimestamp(6, endstamp);
        ps.setInt(7, 1);
        ps.setInt(8, 1);
        ps.setInt(9, 3);
        ps.executeUpdate();
    }

//hello

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

    public static void getAppointment(int appointmentId) throws SQLException {
        String sql = "SELECT Appointment_ID FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
    }


    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }


    public static Appointment checkAppointment(LocalDateTime nowLDT) throws SQLException {
        String sql = "SELECT *\n" +
                "FROM appointments\n" +
                "WHERE Start >= ? AND Start <= date_add(?, INTERVAL 1 hour);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(nowLDT));
        ps.setTimestamp(2, Timestamp.valueOf(nowLDT));
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

    public static String checkAppointmentOverlap(LocalDateTime startLDT, LocalDateTime endLDT) throws SQLException {
        String sql = "SELECT Start, End FROM appointments WHERE (Start BETWEEN ? AND ?) OR (End BETWEEN ? AND ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(startLDT));
        ps.setTimestamp(2, Timestamp.valueOf(endLDT));
        ps.setTimestamp(3, Timestamp.valueOf(startLDT));
        ps.setTimestamp(4, Timestamp.valueOf(endLDT));
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No conflict";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("End").toLocalDateTime();
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No conflict";
            /*if (startConflictDateTime.equals(startLDT) || startConflictDateTime.equals(endLDT)
            || endConflictDateTime.equals(startLDT) || endConflictDateTime.equals(endLDT))
            {
                return overlapReport;
            }*/
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }

            return "Overlaps with appointment (" + CollectionLists.myFormattedTF(startConflictTime) + " - " + CollectionLists.myFormattedTF(endConflictTime) + ")";
        }
    }
}
