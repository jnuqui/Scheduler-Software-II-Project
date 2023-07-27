package model;

/** Class for the Customer class model. Contains constructors to create Customer objects and methods to
 *  set and get their attributes. */
public class Customer
{
    int customerId;
    String name;
    String address;
    String postalCode;
    String phone;
    int divisionIdFK;
    String divisionName;
    String country;
    int countryId;

    /** The main constructor for Customer objects. It is used to create Customer objects that are used
     *  primarily for the TableView. Each attribute is used to display a useful column detail. Each of these attributes
     *  come from a database query of the Customers table, except country, which comes from a JOIN query on the Country
     *  table.
     *  @param customerId The int customerId that is found in the customers table in the database.
     *  @param name The String of name that is found in the customers table in the database.
     *  @param address The String of address that is found in the customers table in the database.
     *  @param postalCode The String of postalCode that is found in the customers table in the database.
     *  @param phone The String of phone that is found in the customers table in the database.
     *  @param divisionName The String divisionName that is found in the First Level Divisions table in the database.
     *  @param divisionIdFK The int divisionIdFK that is found in the customers table in the database
     *  @param country The String of country that is found in the countries table in the database through a JOIN query.
     *  */
    public Customer(int customerId, String name, String address, String postalCode, String phone, String divisionName, int divisionIdFK, String country)
    {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionName = divisionName;
        this.divisionIdFK = divisionIdFK;
        this.country = country;
    }

    /** The method to return the int of a customer object's customerId.
     *  @return Returns int of a customerId */
    public int getCustomerId() {
        return customerId;
    }

    /** The method used to set the customer object's contactId.
     *  @param customerId The customerId to set the customer object's customerId. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** The method to return the String of a customer object's name.
     *  @return Returns String of the customer's name */
    public String getName() {
        return name;
    }

    /** The method used to set the customer object's name.
     *  @param name The name to set the customer object's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** The method to return the String of a customer object's address.
     *  @return Returns String of the customer's address */
    public String getAddress() {
        return address;
    }

    /** The method used to set the customer object's address.
     *  @param address The address to set the customer object's address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** The method to return the String of a customer object's postalCode.
     *  @return Returns String of the customer's postalCode */
    public String getPostalCode() {
        return postalCode;
    }

    /** The method used to set the customer object's postalCode.
     *  @param postalCode The postalCode to set the customer object's postalCode. */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** The method to return the String of a customer object's phone.
     *  @return Returns String of the customer's phone */
    public String getPhone() {
        return phone;
    }

    /** The method used to set the customer object's phone.
     *  @param phone The phone to set the customer object's phone. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** The method to return the int of a customer object's divisionIdFK.
     *  @return Returns int of a divisionIdFK */
    public int getDivisionIdFK() {
        return divisionIdFK;
    }

    /** The method used to set the customer object's divisionIdFK.
     *  @param divisionIdFK The divisionIdFK to set the customer object's divisionIdFK. */
    public void setDivisionIdFK(int divisionIdFK) {
        this.divisionIdFK = divisionIdFK;
    }

    /** The method to return the String of a customer object's divisionName.
     *  @return Returns String of the customer's divisionName */
    public String getDivisionName() {
        return divisionName;
    }

    /** The method used to set the customer object's divisionName.
     *  @param divisionName The divisionName to set the customer object's divisionName. */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** The method to return the String of a customer object's country.
     *  @return Returns String of the customer's country */
    public String getCountry() {
        return country;
    }

    /** The method used to set the customer object's country.
     *  @param country The country to set the customer object's country. */
    public void setCountry(String country) {
        this.country = country;
    }

    /** The method to return the int of a customer object's countryId.
     *  @return Returns int of a countryId */
    public int getCountryId() {
        return countryId;
    }

    /** The method used to set the customer object's countryId.
     *  @param countryId The countryId to set the customer object's countryId. */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** String method that has an Override to return the customer's name instead of the object model data.
     *  @return Returns name. */
    @Override
    public String toString()
{
    return name;
}

}

