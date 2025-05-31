package HRMnangcao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import model.SubjectDAO;


@Entity
@Table(name="Student")
public class Student extends Human {
	@Column(name ="class")
	private String class_;
	@Transient
	private HashMap<String,Subject> subjectList = new HashMap<String,Subject>();
	@Transient
	private List<StudentSubject> studentsub = new ArrayList<StudentSubject>();
	private SubjectDAO subjectdao = new SubjectDAO();
	private Subject subject;
	public Student(){
		 
	}
	
	public Student(String code){
		super(code);
		
	}
	
	public Student(String code, String fullname){
		super(code, fullname);
	}
	
	public Student(String code, String fullname, String class_){
		super(code, fullname);
		this.class_ = class_;
	}

	public Student(String code, String fullname, String address,String class_){
		super(code, fullname, address);
		this.class_ = class_;
	}

	public void addSubject(Subject sub) {
		subjectList.put(sub.getSubjectCode(), sub);
	}
	public void removeSubject(String key) {
		subjectList.remove(key);
	}
	
	public float calTermAverageMark() {
	    float sumGrade = 0;
	    int sumCredit = 0;

	    for (StudentSubject stdsub : studentsub) {
	    	subject = subjectdao.getSubjectByCode(stdsub.getSubjectcode());
	        if (subject != null) {
	            int credit = subject.getCredit();
	            sumCredit += credit;
	            sumGrade += stdsub.calSubjectMark(code, subject.getSubjectCode()) * credit;
	        }
	    }

	    if (sumCredit == 0) {
	        return 0;
	    }
	    return sumGrade / sumCredit;
	}
	
	
	@Override
	public void enterInfo(Scanner sc) {
		System.out.println("Nhap vao ten: ");
		this.fullname = sc.nextLine();
		System.out.println("Nhap vao ma code: ");
		this.code = sc.nextLine();
		System.out.println("Nhap vao dia chi: ");
		this.address = sc.nextLine();
		System.out.println("Nhap vao lop: ");
		this.class_ = sc.nextLine();
	}

	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Override
	public String toString() {
		StringBuilder stringb = new StringBuilder();
		
		 stringb.append("====================////==================\n");
		 stringb.append("Ho va ten sinh vien: ").append(fullname).append("\n");
		 stringb.append("Ma sinh vien: ").append(code).append("\n");
		 stringb.append("Đia chi: ").append(address).append("\n");
		 stringb.append("Lop: ").append(class_).append("\n");
		 studentsub=subjectdao.getAllStudentSubjects(code);
		 
		 if (studentsub.isEmpty()) {
			 stringb.append("Sinh vien khong co mon hoc nao trong ky");
			 return stringb.toString();
		 }
		
		stringb.append("Danh sach mon hoc:\n");
		for (StudentSubject s : studentsub) {
		    stringb.append(s).append("\n"); 
		    stringb.append("Diem trung binh mon ").append(s.calSubjectMark(code,s.getSubjectcode())).append("\n");
		}
		stringb.append("Diem trung binh hoc ky: ").append(calTermAverageMark());
		return stringb.toString();
	}
}
