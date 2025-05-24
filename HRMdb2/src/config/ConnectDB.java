package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private Connection connection;
	public void connectMySQL() {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver"); // Nạp driver MySQL
	        String dbURL = "jdbc:mysql://localhost:3306/baitoantinchi";
	        
	        //username mac dinh la root
	        String username = "root"; 
	        
	        //password khi cai db thi dat
	        String password = "123456";
	        connection = DriverManager.getConnection(dbURL, username, password);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        System.out.println("Kết nối thất bại!");
	    }
	}
	public Connection getConnection() {
        return connection;
    }
}
