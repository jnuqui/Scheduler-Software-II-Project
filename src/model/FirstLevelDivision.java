package model;

/** Class for the FirstLevelDivision class model. Contains constructors to create FirstLevelDivision objects and
 *  methods to set and get their attributes. */
public class FirstLevelDivision
{
    int divisionId;
    String divisionName;
    int countryIdFK;

    /** A constructor for FirstLevelDivision objects. It is used to create FirstLevelDivision objects that are used
     *  FirstLevelDivision combo boxes. The values come from a database query to the First Level Division table.
     *  @param divisionId The divisionId of a FirstLevelDivision
     *  @param divisionName divisionName name as a String.
     * @param countryIdFK The countryId foreign key of the First Level Division table. */
    public FirstLevelDivision(int divisionId, String divisionName, int countryIdFK)
    {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryIdFK = countryIdFK;
    }

    /** A constructor for FirstLevelDivision objects. It is used in the UpdateCustomer view to display the
     *  FirstLevelDivision that the selected customer has.
     *  @param divisionName divisionName name as a String. */
    public FirstLevelDivision(String divisionName)
    {
        this.divisionName = divisionName;
    }

    /** The method to return the int of a FirstLevelDivision object's divisionId.
     *  @return Returns int of a divisionId */
    public int getDivisionId() {
        return divisionId;
    }

    /** The method used to set the FirstLevelDivision object's contactId.
     *  @param divisionId The divisionId to set the FirstLevelDivision object's divisionId. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** The method to return the String of an FirstLevelDivision object's divisionName.
     *  @return Returns String of divisionName */
    public String getDivisionName() {
        return divisionName;
    }

    /** The method used to set the FirstLevelDivision object's divisionName.
     *  @param divisionName The String to set the FirstLevelDivision object's divisionName. */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** The method to return the int of a FirstLevelDivision object's countryIdFK.
     *  @return Returns int of a countryIdFK */
    public int getCountryIdFK() {
        return countryIdFK;
    }

    /** The method used to set the FirstLevelDivision object's countryIdFK.
     *  @param countryIdFK The countryIdFK to set the FirstLevelDivision object's countryIdFK. */
    public void setCountryIdFK(int countryIdFK) {
        this.countryIdFK = countryIdFK;
    }

    /** String method that has an Override to return the FirstLevelDivision's divisionName instead of the object
     * model data.
     *  @return Returns divisionName. */
    @Override
    public String toString()
    {
        return divisionName;
    }
}
