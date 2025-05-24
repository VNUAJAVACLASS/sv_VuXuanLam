package com.vnua.edu.thoikhoabieu;

import java.time.LocalDate;
import controller.Controller;

/*
 * Lớp quản lý chương trình chính của ứng dụng,
 * lưu trữ và chia sẻ dữ liệu chung như ngày bắt đầu học kỳ.
 */
public class ChuongTrinhChinh {

    // ✅ Biến static dùng chung để lưu ngày bắt đầu học kỳ
    public static LocalDate firstDayOfTerm;

    /*
     * Phương thức main để khởi động chương trình.
     * Tạo đối tượng Controller và gọi phương thức start để bắt đầu.
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }

    /*
     * Lấy ngày bắt đầu học kỳ hiện tại.
     * Trả về ngày bắt đầu học kỳ
     */
    public static LocalDate getFirstDayOfTerm() {
        return firstDayOfTerm;
    }

    /*
     * Gán ngày bắt đầu học kỳ mới.
     * Nhận vào ngày bắt đầu học kỳ cần thiết lập
     */
    public static void setFirstDayOfTerm(LocalDate date) {
        firstDayOfTerm = date;
    }
}
