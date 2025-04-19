package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import HRMnangcao.Human;
import HRMnangcao.Lecturer;
import HRMnangcao.Student;

public class UserDAO  implements IUserDAO{
	
	private Connection connection;
	
	public UserDAO(){
		try {
			String dbURL ="jdbc:ucanaccess://lib/baitoantinchi.accdb";
			connection =DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<Human> getAllUsers(){
		List<Human> userList = new ArrayList<Human>();
		String query ="SELECT * FROM tbl_users";
		
		try(Statement state =connection.createStatement();
			ResultSet rs = state.executeQuery(query)){
			
			while(rs.next()) {
				String userCode = rs.getString("user_code");
				String userName = rs.getString("fullname");
				String userAddress = rs.getString("address");
				String class_ = rs.getString("class");
				String password = rs.getString("password");
				int userType = rs.getInt("user_type");
				Human user;
				
				if(userType == 0) {
					user = new Student(userCode,userName,userAddress,class_);
					userList.add(user);
				}
				
				else if (userType == 1) 
				{
					user = new Lecturer(userCode,userName,userAddress,password);
					userList.add(user);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	public Human searchUserId(String code) {
		Human user =null;
		
		String query ="SELECT * FROM tbl_users WHERE user_code=?";
		
		
		try(PreparedStatement stms = connection.prepareStatement(query)){
			stms.setString(1,code);
			ResultSet rs = stms.executeQuery();
			if(rs.next()) {
				String userCode = rs.getString("user_code");
				String userName = rs.getString("fullname");
				String userAddress = rs.getString("address");
				String class_ = rs.getString("class");
				String password = rs.getString("password");
				int userType = rs.getInt("user_type");
	
				if(userType == 0) {
					user = new Student(userCode,userName,userAddress,class_);
				}
				
				else if (userType == 1) 
				{
					user = new Lecturer(userCode,userName,userAddress,password);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}
	
	public boolean addUser(Human user) {
		String query="INSERT INTO tbl_users(user_code,fullname,address,class,password,user_type) VALUES(?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, user.getCode());
			stmt.setString(2, user.getFullname());
			stmt.setString(3, user.getAddress());
			if(user instanceof Student) {
				stmt.setString(4,((Student)user).getClass_());
				stmt.setString(5, null);
			}
			if(user instanceof Lecturer) {
				stmt.setString(4, null);
				stmt.setString(5,((Lecturer)user).getPassword());
			}
			stmt.setInt(6, user.getType());
			
			
			int row = stmt.executeUpdate();
			
			return row>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
