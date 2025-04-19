package baiToanHRM;

import java.util.Scanner;

public class Subject {
	private String subjectCode;
	private String subjectName;
	private int credit;
	private float attendanceMark;
	private float midExamMark;
	private float finalExamMark;
	
	//Ham khong tham so
	public Subject() {}
	
	//ham 3 tham so
	public Subject(String subjectCode, String subjectName, int credit) {
		this.subjectCode=subjectCode;
		this.subjectName=subjectName;
		this.credit=credit;
	}
	
	//Ham tinh diem cua mon chuyen can 0,1 giua ki 0,3 cuoi ki 0,6
		public float calSubjectMark() {
			float diem=(float)(attendanceMark * 0.1 + midExamMark * 0.3 + finalExamMark * 0.6); 
			return diem;
		}
	
	//Tinh he chuyen doi tu 10 sang 4
	public float calConversionMark() {
		float diemhe10 = calSubjectMark();
		float diemhe4 =-1;
		
		if(diemhe10<4) {
			diemhe4=0;
		}
		else if(diemhe10<5) {
			diemhe4=1;
		}
		else if(diemhe10<5.5) {
			diemhe4=1.5f;
		}
		else if(diemhe10<6.5) {
			diemhe4=2;
		}
		else if(diemhe10<7) {
			diemhe4=2.5f;
		}
		else if(diemhe10<7.5) {
			diemhe4=3;
		}
		else if(diemhe10<8) {
			diemhe4=3.5f;
		}
		else if(diemhe10<8.5) {
			diemhe4=4;
		}
		return diemhe4;
	}
	
	//Ham tinh diem he chu tu diem mon
	public String calGrade() {
		float diemhe10 = calSubjectMark();
		String diemchu = null;
		if(diemhe10<4) {
			diemchu="F";
		}
		else if(diemhe10<5) {
			diemchu="D";
		}
		else if(diemhe10<5.5) {
			diemchu="D+";
		}
		else if(diemhe10<6.5) {
			diemchu="C";
		}
		else if(diemhe10<7) {
			diemchu="C+";
		}
		else if(diemhe10<7.5) {
			diemchu="B";
		}
		else if(diemhe10<8) {
			diemchu="B+";;
		}
		else if(diemhe10<8.5) {
			diemchu="A";
		}
		return diemchu;
	}
	
	//Ham chuyen diem tu he chu sang he 4
	public float calConversionMark(String grade) {
		float diem =-1;
		switch(grade)
		{
			case "F":
				diem=0;
				break;
			case "D":
				diem=1;
				break;
			case "D+":
				diem=1.5f;
				break;
			case "C":
				diem=2;
				break;
			case "C+":
				diem=2.5f;
				break;
			case "B":
				diem=3;
				break;
			case "B+":
				diem=3.5f;
				break;
			case "A":
				diem=4;
				break;
		}
		return diem;
	}
	
	//Lay ve so tin chi
	public int getCredit() {
		return credit;
	}
	
	//Set
	public void setAttendanceMark(float attendanceMark) {
		this.attendanceMark=attendanceMark;
	}
	public void setMidExamMark(float midExamMark) {
		this.midExamMark=midExamMark;
	}
	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark=finalExamMark;
	}
	
	
	//Nhap vao Subject
	public void enterInfo(Scanner sc)
	{
		System.out.print("\nNhap vao ma mon hoc: ");
		subjectCode =sc.nextLine();
		sc.nextLine();
		System.out.print("\nNhap vao ten mon hoc la: ");
		sc.nextLine();
		subjectName=sc.nextLine();
		System.out.print("Nhap vao so tin chi: ");
		credit=sc.nextInt();
		System.out.print("Nhap vao diem chuyen can: ");
		attendanceMark =sc.nextFloat();
		System.out.print("Nhap vao diem giua ky: ");
		midExamMark=sc.nextFloat();
		System.out.print("Nhap vao diem cuoi ki: ");
		finalExamMark=sc.nextFloat();
	}
	@Override
	public String toString() {
		return subjectCode + " - " + subjectName + " - " + credit + " - " + calGrade(); 
	}
	
	
}
