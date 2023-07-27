package model;

/** Class for the Country class model. Contains constructors to create Country objects and methods to
 *  set and get their attributes. */
public class Country
{
    int countryId;
    String countryName;

    /** A constructor for country objects. It is used to create country objects that are used country combo boxes
     *  @param countryId country name as a String.
     * @param countryName country name as a String.
     *  */
    public Country(int countryId, String countryName)
    {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /** The method to return the int of an Country object's countryId.
     *  @return Returns int of a countryId */
    public int getCountryId() {
        return countryId;
    }

    /** The method used to set the country object's countryId.
     *  @param countryId The countryId to set the country object's countryId. */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** The method to return the String of an Country object's countryName.
     *  @return Returns String of countryName */
    public String getCountryName() {
        return countryName;
    }

    /** The method used to set the country object's countryName.
     *  @param countryName The String to set the country object's countryName. */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** String method that has an Override to return countryName instead of the object model data.
     *  @return Returns countryName. */
    @Override
    public String toString() {
        return countryName;
    }
}



