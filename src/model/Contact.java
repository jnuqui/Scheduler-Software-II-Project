package model;

/** Class for the Contact class model. Contains constructors to create Contact objects and methods to
 *  set and get their attributes. */
public class Contact
{
    int contactId;
    String contactName;

    /** A constructor for contact objects. It is used to create contact objects that are used contact combo boxes
     *  @param contactName Contact name as a String.
     *  */
    public Contact(String contactName)
    {
        this.contactName = contactName;
    }

    /** The method to return the int of an contact object's contactId.
     *  @return Returns int of a contactId */
    public int getContactId() {
        return contactId;
    }

    /** The method used to set the contact object's contactId.
     *  @param contactId The contactId to set the contact object's contactId. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** The method to return the String of a contact object's contactName.
     *  @return Returns String of a contactName */
    public String getContactName() {
        return contactName;
    }

    /** The method used to get the contact object's contactName.
     *  @param contactName The contactName to set the contact object's contactName. */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** String method that has an Override to return contactName instead of the object model data.
     *  @return Returns contactName. */
    @Override
    public String toString(){
        return contactName;
    }
}
