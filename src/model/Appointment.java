package model;

import java.time.LocalDateTime;

/** Class for the Appointment class model. Contains constructors to create Appointment objects and methods to
 *  set and get their attributes. */
public class Appointment
{
    int appointmentId;
    String title;
    String description;
    String location;
    String contactName;
    String type;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int customerId;
    int contactId;
    int userId;

    String month;
    int count;

    /** The main constructor for appointment objects. It is used to create appointment objects that are used
     *  primarily for the TableView. Each attribute is used to display a useful column detail. Each of these attributes
     *  come from a database query of the appointments table. One column (contactName) comes from the contacts table,
     *  which comes from a JOIN query.
     *  @param appointmentId The int appointmentId that is found in the appointment table in the database.
     *  @param title The String title that is found in the appointment table in the database.
     *  @param description The String description that is found in the appointment table in the database.
     *  @param location The String location that is found in the appointment table in the database.
     *  @param contactName The String contactName that is found in the contacts table in the database.
     *  @param type The String type that is found in the appointment table in the database.
     *  @param startTime The LocalDateTime start that is found in the appointment table in the database, converted
     *                   from a timestamp.
     *  @param endTime The LocalDateTime end that is found in the appointment table in the database, converted from
     *                 a timestamp.
     *  @param customerId The int customerId that is found in the appointment table in the database
     *  @param contactId The int contactId that is found in the appointment table in the database
     *  @param userId The int userId that is found in the appointment table in the database
     *   */
    public Appointment(int appointmentId, String title, String description, String location, String contactName, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int contactId, int userId)
    {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.contactId = contactId;
        this.userId = userId;
    }

    /** A constructor for appointment objects. It is used to create an appointment object that is found in the
     *  database that is coming up within 15 minutes. Upon login to the application, the object is ultimately returned
     *  if found.
     *  @param appointmentId The int appointmentId comes from the database
     *  @param startTime The LocalDateTime start that is found in the appointment table in the database, converted
     *                   from a timestamp.
     *  @param endTime The LocalDateTime end that is found in the appointment table in the database, converted from
     *                 a timestamp.
     *   */
    public Appointment(int appointmentId, LocalDateTime startTime, LocalDateTime endTime)
    {
        this.appointmentId = appointmentId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** A constructor appointment objects. It is used to create appointment objects that are used for the
     *  month and type report TableView.
     *  @param month The String of month that is selected in the month combo box.
     *  @param type The String type that is found in the appointment table in the database.
     *  @param count The int count that is found of how many appointments are found in the database, given
     *                month and type.
     *  */
    public Appointment(String month, String type, int count)
    {
        this.month = month;
        this.type = type;
        this. count = count;
    }

    /** A constructor for appointment objects. It is used to create an empty appointment object when an appointment
     *  that does not happen within 15 minutes upon login to the application. It ultimately is used as a flag to
     *  show that an appointment is not coming up.
     *  */
    public Appointment(){

    }

    /** The method to return a String of an appointment's contactName.
     *  @return Returns String contactName */
    public String getContactName() {
        return contactName;
    }

    /** The method used to set an appointment object's contactName.
     *  @param contactName Contact name to set the appointment's contactName. */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** The method to return the int of an appointment's userId.
     *  @return Returns userId */
    public int getUserId() {
        return userId;
    }

    /** The method used to set the appointment object's userId.
     * @param userId userId to set the appointment's userId. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** The method to return the int of an appointment's appointmentId.
     *  @return Returns appointmentId. */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** The method used to set the appointment object's appointmentId.
     *  @param appointmentId The appointmentId to set the appointment's appointmentId. */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** The method to return a String of an appointment's title.
     *  @return Returns title of an appointment object. */
    public String getTitle() {
        return title;
    }

    /** The method to return a String of an appointment's description.
     *  @return Returns description of an appointment object. */
    public String getDescription() {
        return description;
    }

    /** The method used to set the appointment object's description.
     *  @param description The description to set the appointment's description. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** The method to return the String of an Appointment object's location.
     *  @return Returns String of location */
    public String getLocation() {
        return location;
    }

    /** The method used to set the Appointment object's location.
     *  @param location The String to set the Appointment object's location. */
    public void setLocation(String location) {
        this.location = location;
    }

    /** The method to return the String of an Appointment object's type.
     *  @return Returns String of type */
    public String getType() {
        return type;
    }

    /** The method used to set the Appointment object's type.
     *  @param type The String to set the Appointment object's type. */
    public void setType(String type) {
        this.type = type;
    }

    /** The method used to set the Appointment object's startTime.
     *  @param startTime The LocalDateTime to set the Appointment object's startTime. */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** The method used to set the Appointment object's endTime.
     *  @param endTime The LocalDateTime to set the Appointment object's endTime. */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** The method to return the LocalDateTime of an Appointment object's startTime.
     *  @return Returns LocalDateTime of startTime */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** The method to return the String of an Appointment object's month.
     *  @return Returns String of month */
    public String getMonth() {
        return month;
    }

    /** The method used to set the Appointment object's month.
     *  @param month The String to set the Appointment object's month. */
    public void setMonth(String month) {
        this.month = month;
    }

    /** The method to return the int of an appointment's count for the month type report.
     *  @return Returns count of rows. */
    public int getCount() {
        return count;
    }

    /** The method used to set the appointment object's count.
     *  @param count The count to set the appointment's count. */
    public void setCount(int count) {
        this.count = count;
    }

    /** The method to return the LocalDateTime of an Appointment object's endTime.
     *  @return Returns LocalDateTime of endTime */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** The method to return the int of an appointment's customerId.
     *  @return Returns customerId. */
    public int getCustomerId() {
        return customerId;
    }

    /** The method used to set the appointment object's customerId.
     *  @param customerId The customerId to set the appointment's customerId. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** The method to return the int of an appointment's contactId.
     *  @return Returns contactId. */
    public int getContactId() {
        return contactId;
    }

    /** The method used to set the appointment object's contactId.
     *  @param contactId The contactId to set the appointment's contactId. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** The method used to set the Appointment object's title.
     *  @param title The String to set the Appointment object's title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** String method that has an Override to return the Appointment's title instead of the object
     * model data.
     *  @return Returns title. */
    @Override
    public String toString()
    {
        return title;
    }
}




