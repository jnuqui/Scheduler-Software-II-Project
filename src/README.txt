Title: "Scheduler" - Desktop appointment scheduling application.
Purpose: A desktop application that handles the scheduling of appointments for a global consulting
        organization. The application handles input logic/data flow for customers, appointments and more to
        interact with a MySQL database to facilitate CRUD operations.
        Also I would like to earn credit for QAM2 TASK 1.
Author: Jonathan Nuqui
Contact information: jnuqui@wgu.edu
Student application version: 1.0.17
Date: 7/25/23

IDE: IntelliJ IDEA 2021.1.3 (Community Edition)
Java SDK: 17.0.1
JavaFX: 17.0.1

Directions:

Login: Use the username: "test" password: "test" to login to the application.

Customer: Use the buttons to interact with customer data.
- Add:    Type entries into the textfields and select combo boxes. Note: Country must be selected before a first level
          division can be selected. Click "Add" when all fields are filled to add customer. Use the "Reset" button to
          quickly clear the form to start over. Click "Close" to return to the customer view.
- Update: Choose a customer from the TableView to update its information. CustomerId will be disabled, but the other
          fields can be edited. Click "Update" to update the customer when all fields are filled.
- Delete: Choose a customer from the TableView to delete a customer. If the customer has appointments, he or she may
          may not be deleted. Click "OK" to confirm to delete.

Appointments: Use the buttons to interact with appointment data.
- Add:    Type entries into the textfields, select Date and use combo boxes. Times must be within 8:00AM - 10:00PM (ET).
          AppointmentId will be disabled. Alerts will show if the time is invalid when attempting to add.
          Click "Add" when all fields are filled to add appointment. Use the "Reset" button to
          quickly clear the form to start over. Click "Close" to return to the appointment view.
- Update: Choose an appointment from the TableView to update its information. AppointmentId will be disabled, but
          the other fields can be edited. Click "Update" to update the appointment when all fields are filled.
          Use the "Reset" button to quickly clear the form to start over. Click "Close" to return to the
          appointment view.
- Delete: Choose a customer from the TableView to delete an appointment. Click "OK" to confirm the delete.

Views: Use the Radio buttons to control the appointment views
- All:    Click to display the default view to show all appointments.
- Month:  Click to filter appointments that happen in the current month.
- Week:   Click to filter appointments that happen within the current time through seven days ahead.

Reports: Use the combo boxes and buttons to generate unique reports:
- 1:      Select both a type and month from the combo boxes and click "Report".
- 2:      Select a contact from the combo box and click "Report".
- 3:      Select a location from the combo box and click "Report".

A3f report: User generates a report of the appointments based on location selection. The report displays
the appointments that exist at the location.

MySQL Database Driver: mysql-connector-java-8.0.25