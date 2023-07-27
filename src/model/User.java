package model;

/** Class for the User class model. Contains constructors to create User objects and
 *  methods to set and get their attributes. */
public class User
{
    int userID;
    String userName;
    String password;

    /** A constructor for a User object. */
    public User()
    {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

