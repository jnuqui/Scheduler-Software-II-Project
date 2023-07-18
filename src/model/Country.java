package model;

public class Country
{
    int countryId;
    String countryName;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Country(int countryId, String countryName)
    {
        this.countryId = countryId;
        this.countryName = countryName;
    }
}



