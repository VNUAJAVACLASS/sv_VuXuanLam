package HRMnangcao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.IUserDAO;
import model.UserDAO;

public class HRM {
	private HashMap<String,Human> hrList = new HashMap<String,Human>();
	private HashMap<String,Subject> subList = new HashMap<String,Subject>();

	private IUserDAO iUserdao;
	public HRM() {
		iUserdao = new UserDAO();
		
	}
	
	public void addHm(Human hm) {
		this.hrList.put(hm.getCode(), hm);
	}
	
	public void addSub(Subject sub) {
		this.subList.put(sub.getSubjectCode(), sub);
	}
	
	public void addHR(Scanner sc) {
        System.out.print("Nhap loai nhan su (1 - Lecturer, 0 - Student): ");
        int type = sc.nextInt();
        sc.nextLine();
        
        Human hm;
        if (type == 1) {
            hm = new Lecturer();
            hm.enterInfo(sc);
            hm.setType(type);
            iUserdao.addUser(hm);
        } else if (type == 0) {
        	 hm = new Student();
             hm.enterInfo(sc);
             hm.setType(type);
             iUserdao.addUser(hm);
        } else {
            System.out.println("Loai nhan su khong hop le!");
            return;
        }

	}
	
	public void printHRList() {
		List<Human> userList = new ArrayList<Human>();
		userList = iUserdao.getAllUsers();
		System.out.println("Danh sach user la: ");
		for(Human user :userList) {
			System.out.println(user);
		}
	}
	
	public void printLecturer() {
		List<Human> userList = new ArrayList<Human>();
		userList = iUserdao.getAllUsers();
		System.out.println("Danh sach giang vien la: ");
		for(Human user :userList) {
			if(user instanceof Lecturer) {
			System.out.println(user);
			}
		}
	}
	
	public void printStudent() {
		List<Human> userList = new ArrayList<Human>();
		userList = iUserdao.getAllUsers();
		System.out.println("Danh sach sinh vien la: ");
		for(Human user :userList) {
			if(user instanceof Student) {
			System.out.println(user);
			}
		}
	}
	
	public Human searchHuman(String code) {
		Human user=null;
		user=iUserdao.searchUserId(code);
		
		return user;
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

	   
	  
	}

	public void initDemoData() {
		addHm(new Lecturer("MTI01", "Nguyen Van A", "Ha Noi"));
        addHm(new Lecturer("MTI02", "Tran Thi B", "Hai Phong"));
        addHm(new Student("671330", "Tran Viet V", "VNUA" , "Ha Nam"));   
        addHm(new Student("671101", "Pham Thanh D", "VNUA"));
        addHm(new Student("671605", "Vu Xuan Lam", "K67CNPMB","Nam Dinh"));

    }

	
}