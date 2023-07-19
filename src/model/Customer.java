package model;

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

    public Customer(int customerId, String name, String address, String postalCode, String phone, int divisionIdFK,
    String divisionName, String country, int countryId)
    {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionIdFK = divisionIdFK;
        this.divisionName = divisionName;
        this.country = country;
        this.countryId = countryId;
    }

    public Customer(int customerId, String name, String address, String postalCode, String phone, int divisionIdFK)
    {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionIdFK = divisionIdFK;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionIdFK() {
        return divisionIdFK;
    }

    public void setDivisionIdFK(int divisionIdFK) {
        this.divisionIdFK = divisionIdFK;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString()
{
    return name;
}

}

