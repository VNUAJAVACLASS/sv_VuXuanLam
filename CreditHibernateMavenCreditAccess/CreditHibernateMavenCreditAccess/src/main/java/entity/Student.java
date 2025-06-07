package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tbl_users")
public class Student extends Human {
	@Column(name = "class")
	private String class_;
	
	@Transient
	private List<Subject> subjectList = new LinkedList<Subject>();
	
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

	public Student(String code, String fullname, String class_, String address){
		super(code, fullname, address);
		this.class_ = class_;
	}

	public void AddSubject(Subject sub) {
		subjectList.add(sub);
	}
	
	public float calTermAverageMark() {
	    int sumCredit = 0;
	    int sumGrade = 0;
	    
	    for (Subject sub : subjectList) {
	        sumCredit += sub.getCredit();
	        sumGrade += sub.calConversionMark(sub.calGrade()) * sub.getCredit();
	    }
	    return (float) sumGrade / sumCredit;
	}
	
	public void enterInfo(Scanner sc) {
		super.enterInfo(sc);
	}

	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Override
	public String toString() {
		return "Student [class_=" + class_ + ", subjectList=" + subjectList + ", address=" + address + ", code=" + code
				+ ", fullname=" + fullname + "]" + "Term Average Mark" + calTermAverageMark();
	}
	
	
}
