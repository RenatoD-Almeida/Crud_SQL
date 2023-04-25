package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    public static final String URL = "jdbc:mysql://localhost:3306/COMERCIO?allowPublicKeyRetrieval=true&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
