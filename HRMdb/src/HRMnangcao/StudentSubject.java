package HRMnangcao;

import java.util.Scanner;

import model.SubjectDAO;

public class StudentSubject{
	private String usercode;
	private String subjectcode;
	private float attendancemark;
	private float midexammark1;
	private float midexammark2;
	private float midexammark3;
	private float finalexammark;
	private SubjectDAO subjectdao;
	private Subject subject;
	
	public StudentSubject() {
	    subjectdao = new SubjectDAO();
	}
	public StudentSubject(String usercode, String subjectcode) {
		this.usercode= usercode;
		this.subjectcode=subjectcode;
		
	}
	public StudentSubject(float attendancemark, float midexammark1,float midexammark2,float midexammark3,float finalexammark) {
		this.attendancemark=attendancemark;
		this.midexammark1=midexammark1;
		this.midexammark2=midexammark2;
		this.midexammark3=midexammark3;
		this.finalexammark=finalexammark;
	}
	public StudentSubject(String usercode, String subjectcode,float attendancemark, float midexammark1,float midexammark2,float midexammark3,float finalexammark) {
		this(usercode,subjectcode);
		this.attendancemark=attendancemark;
		this.midexammark1=midexammark1;
		this.midexammark2=midexammark2;
		this.midexammark3=midexammark3;
		this.finalexammark=finalexammark;
	}
	
	public float getAttendancemark() {
		return attendancemark;
	}
	public void setAttendancemark(float attendancemark) {
		this.attendancemark = attendancemark;
	}
	public float getMidexammark1() {
		return midexammark1;
	}
	public void setMidexammark1(float midexammark1) {
		this.midexammark1 = midexammark1;
	}
	public float getMidexammark2() {
		return midexammark2;
	}
	public void setMidexammark2(float midexammark2) {
		this.midexammark2 = midexammark2;
	}
	public float getMidexammark3() {
		return midexammark3;
	}
	public void setMidexammark3(float midexammark3) {
		this.midexammark3 = midexammark3;
	}

	public float getFinalexammark() {
		return finalexammark;
	}

	public void setFinalexammark(float finalexammark) {
		this.finalexammark = finalexammark;
	}
	public float calConversionMark(String grade) {
        switch (grade) {
        case "A": return 4.0f;
        case "B+": return 3.5f;
        case "B": return 3.0f;
        case "C+": return 2.5f;
        case "C": return 2.0f;
        case "D+": return 1.5f;
        case "D": return 1.0f;
        default: return 0.0f;
        }
	}
	
	public String calGrade() {
        float subjectMark = calSubjectMark();
        
        if (subjectMark >= 8.5) return "A";
        if (subjectMark >= 7.5) return "B+";
        if (subjectMark >= 7.0) return "B";
        if (subjectMark >= 6.5) return "C+";
        if (subjectMark >= 6.0) return "C";
        if (subjectMark >= 5.5) return "D+";
        if (subjectMark >= 5.0) return "D";
        return "F";
	}
	
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getSubjectcode() {
		return subjectcode;
	}
	public void setSubjectcode(String subjectcode) {
		this.subjectcode = subjectcode;
	}
	
	public void enterStudentSub (Scanner sc) {
		System.out.println("Nhap vao ma sinh vien: ");
		this.usercode = sc.nextLine();
		System.out.println("Nhap vao ma mon hoc: ");
		this.subjectcode = sc.nextLine();
	}
	
	public void updateStudentSub (Scanner sc) {
		System.out.println("Nhap vao ma sinh vien: ");
		this.usercode = sc.nextLine();
		System.out.println("Nhap vao ma mon hoc");
		this.subjectcode = sc.nextLine();
		System.out.println("Nhap vao diem chuyen can: ");
		this.attendancemark = sc.nextFloat();
		System.out.println("Nhap vao diem giua ky 1: ");
		this.midexammark1 = sc.nextFloat();
		System.out.println("Nhap vao diem giua ky 2: ");
		this.midexammark2 = sc.nextFloat();
		System.out.println("Nhap vao diem giua ky 3: ");
		this.midexammark3 = sc.nextFloat();
		System.out.println("Nhap vao diem cuoi ky: ");
		this.finalexammark = sc.nextFloat();
	}

	public float calSubjectMark() {
	    return subject.getHesodiem1() * attendancemark
	         + subject.getHesodiem2() * midexammark1
	         + subject.getHesodiem3() * midexammark2
	         + subject.getHesodiem4() * midexammark3
	         + subject.getHesodiem5() * finalexammark;
	}
}
