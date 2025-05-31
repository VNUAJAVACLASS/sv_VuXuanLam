package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import HRMnangcao.StudentSubject;
import HRMnangcao.Subject;

public class SubjectDAO {
	private Connection connection;
	public SubjectDAO() {
		try {
			String dbURL ="jdbc:ucanaccess://lib/baitoantinchi.accdb";
			connection =DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Subject> getAllSubjects(){
		List<Subject> subjectList = new ArrayList<Subject>();
		String query ="SELECT * FROM tbl_subject";
		
		try(Statement state =connection.createStatement();
			ResultSet rs = state.executeQuery(query)){
			
			while(rs.next()) {
				String subjectCode = rs.getString("subject_code");
				String subjectName = rs.getString("subject_name");
				int credit = rs.getInt("credit");
				float hesodiem1 = rs.getFloat("hesodiem1");
				float hesodiem2 = rs.getFloat("hesodiem2");
				float hesodiem3 = rs.getFloat("hesodiem3");
				float hesodiem4 = rs.getFloat("hesodiem4");
				float hesodiem5 = rs.getFloat("hesodiem5");
				
				Subject subject = new Subject(subjectCode, subjectName,credit ,hesodiem1, hesodiem2, hesodiem3, hesodiem4, hesodiem5);
				subjectList.add(subject);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subjectList;
	}
	public boolean addSubject(Subject sub) {
		String query="INSERT INTO tbl_subject(subject_code,subject_name,credit,hesodiem1,hesodiem2,hesodiem3,hesodiem4,hesodiem5) VALUES(?,?,?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, sub.getSubjectCode());
			stmt.setString(2, sub.getSubjectName());
			stmt.setInt(3, sub.getCredit());
			stmt.setFloat(4,sub.getHesodiem1());
			stmt.setFloat(5,sub.getHesodiem2());
			stmt.setFloat(6,sub.getHesodiem3());
			stmt.setFloat(7,sub.getHesodiem4());
			stmt.setFloat(8,sub.getHesodiem5());
			int row = stmt.executeUpdate();
			
			return row>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public Subject searchSubject(String code) {
		Subject subject =null;
		
		String query ="SELECT * FROM tbl_subject WHERE subject_code=?";
		
		
		try(PreparedStatement stms = connection.prepareStatement(query)){
			stms.setString(1,code);
			ResultSet rs = stms.executeQuery();
			if(rs.next()) {
				String subjectCode = rs.getString("subject_code");
				String subjectName = rs.getString("subject_name");
				int credit = rs.getInt("credit");
				float hesodiem1 = rs.getFloat("hesodiem1");
				float hesodiem2 = rs.getFloat("hesodiem2");
				float hesodiem3 = rs.getFloat("hesodiem3");
				float hesodiem4 = rs.getFloat("hesodiem4");
				float hesodiem5 = rs.getFloat("hesodiem5");
				
				subject = new Subject(subjectCode, subjectName,credit ,hesodiem1, hesodiem2, hesodiem3, hesodiem4, hesodiem5);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subject;
		
	}
	public boolean addSutdenSub(StudentSubject sub) {
		String query="INSERT INTO tbl_usersubject(user_code,subject_code) VALUES(?,?)";
		
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, sub.getUsercode());
			stmt.setString(2, sub.getSubjectcode());

			int row = stmt.executeUpdate();
			
			return row>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<StudentSubject> getAllStudentSubjects(String usercode){
		List<StudentSubject> studentSubjectList = new ArrayList<StudentSubject>();
		String query = "SELECT * FROM tbl_usersubject where user_code = ?";
		try(PreparedStatement stms = connection.prepareStatement(query)){
			stms.setString(1,usercode);
			ResultSet rs = stms.executeQuery();
			while(rs.next()) {
				String user_code = rs.getString("user_code");
				String subject_code = rs.getString("subject_code");
				float attendanceexammark = rs.getFloat("attendanceexammark");
				float midexammark1 = rs.getFloat("midexammark1");
				float midexammark2 = rs.getFloat("midexammark2");
				float midexammark3 = rs.getFloat("midexammark3");
				float finalexammark = rs.getFloat("finalexammark");
				
				StudentSubject stusubject = new StudentSubject(user_code, subject_code,attendanceexammark ,midexammark1, midexammark2, midexammark3, finalexammark);
				studentSubjectList.add(stusubject);
			}
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return studentSubjectList;
		
		
	} public Subject getSubjectByCode(String subjectCode) {
        String query = "SELECT * FROM tbl_subject WHERE subject_code=?";
        Subject subject = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String code = rs.getString("subject_code");
                String name = rs.getString("subject_name");
                int credit = rs.getInt("credit");
                float hesodiem1 = rs.getFloat("hesodiem1");
                float hesodiem2 = rs.getFloat("hesodiem2");
                float hesodiem3 = rs.getFloat("hesodiem3");
                float hesodiem4 = rs.getFloat("hesodiem4");
                float hesodiem5 = rs.getFloat("hesodiem5");

                subject = new Subject(code, name, credit, hesodiem1, hesodiem2, hesodiem3, hesodiem4, hesodiem5);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subject;
    }

    // Truy vấn điểm của sinh viên từ tbl_usersubject
    public StudentSubject getStudentSubject(String userCode, String subjectCode) {
        String query = "SELECT * FROM tbl_usersubject WHERE user_code=? AND subject_code=?";
        StudentSubject studentSubject = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            stmt.setString(2, subjectCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userCodeDB = rs.getString("user_code");
                String subjectCodeDB = rs.getString("subject_code");
                float attendanceexammark = rs.getFloat("attendanceexammark");
                float midexammark1 = rs.getFloat("midexammark1");
                float midexammark2 = rs.getFloat("midexammark2");
                float midexammark3 = rs.getFloat("midexammark3");
                float finalexammark = rs.getFloat("finalexammark");

                studentSubject = new StudentSubject(userCodeDB, subjectCodeDB, attendanceexammark,
                        midexammark1, midexammark2, midexammark3, finalexammark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentSubject;
    }
    
    public boolean updateStudentSubjectMarks(StudentSubject sub) {
        String query = "UPDATE tbl_usersubject SET attendanceexammark=?, midexammark1=?, midexammark2=?, midexammark3=?, finalexammark=? WHERE user_code=? AND subject_code=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, sub.getAttendancemark());
            stmt.setFloat(2, sub.getMidexammark1());
            stmt.setFloat(3, sub.getMidexammark2());
            stmt.setFloat(4, sub.getMidexammark3());
            stmt.setFloat(5, sub.getFinalexammark());
            stmt.setString(6, sub.getUsercode());
            stmt.setString(7, sub.getSubjectcode());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
