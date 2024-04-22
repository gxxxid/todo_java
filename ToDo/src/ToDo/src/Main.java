package ToDo.src;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        EventDAO eventDAO = new EventDAO();

        // Create a new Event object
        Event newEvent = new Event();
        newEvent.setEventTitle("Team Meeting");
        newEvent.setEventDetail("Discuss project updates");
        newEvent.setStartDate(new Timestamp(System.currentTimeMillis()));
        newEvent.setRepeat(true);
        newEvent.setRepeatPeriod("Week");
        newEvent.setRepeatIndex(1);

        // Add the event to the database
        eventDAO.addEvent(newEvent);
        System.out.println("Event added successfully.");

        // Assuming the eventIndex is returned or known (pseudo code here, you need to adjust based on actual logic)
        int eventId = 1; // This should be dynamically retrieved after adding the event

        // Get and print the event by ID
        Event retrievedEvent = eventDAO.getEventById(eventId);
        if (retrievedEvent != null) {
            System.out.println("Retrieved Event: " + retrievedEvent.getEventTitle() + ", Details: " + retrievedEvent.getEventDetail());
        }

        // Update the next occurrence of the event
        try {
            eventDAO.updateEventNextOccurrence(eventId);
            System.out.println("Next occurrence updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating next occurrence: " + e.getMessage());
            e.printStackTrace();
        }

        // Delete the event
        eventDAO.deleteEventById(eventId);
        System.out.println("Event deleted successfully.");
    }
}

