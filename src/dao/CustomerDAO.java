package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The Abstract Class for AppointmentDAO. This handles database queries related to customers. */
public abstract class CustomerDAO {

    /** This method gets customers from the database to populate the TableView. A query to return all the columns of
     *  Customer and Country name by using JOINS with the Country and First_Level_Divisions database tables and makes
     *  an ObservableList of the results.
     *  @return Returns an ObservableList of Customers.
     *  */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT customers.*, first_level_divisions.* , countries.Country FROM customers JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID  JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String divisionName = rs.getString("Division");
            int divisionIdFK = rs.getInt("Division_ID");
            String country = rs.getString("Country");
            Customer customer = new Customer(customerId, name, address, postalCode, phone, divisionName, divisionIdFK, country);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    /** Takes user input from the AddCustomer view to generate an INSERT statement for customers. Fields from
     *  AddCustomer view are passed through this method and are used as parameters in the INSERT statement.
     *  @param customerName The String that will set the customer's name, obtained from the customer name textfield.
     *  @param address The String that will set the customer's address, obtained from the address textfield.
     *  @param postalCode The String that will set the customer's postalCode, obtained from the postalCode textfield.
     *  @param phone The String that will set the customer's phone, obtained from the phone textfield.
     *  @param divisionIdFK The int value that will set the customer's divisionId in the database. It is obtained from
     *                      the user's selection of the First Level Division combo box.
     *   */
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionIdFK) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,  address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionIdFK);
        ps.executeUpdate();
    }

    /** Gathers user input from the UpdateCustomer view to generate an UPDATE statement for customers. Fields from
     *  UpdateCustomer view are passed through this method and are used as parameters in the UPDATE statement.
     *  @param customerName The String that will set the customer's name, obtained from the customer name textfield.
     *  @param address The String that will set the customer's address, obtained from the address textfield.
     *  @param postalCode The String that will set the customer's postalCode, obtained from the postalCode textfield.
     *  @param phone The String that will set the customer's phone, obtained from the phone textfield.
     *  @param divisionIdFK The int value that will set the customer's divisionId in the database. It is obtained from
     *                        the user's selection of the First Level Division combo box.
     *  @param customerId This comes from the original customer selection from the Customer TableView. It cannot be
     *                    updated, but will be used to determine what customer will be updated.
     *                    */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionIdFK, int customerId) throws SQLException {
        String sql = "UPDATE CUSTOMERS set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?," +
                "                Division_ID = ?" +
                "                where Customer_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,  address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionIdFK);
        ps.setInt(6, customerId);
        ps.executeUpdate();
    }

    /** This used to determine if the given customer has any appointments to control the logic of deleting the customer.
     *  The method uses a customerId as a query for the matching customer.
     *  @param customerId customerId obtained from the customer TableView selection. This is used in the database
     *                    query to determine the existence of any appointments for the customer.
     *  @return Returns a count of appointments.
     *  */
    public static int checkCustomerAppointments(int customerId) throws SQLException
    {
        String sql = "SELECT COUNT(appointment_id) AS RowCount from APPOINTMENTS where customer_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("RowCount");
        return count;
    }

    /** The method used to delete a customer from the database with a DELETE statement.
     * @param customerId Obtained from the customer that is selected from the customer TableView, this customerId is
     *        used as a parameter in the method's sql query to delete the matching customer.
     *        */
    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

    /**  A query is made to the database to fill an ObservableList of String objects with the results. This is used to
     *  populate the customerId combo boxes on Add and Update Appointment views.
     *  @return Returns an ObservableList of Strings, containing Customer ID's. */
    public static ObservableList <String> getCustomerIds() throws SQLException {
        String sql = "SELECT Customer_ID FROM CUSTOMERS";
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String customerId = rs.getString("Customer_ID");
            customerIds.add(customerId);
        }
        return customerIds;
    }

    /** This is used to bring the customerId to the Update Customer view when user selects a customer to update from
     *  the TableView.
     *  @param customerId Obtained from the user's selection of a customer to update from the TableView. It then
     *                    matches it's location in the array and ultimately determines the value in the combo box.
     *  @return Returns the index location of a customerId of the customerId combo box.
     *   */
    public static int getMatchingCustomerId(String customerId) throws SQLException {

        String[] customerIdList = getCustomerIds().toArray(new String[0]);
        int customerIdIndex = 0;
        for (int i = 0; i <= customerIdList.length; i++) {
            if (customerId.equals(customerIdList[i]))
            {
                customerIdIndex = i;
                break;
            }
        }
        return customerIdIndex;
    }
}



