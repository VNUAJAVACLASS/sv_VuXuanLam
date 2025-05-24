package HRMnangcao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.SubjectDAO;


public class SubjectController {
	
	private SubjectDAO subjectdao;
	
	public SubjectController(){
		subjectdao = new SubjectDAO();
	}
	public void printSubjectList() {
		List<Subject> subjectList = new ArrayList<Subject>();
		subjectList = subjectdao.getAllSubjects();
		System.out.println("Danh sach mon hoc la: ");
		for(Subject sub :subjectList) {
			System.out.println(sub);
		}
	}
	public void addSubject(Scanner sc) {
		Subject sub = new Subject();
		sub.enterSubject(sc);
		subjectdao.addSubject(sub);
	}
	public void dangKyMonHoc(Scanner sc) {
		StudentSubject sub = new StudentSubject();
		sub.enterStudentSub(sc);
		subjectdao.addSutdenSub(sub);
	}
	
	public float tinhDiem () {
		StudentSubject stusub = new StudentSubject();
		float abc = stusub.calSubjectMark("671605", "TH03");
		return abc;
	}
	
	public void suaDiem(Scanner sc) {
	    // Nhập mã sinh viên và mã môn học cần sửa điểm
	    System.out.print("Nhập mã sinh viên: ");
	    String userCode = sc.nextLine();

	    System.out.print("Nhập mã môn học: ");
	    String subjectCode = sc.nextLine();

	    // Tìm kiếm điểm của sinh viên
	    StudentSubject stusub = subjectdao.getStudentSubject(userCode, subjectCode);
	    
	    // Kiểm tra nếu tìm thấy sinh viên và môn học
	    if (stusub != null) {
	        System.out.println("Thông tin hiện tại của môn học: ");
	        System.out.println(stusub);  // Hiển thị điểm cũ của sinh viên

	        // Nhập điểm mới từ người dùng
	        System.out.print("Nhập điểm thi học phần: ");
	        float attendanceexammark = sc.nextFloat();
	        
	        System.out.print("Nhập điểm thi giữa kỳ 1: ");
	        float midexammark1 = sc.nextFloat();
	        
	        System.out.print("Nhập điểm thi giữa kỳ 2: ");
	        float midexammark2 = sc.nextFloat();
	        
	        System.out.print("Nhập điểm thi giữa kỳ 3: ");
	        float midexammark3 = sc.nextFloat();
	        
	        System.out.print("Nhập điểm thi cuối kỳ: ");
	        float finalexammark = sc.nextFloat();
	        
	        // Cập nhật điểm mới vào đối tượng StudentSubject
	        stusub.setAttendancemark(attendanceexammark);
	        stusub.setMidexammark1(midexammark1);
	        stusub.setMidexammark2(midexammark2);
	        stusub.setMidexammark3(midexammark3);
	        stusub.setFinalexammark(finalexammark);
	        
	        // Gọi DAO để cập nhật điểm vào database
	        boolean success = subjectdao.updateStudentSubjectMarks(stusub);

	        if (success) {
	            System.out.println("Cập nhật điểm thành công!");
	        } else {
	            System.out.println("Cập nhật điểm thất bại.");
	        }
	    } else {
	        System.out.println("Không tìm thấy sinh viên hoặc môn học.");
	    }
	}

}
