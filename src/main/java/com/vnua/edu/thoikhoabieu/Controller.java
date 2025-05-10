package com.vnua.edu.thoikhoabieu;

import java.time.LocalDate;
import java.util.Scanner;

public class Controller {
	private static final String TKB_URL = "D:\\sv_VuXuanLam\\tkb_vuxuanlam.html";
    private LichHocService lhService;

    public Controller() {
        this.lhService = new LichHocService(TKB_URL);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
        	System.out.println("==============TKB VNUA==================");
            System.out.println("1. Thời khóa biểu cả kỳ");
            System.out.println("2. Thời khóa biểu hôm nay");
            System.out.println("3. Thời khóa biểu tuần hiện tại");
            System.out.println("4. Thời khóa biểu theo tuần");
            System.out.println("5. Thời khóa biểu theo ngày");
            System.out.println("6. Thoát");
            System.out.print("============Mời bạn chọn=============");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                	lhService.printAllTKB();
                    break;
                case 2:
                	lhService.printTKBToday();
                    break;
                case 3:
                	lhService.inTKBTuanHienTai();
                    break;
                case 4:
                    // Nhập tuần từ người dùng
                    System.out.print("Nhập số tuần: ");
                    int tuan = scanner.nextInt();
                    lhService.inTKBTheoTuan(tuan);
                    break;
                case 5:
                    // Nhập ngày từ người dùng
                    System.out.print("Nhập ngày (yyyy-MM-dd): ");
                    String dateInput = scanner.next();
                    LocalDate date = LocalDate.parse(dateInput);
                    lhService.inTKBTheoNgay(date);
                    break;
                case 6:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
