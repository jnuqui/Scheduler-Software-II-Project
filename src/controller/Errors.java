package controller;

/** This is the Errors interface for the Errors lambda expression used in the AddAppointment controller. It cuts down
 *  lines of code that is typically used in launching alert windows. */
public interface Errors {
    void makeAlert(String d, String m);
}
