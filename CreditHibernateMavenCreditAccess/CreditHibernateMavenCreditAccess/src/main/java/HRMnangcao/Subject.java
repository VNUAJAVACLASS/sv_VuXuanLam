package HRMnangcao;

import java.util.Scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_subject")
public class Subject {

    @Id
    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "credit")
    private int credit;

    @Column(name = "heso_diem1")
    private float hesodiem1;

    @Column(name = "heso_diem2")
    private float hesodiem2;

    @Column(name = "heso_diem3")
    private float hesodiem3;

    @Column(name = "heso_diem4")
    private float hesodiem4;

    @Column(name = "heso_diem5")
    private float hesodiem5;

    // ==== Constructors ====

    public Subject() {}

    public Subject(String subjectCode, String subjectName, int credit) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    public Subject(String subjectCode, String subjectName, int credit,
                   float hesodiem1, float hesodiem2, float hesodiem3,
                   float hesodiem4, float hesodiem5) {
        this(subjectCode, subjectName, credit);
        this.hesodiem1 = hesodiem1;
        this.hesodiem2 = hesodiem2;
        this.hesodiem3 = hesodiem3;
        this.hesodiem4 = hesodiem4;
        this.hesodiem5 = hesodiem5;
    }

    // ==== Getters and Setters ====

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
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

    // ==== ToString ====
    @Override
    public String toString() {
        return "\nMã môn học: " + subjectCode
             + "\nTên môn học: " + subjectName
             + "\nSố tín chỉ: " + credit
             + "\nHệ số điểm 1: " + hesodiem1
             + "\nHệ số điểm 2: " + hesodiem2
             + "\nHệ số điểm 3: " + hesodiem3
             + "\nHệ số điểm 4: " + hesodiem4
             + "\nHệ số điểm 5: " + hesodiem5;
    }
}
