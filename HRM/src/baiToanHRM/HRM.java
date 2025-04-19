package baiToanHRM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HRM {
	private List<Human> hrList;
	
	public HRM() {
	        this.hrList = new ArrayList<>(); 
	}
	//Ham them nguoi vao list
	public void addHR(Human hm) {
		hrList.add(hm);
	}
	
	//Ham xac dinh xem la giang vien hay la sinh vien
	public void addHR(Scanner sc) {
		int loai;
		System.out.println("Chon loai nhan su (1 gv, 2 sv): ");
		loai=sc.nextInt();
		sc.nextLine();
		
		Human human =null;
		if(loai == 1) {
			human = new Lecturer();
		}
		else if(loai == 2 ) {
			human = new Student();
		}
		
		human.enterInfo(sc);
		addHR(human);
	}
	
	//ham in ra danh sach hu man
	public void printHRList()
	{
		for (Human human: hrList)
		{
			System.out.print(human);
		}
	}
	//Ham in ra thong tin giang vien
	
	public void printLecturerinfo() {
		System.out.println("Danh sach giang vien: ");
		for(Human human: hrList)
		{
			if(human instanceof Lecturer)
			{
				
				
				System.out.println(human);
			}
		}
	}
	
	public void printStudentList()
	{
		System.out.println("Danh sach sinh vien: ");
		for(Human human: hrList)
		{
			if(human instanceof Student)
			{
				Student std = (Student)human;
				System.out.println("Mã sinh viên: " + std.getCode() + ", Lớp: " + std.getClass_());
			}
		}
	}
	// Hàm khởi tạo dữ liệu minh họa
	public void initDemoData() {
	    
	    Lecturer gv1 = new Lecturer("GV001", "Nguyễn Văn A", "Hà Nội");
	    Lecturer gv2 = new Lecturer("GV002", "Trần Thị B", "Hung Yen");
	    Lecturer gv3 = new Lecturer("GV001", "Nguyễn Văn A", "Hà Nội");
	    Lecturer gv4 = new Lecturer("GV002", "Trần Thị B", "Hung Yen");

	    // Thêm sinh viên mẫu
	    Student sv1 = new Student("SV001", "Phạm Văn C", "Hải Phòng", "CNPM");
	    Student sv2 = new Student("SV002", "Lê Thị D", "Đà Nẵng", "CNTT");
	    
	    // Thêm vào danh sách
	    addHR(gv1);
	    addHR(gv2);
	    addHR(gv3);
	    addHR(gv4);
	    addHR(sv1);
	    addHR(sv2);
	}
	
	public void initDemoData(Scanner sc) {
	    System.out.print("Nhập số lượng nhân sự cần thêm: ");
	    int n = sc.nextInt();
	    sc.nextLine();

	    for (int i = 0; i < n; i++) {
	        System.out.println("Nhập nhân sự thứ " + (i + 1) + ":");
	        addHR(sc);
	    }

	    System.out.println("Dữ liệu nhân sự đã được nhập thành công.");
	}
	
	
	//ham search human dua tren code
	 public String searchHuman(String code) {
	        for (Human h : hrList) {
	            if (h.getCode().equals(code)) {
	                return h.toString();
	            }
	        }
	        return "Not found!";
	    }

}
