package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private static final String URL =
            "jdbc:mysql://localhost:3306/mybook?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";

    private static final String USER = "lamdeptrai24";
    private static final String PASSWORD = "123456!";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
