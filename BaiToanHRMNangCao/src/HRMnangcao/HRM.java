package HRMnangcao;

import java.util.HashMap;
import java.util.Scanner;

public class HRM {
	private HashMap<String,Human> hrList = new HashMap<String,Human>();
	private HashMap<String,iCreditSubject> subList = new HashMap<String,iCreditSubject>();

	public HRM() {
		
	}
	
	public void addHm(Human hm) {
		this.hrList.put(hm.getCode(), hm);
	}
	
	public void addSub(iCreditSubject sub) {
		this.subList.put(sub.getSubjectCode(), sub);
	}
	
	public void addHR(Scanner sc) {
        System.out.print("Nhap loai nhan su (1 - Lecturer, 2 - Student): ");
        int type = sc.nextInt();
        sc.nextLine();
        
        Human hm;
        if (type == 1) {
            hm = new Lecturer();
            hm.enterInfo(sc);
        } else if (type == 2) {
            hm = new Student();
            hm.enterInfo(sc);
        } else {
            System.out.println("Loai nhan su khong hop le!");
            return;
        }

        hm.enterInfo(sc);
        this.addHm(hm);
	}
	
	public void printHRList() {
		System.out.println("Danh sach nhan su la:");
		for (Human human : hrList.values()) {
            System.out.println(human);		}
	}
	
	public void printLecturer() {
		System.out.println("Danh sach giang vien la: ");
		for (Human human : hrList.values()) {
			if (human instanceof Lecturer) {
	            System.out.println(human);			}
		}
	}
	
	public void printStudent() {
		System.out.println("Danh sach sinh vien la: ");
		for (Human human : hrList.values()) {
			if (human instanceof Student) {
	            System.out.println(human);			}
		}
	}
	
	public Human searchHuman(String code) {
		for (Human human : hrList.values()) {
			if (human.getCode().toLowerCase().equals(code)) {
				return human;
			}
		}
		return null;
	}
	
	
	public void registerSubject(Scanner sc) {
	    System.out.println("Chon sinh vien: ");
	    printStudent();
	    System.out.print("Nhap ma sinh vien muon dang ky: ");
	    String studentCode = sc.nextLine();
	    
	   
	    Student student = (Student) searchHuman(studentCode);
	    if (student == null) {
	        System.out.println("Sinh viên không tồn tại!");
	        return;
	    }

	    System.out.println("Chon mon hoc muon dang ky: ");
	    System.out.print("Nhap ma mon hoc muon dang ky: ");

	  
	   
	    
	    System.out.println("Đăng ký môn học thành công!");
	}

	public void initDemoData() {
		addHm(new Lecturer("MTI01", "Nguyen Van A", "Ha Noi"));
        addHm(new Lecturer("MTI02", "Tran Thi B", "Hai Phong"));
        addHm(new Student("671330", "Tran Viet V", "VNUA" , "Ha Nam"));   
        addHm(new Student("671101", "Pham Thanh D", "VNUA"));
        addHm(new Student("671605", "Vu Xuan Lam", "K67CNPMB","Nam Dinh"));
		iCreditSubject javaSub = new JavaSubject("TH01","Lap trinh java",3);
		iCreditSubject pythonSub = new PythonSubject("TH02", "Lap trinh Python", 2); 

		addSub(pythonSub);
		addSub(javaSub);
    }

	
}