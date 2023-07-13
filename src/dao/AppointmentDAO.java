package dao;

import controller.SchedulerDashboardController;
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

public class AppointmentDAO
{
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





    public static int selectAppointmentId() throws SQLException {
        String sql = "SELECT MAX(Appointment_ID) FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs.getInt("Appointment_ID");
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


    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }
}
