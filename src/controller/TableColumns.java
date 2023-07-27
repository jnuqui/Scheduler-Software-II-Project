package controller;

/** This is the TableColumns interface for the TableColumns expression code used in the SchedulerDashboardontroller.
 *  It cuts down lines of code that is typically used to initialize TableColumns in a TableView. The lambda expression
 *  is called in three different methods; populateTable, populateTableMonth, and populateTableWeek. */
public interface TableColumns {
    void setColumns(String a, String t, String d, String l, String c, String t2, String st, String et, String cId, String uId);
}
