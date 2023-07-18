package model;

public class FirstLevelDivision
{
    int divisionId;
    String divisionName;
    int countryIdFK;

    public FirstLevelDivision(int divisionId, String divisionName, int countryIdFK)
    {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryIdFK = countryIdFK;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getCountryIdFK() {
        return countryIdFK;
    }

    public void setCountryIdFK(int countryIdFK) {
        this.countryIdFK = countryIdFK;
    }
}
