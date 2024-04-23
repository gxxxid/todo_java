/**
 * Class EventDAO
 * This is the class for all operation on EventDAO
 */
package ToDo.src;
import java.sql.*;
import ToDo.src.Event;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    //sql queries
    private static final String GET_EVENT_BY_ID = "SELECT * FROM Event WHERE eventIndex = ?;";
    private static final String GET_EVENT_BY_TITLE = "SELECT * FROM Event WHERE eventTitle = ?;";
    private static final String GET_EVENT_BY_DETAIL = "SELECT * FROM Event WHERE eventDetail = ?;";
    private static final String GET_CURRENT_EVENTS =
            "SELECT * FROM Event WHERE nextOccurrence >= ? ORDER BY nextOccurrence ASC;";
    private static final String GET_HISTORY_EVENTS =
            "SELECT * FROM Event WHERE nextOccurrence < ? ORDER BY nextOccurrence ASC;";
    private static final String ADD_EVENT = "INSERT INTO Event(eventTitle, eventDetail, startDate, " +
            "`repeat`, repeatPeriod, repeatIndex, nextOccurrence) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String DELETE_EVENT_BY_ID = "DELETE FROM Event WHERE eventIndex = ?;";


    public Event getEventById(int eventId) {
        Event event = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_EVENT_BY_ID)) {
            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    event = new Event();
                    event.setEventIndex(eventId);
                    event.setEventTitle(rs.getString("eventTitle"));
                    event.setEventDetail(rs.getString("eventDetail"));
                    event.setStartDate(rs.getTimestamp("startDate"));
                    event.setNextOccurrence(rs.getTimestamp("nextOccurrence"));
                    event.setRepeat(rs.getBoolean("repeat"));
                    event.setRepeatPeriod(rs.getString("repeatPeriod"));
                    event.setRepeatIndex(rs.getInt("repeatIndex"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }//end getEventById
    public List<Event> getEvents(Timestamp currentDate){
        List<Event> events = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_CURRENT_EVENTS)) {

            pstmt.setTimestamp(1, currentDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEventIndex(rs.getInt("eventIndex"));
                    event.setEventTitle(rs.getString("eventTitle"));
                    event.setEventDetail(rs.getString("eventDetail"));
                    event.setStartDate(rs.getTimestamp("startDate"));
                    event.setNextOccurrence(rs.getTimestamp("nextOccurrence"));
                    event.setRepeat(rs.getBoolean("`repeat`"));
                    event.setRepeatPeriod(rs.getString("repeatPeriod"));
                    event.setRepeatIndex(rs.getInt("repeatIndex"));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }//end getEvents
    public List<Event> getHistoryEvents(Timestamp currentDate){
        List<Event> events = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_HISTORY_EVENTS)) {

            pstmt.setTimestamp(1, currentDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEventIndex(rs.getInt("eventIndex"));
                    event.setEventTitle(rs.getString("eventTitle"));
                    event.setEventDetail(rs.getString("eventDetail"));
                    event.setStartDate(rs.getTimestamp("startDate"));
                    event.setRepeat(rs.getBoolean("`repeat`"));
                    event.setRepeatPeriod(rs.getString("repeatPeriod"));
                    event.setRepeatIndex(rs.getInt("repeatIndex"));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }//get history event
    public void addEvent(Event event) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(ADD_EVENT)) {
            pstmt.setString(1, event.getEventTitle());
            pstmt.setString(2, event.getEventDetail());
            pstmt.setTimestamp(3, event.getStartDate());
            pstmt.setBoolean(4, event.getRepeat());
            if (event.getRepeat()) {
                pstmt.setString(5, event.getRepeatPeriod());
                pstmt.setInt(6, event.getRepeatIndex());
            } else {
                pstmt.setNull(5, Types.VARCHAR);  // Set repeatPeriod to NULL if not repeating
                pstmt.setNull(6, Types.INTEGER);  // Set repeatIndex to NULL if not repeating
            }

            // Calculate nextOccurrence
            Timestamp nextOccurrence = calculateNextOccurrence(event);
            pstmt.setTimestamp(7, nextOccurrence);

            pstmt.executeUpdate();  // Use executeUpdate() for insert, update, delete operations
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//end addEvent

    private Timestamp calculateNextOccurrence(Event event) {
        if (!event.getRepeat()) {
            return event.getStartDate(); // If not repeating, nextOccurrence is the startDate
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(event.getStartDate().getTime());  // Start from the startDate

        // Adjust calendar based on repeatPeriod and repeatIndex
        switch (event.getRepeatPeriod().toLowerCase()) {
            case "day":
                cal.add(Calendar.DATE, event.getRepeatIndex());
                break;
            case "week":
                cal.add(Calendar.DATE, 7 * event.getRepeatIndex());
                break;
            case "month":
                cal.add(Calendar.MONTH, event.getRepeatIndex());
                break;
            case "year":
                cal.add(Calendar.YEAR, event.getRepeatIndex());
                break;
            default:
                return event.getStartDate(); // Fallback to startDate if period is unrecognized
        }
        return new Timestamp(cal.getTimeInMillis());
    }//end calculateNextOccurrence


    public void deleteEventById(int eventId){
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_EVENT_BY_ID)) {
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//End deleteEventById

    public void updateEventNextOccurrence(int eventId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(
                     "SELECT startDate, repeatPeriod, repeatIndex FROM Event WHERE eventIndex = ?")) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Timestamp startDate = rs.getTimestamp("startDate");
                String repeatPeriod = rs.getString("repeatPeriod");
                int repeatIndex = rs.getInt("repeatIndex");

                // Calculate the next occurrence
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(startDate.getTime());
                switch (repeatPeriod) {
                    case "Day":
                        cal.add(Calendar.DATE, repeatIndex);
                        break;
                    case "Week":
                        cal.add(Calendar.DATE, 7 * repeatIndex);
                        break;
                    case "Month":
                        cal.add(Calendar.MONTH, repeatIndex);
                        break;
                    case "Year":
                        cal.add(Calendar.YEAR, repeatIndex);
                        break;
                }

                // Update the next occurrence in the database
                try (PreparedStatement updateStmt = connection.prepareStatement(
                        "UPDATE Event SET nextOccurrence = ? WHERE eventIndex = ?")) {
                    updateStmt.setTimestamp(1, new Timestamp(cal.getTimeInMillis()));
                    updateStmt.setInt(2, eventId);
                    updateStmt.executeUpdate();
                }
            }
        }
    }


}//end EventDAO


