package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HRM {
	private List<Human> hrList = new LinkedList<Human>();

	public HRM() {
		
	}
	
	public void addHR(Human hm) {
		this.hrList.add(hm);
	}
	
	public void addHR(Scanner sc) {
        System.out.print("Nhap loai nhan su (1 - Lecturer, 2 - Student): ");
        int type = sc.nextInt();
        sc.nextLine();
        
        Human hm;
        if (type == 1) {
            hm = new Lecturer();
        } else if (type == 2) {
            hm = new Student();
        } else {
            System.out.println("Loai nhan su khong hop le!");
            return;
        }

        hm.enterInfo(sc);
        this.hrList.add(hm);
	}
	
	public void printHRList() {
		System.out.println("Danh sach nhan su la:");
		for (Human human : hrList) {
            System.out.println(human);		}
	}
	
	public void printLecturer() {
		System.out.println("Danh sach nhan su la:");
		for (Human human : hrList) {
			if (human instanceof Lecturer) {
	            System.out.println(human);			}
		}
	}
	
	public void printStudent() {
		System.out.println("Danh sach nhan su la:");
		for (Human human : hrList) {
			if (human instanceof Student) {
	            System.out.println(human);			}
		}
	}
	
	public String searchHuman(String code) {
		for (Human human : hrList) {
			if (human.getCode().equals(code)) {
				return human.toString();
			}
		}
		return "Khong tim thay";
	}
	
	public void initDemoData() {
		hrList.add(new Lecturer("MTI01", "Nguyen Van A", "Ha Noi"));
        hrList.add(new Lecturer("MTI02", "Tran Thi B", "Hai Phong"));
        hrList.add(new Student("671259", "Doan Minh Dai", "Ninh Binh"));
        hrList.add(new Student("671101", "Pham Thanh D", "Ha Nam"));
    }

	public void initDemoData(Scanner sc) {
		System.out.println("Nhap so luong nhan su: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            addHR(sc);
        }
	}
}