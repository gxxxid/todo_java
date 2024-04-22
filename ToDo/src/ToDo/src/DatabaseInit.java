package ToDo.src;
import java.sql.*;

public class DatabaseInit {
    //Initialize database and create table
    public static void initializeDatabase() throws SQLException {
        String createDB = "create DATABASE IF NOT EXISTS cs4080Todo;";
        String createUser = "CREATE USER IF NOT EXISTS 'rt'@'host' IDENTIFIED  BY '4080';";
        //create Event table
        String createEventTable = "CREATE TABLE IF NOT EXISTS Event (" +
                "eventIndex INT AUTO_INCREMENT PRIMARY KEY," +
                "eventTitle VARCHAR(50)," +
                "eventDetail VARCHAR(255)," +
                "startDate DATETIME," +
                "nextOccurrence DATETIME," +
                "repeat BOOLEAN," +
                "repeatPeriod ENUM('Day', 'Week', 'Month', 'Year')," +
                "repeatIndex INT" +
                ");";

        try(Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement()){
            statement.executeUpdate(createDB);
            statement.executeUpdate(createUser);
            statement.executeUpdate(createEventTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}