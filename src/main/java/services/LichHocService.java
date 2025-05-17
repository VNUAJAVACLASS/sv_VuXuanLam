package services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.vnua.edu.thoikhoabieu.DocDuLieu;
import com.vnua.edu.thoikhoabieu.LayVeNgayHienTai;
import com.vnua.edu.thoikhoabieu.LichHoc;
import com.vnua.edu.thoikhoabieu.MonHoc;
import com.vnua.edu.thoikhoabieu.Ngay;
import com.vnua.edu.thoikhoabieu.Tuan;

public class LichHocService {
    private final LichHoc tkb;

    public LichHocService(String url) {
        try {
            this.tkb = DocDuLieu.parseTKB(url);
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc thời khóa biểu từ URL: " + url, e);
        }
    }

    public void printTKBToday() {
        LocalDate today = LocalDate.now();

        System.out.println("Hôm nay là: " + today);

        // Lấy Ngay trực tiếp từ map theo ngày
        Ngay ngay = tkb.getNgayTheoNgay(today);

        if (ngay == null) {
            System.out.println("Không có dữ liệu thời khóa biểu cho ngày hôm nay.");
            return;
        }

        int thu = LayVeNgayHienTai.getThuFromDate(today);
        System.out.println("(" + inThu(thu) + ")");

        inNgay(thu, today, ngay);
    }


    private String inThu(int thu) {
        switch (thu) {
            case 2: return "Thứ 2";
            case 3: return "Thứ 3";
            case 4: return "Thứ 4";
            case 5: return "Thứ 5";
            case 6: return "Thứ 6";
            case 7: return "Thứ 7";
            case 8: return "Chủ Nhật";
            default: return "Không xác định";
        }
    }

    private void inNgay(int thu, LocalDate date, Ngay ngay) {
        System.out.println(inThu(thu) + (date != null ? " (" + date + ")" : "") + ":");
        if (ngay == null || ngay.getDanhSachMon().isEmpty()) {
            System.out.println("Không có môn học.");
            return;
        }
        for (MonHoc mh : ngay.getDanhSachMon()) {
            inMon(mh);
        }
    }

    private void inMon(MonHoc mh) {
        String thoiGian = doiTietSangGio(mh.getTietBatDau(), mh.getSoTiet());
        System.out.println("Mã môn:" + mh.getMaMonHoc() +
                           "|| Tên môn: " + mh.getTenMonHoc() +
                           "|| Tiết bắt đầu: " + mh.getTietBatDau() +
                           "|| Số tiết: " + mh.getSoTiet() +
                           "|| Thời gian: " + thoiGian +
                           "|| Phòng học: " + mh.getPhongHoc() +
                           "|| Giảng viên: " + mh.getGiangVien());
    }

    public void inTKBTuanHienTai() {
        LocalDate today = LocalDate.now();
        int tuan = LayVeNgayHienTai.getWeekFromDate(today);

        System.out.println("Hôm nay: " + today + " → Tuần " + tuan);

        Tuan t = tkb.getDanhSachTuan().get(tuan);
        if (t == null) {
            System.out.println("Không có dữ liệu thời khóa biểu cho tuần này.");
            return;
        }

        // Lấy ngày bắt đầu học kỳ từ class đã set
        LocalDate startDate = LayVeNgayHienTai.getStartDate();
        if (startDate == null) {
            System.out.println("⚠️ Chưa có ngày bắt đầu học kỳ được thiết lập!");
            return;
        }

        for (int thu = 2; thu <= 7; thu++) {
            Ngay ngay = t.getNgay(thu);
            // Tính ngày thực tế dựa trên startDate
            LocalDate date = startDate.plusDays((tuan - 1) * 7 + (thu - 2));
            inNgay(thu, date, ngay);
        }
    }


    public void inTKBTheoTuan(int tuan) {
        Tuan t = tkb.getDanhSachTuan().get(tuan);
        if (t == null) {
            System.out.println("Không có thời khóa biểu cho tuần " + tuan);
            return;
        }
        System.out.println("Thời khóa biểu tuần " + tuan + ":");
        printAll(tuan, t);
    }

    public void inTKBTheoNgay(LocalDate date) {
        int tuan = LayVeNgayHienTai.getWeekFromDate(date);
        int thu = LayVeNgayHienTai.getThuFromDate(date);

        Tuan t = tkb.getDanhSachTuan().get(tuan);
        if (t == null) {
            System.out.println("Không có thời khóa biểu cho tuần " + tuan);
            return;
        }

        Ngay ngay = t.getNgay(thu);
        System.out.println("" + date + " (Tuần " + tuan + ", " + inThu(thu) + "):");
        inNgay(thu, date, ngay);
    }

    public void printAllTKB() {
        Map<Integer, Tuan> danhSachTuan = tkb.getDanhSachTuan();

        if (danhSachTuan.isEmpty()) {
            System.out.println("Không có tuần nào trong thời khoá biểu.");
            return;
        }

        for (Map.Entry<Integer, Tuan> entryTuan : danhSachTuan.entrySet()) {
            int soTuan = entryTuan.getKey();
            Tuan tuan = entryTuan.getValue();
            System.out.println("====== Thời khóa biểu tuần " + soTuan + " ======");
            printAll(soTuan, tuan);
        }
    }

    private void printAll(int soTuan, Tuan tuan) {
        LocalDate startDate = LayVeNgayHienTai.getStartDate(); // Lấy ngày bắt đầu động
        if (startDate == null) {
            System.out.println("⚠️ Chưa có ngày bắt đầu học kỳ được thiết lập!");
            return;
        }

        for (Map.Entry<Integer, Ngay> entryNgay : tuan.getDanhSachNgay().entrySet()) {
            int thu = entryNgay.getKey();
            Ngay ngay = entryNgay.getValue();
            // Tính ngày thực tế
            LocalDate date = startDate.plusDays((soTuan - 1) * 7 + (thu - 2));
            inNgay(thu, date, ngay);
        }
    }


    public static String doiTietSangGio(int tietBatDau, int soTiet) {
        int tietDuration = 50;
        int breakDuration = 5;

        int startHour = 7;
        int startMinute = 0;

        int totalMinutes = 0;
        for (int i = 1; i < tietBatDau; i++) {
            totalMinutes += tietDuration + breakDuration;
        }

        int gioBatDau = startHour + (startMinute + totalMinutes) / 60;
        int phutBatDau = (startMinute + totalMinutes) % 60;

        int duration = soTiet * tietDuration + (soTiet - 1) * breakDuration;
        int gioKetThuc = gioBatDau + (phutBatDau + duration) / 60;
        int phutKetThuc = (phutBatDau + duration) % 60;

        return String.format("%02d:%02d - %02d:%02d", gioBatDau, phutBatDau, gioKetThuc, phutKetThuc);
    }
}