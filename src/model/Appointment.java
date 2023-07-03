package model;

public class Appointment
{
    int appointmentId;
    String title;
    String description;
    String location;
    String type;
    String startTime;
    String endTime;
    int customerId;
    int contactId;

    public Appointment(int appointmentId, String title)
    {
        this.appointmentId = appointmentId;
        this.title = title;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}




