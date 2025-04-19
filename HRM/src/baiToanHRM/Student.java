package baiToanHRM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends Human {
	private String class_;
	private List<Subject> subjectList = new ArrayList<Subject>();
	
	
	//ham khong tham so
	public Student() {}
	
	//ham 1 tham so
	public Student(String code) {
		super(code);
	}
	
	//ham 2 tham so
	public Student(String code, String fullname) {
		super(code,fullname);
	}
	
	//Ham 3 tham so
	public Student(String code, String fullname, String class_) {
		super(code,fullname);
		this.class_=class_;
	}
	
	//Ham 4 tham so
	public Student(String code, String fullname, String class_, String address) {
		this(code,fullname,class_);
		this.address=address;
	}
	
	//Ham add mon hoc vao list
	public void addSubject(Subject sub) {
		subjectList.add(sub);
	}
	
	public void enterInfo(Scanner sc)
	{
		super.enterInfo(sc);
		System.out.print("Nhap vao lop: ");
		class_=sc.nextLine();
	}
	
	//Ham tinh diem so trung binh
	public float calTermAverageMark()
	{
		float ts=0;
		int ms=0;
		
		for(Subject sub: subjectList)
		{
			ts+= sub.getCredit() * sub.calConversionMark();
			ms+= sub.getCredit();			
		}
		
		return ts/ms;
	}
	
	//GET
	public String getClass_()
	{
		return class_;
	}
	
	//SET
	public void setClass_(String class_)
	{
		this.class_=class_;
	}
	
	@Override
	public String toString() 
	{
		return code + " - "+ fullname + " - " + class_;
	}
	
	
}
