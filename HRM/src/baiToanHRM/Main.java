package baiToanHRM;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
	        HRM hrm = new HRM(); 

	        System.out.println("=== QUẢN LÝ NHÂN SỰ ===");
	        System.out.println("1. Khởi tạo dữ liệu mẫu");
	        System.out.println("2. Nhập dữ liệu nhân sự từ bàn phím");
	        System.out.print("Chọn phương thức nhập dữ liệu: ");
	        
	        int choice = sc.nextInt();
	        sc.nextLine();

	        if (choice == 1) {
	            hrm.initDemoData();
	        } else if (choice == 2) {
	            hrm.initDemoData(sc);
	        } else {
	            System.out.println("Lựa chọn không hợp lệ, thoát chương trình.");
	            return;
	        }

	        // In danh sách nhân sự sau khi nhập
	        System.out.println("\nDanh sách nhân sự:");
	        hrm.printHRList();

	        sc.close();
    }
}
