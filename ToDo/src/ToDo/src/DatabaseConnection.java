package ToDo.src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cs4080Todo";
    private static final String USER = "root";
    private static final String PASSWORD = "3560";

    static {
        try {
            // This explicitly loads the MySQL driver class; required for older Java versions
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }//end geConnection
}//End DatabaseConnection

