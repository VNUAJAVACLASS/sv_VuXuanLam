package HRMnangcao;

import java.util.Scanner;

public class Subject {
	private String subjectCode;
	private String subjectName;
	private int credit;
	private float hesodiem1;
	private float hesodiem2;
	private float hesodiem3;
	private float hesodiem4;
	private float hesodiem5;

	public Subject() {
		
	}
	
	public Subject(String subjectCode, String subjectName, int credit) {
		super();
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.credit = credit;
	}
	public Subject(String subjectCode, String subjectName, int credit,float hesodiem1,float hesodiem2,float hesodiem3,float hesodiem4,float hesodiem5) {
		 this(subjectCode, subjectName, credit);
		    this.hesodiem1 = hesodiem1;
		    this.hesodiem2 = hesodiem2;
		    this.hesodiem3 = hesodiem3;
		    this.hesodiem4 = hesodiem4;
		    this.hesodiem5 = hesodiem5;
	}
		

	public int getCredit() {
		return credit;
	}

	public String getSubjectCode() {
		return subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public float getHesodiem1() {
		return hesodiem1;
	}

	public void setHesodiem1(float hesodiem1) {
		this.hesodiem1 = hesodiem1;
	}

	public float getHesodiem2() {
		return hesodiem2;
	}

	public void setHesodiem2(float hesodiem2) {
		this.hesodiem2 = hesodiem2;
	}

	public float getHesodiem3() {
		return hesodiem3;
	}

	public void setHesodiem3(float hesodiem3) {
		this.hesodiem3 = hesodiem3;
	}

	public float getHesodiem4() {
		return hesodiem4;
	}

	public void setHesodiem4(float hesodiem4) {
		this.hesodiem4 = hesodiem4;
	}

	public float getHesodiem5() {
		return hesodiem5;
	}

	public void setHesodiem5(float hesodiem5) {
		this.hesodiem5 = hesodiem5;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	} 
	public void enterSubject (Scanner sc) {
		System.out.println("Nhap vao ma mon hoc: ");
		this.subjectCode = sc.nextLine();
		System.out.println("Nhap vao ten mon hoc: ");
		this.subjectName = sc.nextLine();
		System.out.println("Nhap vao so tin chi: ");
		this.credit = sc.nextInt();
		System.out.println("Nhap vao he so diem 1: ");
		this.hesodiem1 = sc.nextFloat();
		System.out.println("Nhap vao he so diem 2: ");
		this.hesodiem2 = sc.nextFloat();
		System.out.println("Nhap vao he so diem 3: ");
		this.hesodiem3 = sc.nextFloat();
		System.out.println("Nhap vao he so diem 4: ");
		this.hesodiem4 = sc.nextFloat();
		System.out.println("Nhap vao he so diem 5: ");
		this.hesodiem5 = sc.nextFloat();
		
	}
	@Override
	public String toString() {
		 return 	 "\nMa mon hoc: " + subjectCode
		    		+ "\nTen mon hoc: " + subjectName
		    		+ "\nSo tin chi: " + credit
		    		+ "\nHe so diem 1: " + hesodiem1
		 			+ "\nHe so diem 2: " + hesodiem2
		 			+ "\nHe so diem 3: " + hesodiem3
		 			+ "\nHe so diem 4: " + hesodiem4
		 			+ "\nHe so diem 5: " + hesodiem5;
	}
		
}
