package HRMnangcao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HRM hrm = new HRM();
        boolean check = true;
        hrm.initDemoData();
        Scanner sc = new Scanner(System.in);
        SubjectController scontroller = new SubjectController();

        while (check) {
            System.out.println("Chon chuc nang:");
            System.out.println("1. Xem toan bo danh sach nhan su.");
            System.out.println("2. Xem danh sach sinh vien.");
            System.out.println("3. Xem danh sach giang vien.");
            System.out.println("4. Tim nhan su.");
            System.out.println("5. Them nhan su.");
            System.out.println("6. Danh sach mon hoc.");
            System.out.println("7. Them mon hoc.");
            System.out.println("8. Dang ky mon hoc.");
            System.out.println("9. Thoát chương trình.");
            System.out.print("Chuc nang ban chon la: ");
            
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    hrm.printHRList();
                    waitForZero(sc);
                    break;
                case 2:
                    hrm.printStudent();
                    waitForZero(sc);
                    break;
                case 3:
                    hrm.printLecturer();
                    waitForZero(sc);
                    break;
                case 4:
                    System.out.println("Nhap vao ma code cua nhan su:");
                    String key = sc.nextLine();
                    System.out.println(hrm.searchHuman(key));
                    waitForZero(sc);
                    break;
                case 5:
                    hrm.addHR(sc);
                    waitForZero(sc);
                    break;
                case 6:
                	scontroller.printSubjectList();
                    waitForZero(sc);
                    break;
                case 7:
                    scontroller.addSubject(sc);
                    waitForZero(sc);
                    break;
                case 8:
                    scontroller.dangKyMonHoc(sc);
                    waitForZero(sc);
                    break;
                case 9:
                	System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    return;
                default:
                    System.out.println("Nhap sai thong tin");
                    break;
            }
        }
    }

    private static void waitForZero(Scanner sc) {
        System.out.println("\n>> Nhap '0' de quay lai menu...");
        while (!sc.nextLine().equals("0")) {
            System.out.println(">>>Vui long nhap '0' de quay lai menu");
        }
    }
}
