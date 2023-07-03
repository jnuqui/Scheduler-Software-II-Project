package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO
{
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static void getAppointments() throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            Appointment appointment = new Appointment(appointmentId, title);
            allAppointments.add(appointment);
        }
    }

    public static ObservableList<Appointment> populateAppointments()
    {
        return allAppointments;
    }
}
