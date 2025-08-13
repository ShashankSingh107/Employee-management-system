import java.sql.*;
public class DBConnection {

    private static final String URL="jdbc:mysql://localhost:3306/employee_managment";
    private static final String USER="root";
    private static final String PASSWORD="123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

}
