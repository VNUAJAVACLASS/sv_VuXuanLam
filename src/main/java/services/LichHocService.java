package services;

import java.time.LocalDate;
import java.util.Map;

import com.vnua.edu.thoikhoabieu.DocDuLieu;
import com.vnua.edu.thoikhoabieu.LayVeNgayHienTai;
import com.vnua.edu.thoikhoabieu.LichHoc;
import com.vnua.edu.thoikhoabieu.MonHoc;
import com.vnua.edu.thoikhoabieu.Ngay;
import com.vnua.edu.thoikhoabieu.Tuan;

/*
 * Lớp dịch vụ quản lý và xử lý thời khóa biểu
 * Cung cấp các phương thức để in thời khóa biểu theo ngày, tuần, hoặc toàn bộ
 */
public class LichHocService {
    // Biến lưu trữ đối tượng LichHoc đã đọc được từ nguồn dữ liệu
    private final LichHoc tkb;

    /*
     * Hàm khởi tạo lớp LichHocService,
     * đọc dữ liệu thời khóa biểu từ đường dẫn URL được truyền vào.
     * Nếu không đọc được sẽ ném ra ngoại lệ RuntimeException.
     */
    public LichHocService(String url) {
        try {
            this.tkb = DocDuLieu.parseTKB(url);
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc thời khóa biểu từ URL: " + url, e);
        }
    }

    /*
     * In thời khóa biểu của ngày hôm nay ra màn hình console.
     * Nếu không có dữ liệu sẽ báo không có dữ liệu.
     */
    public void printTKBToday() {
        LocalDate today = LocalDate.now();

        System.out.println("Hôm nay là: " + today);

        // Lấy đối tượng Ngay ứng với ngày hiện tại
        Ngay ngay = tkb.getNgayTheoNgay(today);

        if (ngay == null) {
            System.out.println("Không có dữ liệu thời khóa biểu cho ngày hôm nay.");
            return;
        }

        int thu = LayVeNgayHienTai.getThuFromDate(today);
        System.out.println("(" + inThu(thu) + ")");

        inNgay(thu, today, ngay);
    }

    /*
     * Chuyển số thứ trong tuần thành tên thứ trong tiếng Việt.
     * Ví dụ 2 -> "Thứ 2", 8 -> "Chủ Nhật".
     * Nếu không hợp lệ trả về "Không xác định"
     */
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

    /*
     * In ra chi tiết thời khóa biểu của một ngày.
     * Nếu không có môn học sẽ báo "Không có môn học".
     * Hiển thị thứ, ngày và danh sách môn học.
     */
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

    /*
     * In chi tiết thông tin một môn học,
     * bao gồm mã môn, tên môn, tiết bắt đầu, số tiết, thời gian học,
     * phòng học và giảng viên.
     */
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

    /*
     * In toàn bộ thời khóa biểu của tuần hiện tại,
     * dựa vào ngày bắt đầu học kỳ đã được thiết lập.
     * Nếu chưa thiết lập ngày bắt đầu sẽ cảnh báo.
     */
    public void inTKBTuanHienTai() {
        LocalDate today = LocalDate.now();
        int tuan = LayVeNgayHienTai.getWeekFromDate(today);

        System.out.println("Hôm nay: " + today + " → Tuần " + tuan);

        Tuan t = tkb.getDanhSachTuan().get(tuan);
        if (t == null) {
            System.out.println("Không có dữ liệu thời khóa biểu cho tuần này.");
            return;
        }

        LocalDate startDate = LayVeNgayHienTai.getStartDate();
        if (startDate == null) {
            System.out.println("⚠️ Chưa có ngày bắt đầu học kỳ được thiết lập!");
            return;
        }

        for (int thu = 2; thu <= 7; thu++) {
            Ngay ngay = t.getNgay(thu);
            LocalDate date = startDate.plusDays((tuan - 1) * 7 + (thu - 2));
            inNgay(thu, date, ngay);
        }
    }

    /*
     * In thời khóa biểu của một tuần cụ thể.
     * Nếu không có dữ liệu tuần đó sẽ thông báo.
     */
    public void inTKBTheoTuan(int tuan) {
        Tuan t = tkb.getDanhSachTuan().get(tuan);
        if (t == null) {
            System.out.println("Không có thời khóa biểu cho tuần " + tuan);
            return;
        }
        System.out.println("Thời khóa biểu tuần " + tuan + ":");
        printAll(tuan, t);
    }

    /*
     * In thời khóa biểu theo ngày cụ thể.
     * Tính ra tuần và thứ trong tuần dựa trên ngày truyền vào.
     */
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

    /*
     * In toàn bộ thời khóa biểu của tất cả các tuần có trong dữ liệu.
     * Nếu không có tuần nào sẽ báo.
     */
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

    /*
     * Hàm hỗ trợ in tất cả các ngày trong một tuần cụ thể,
     * tính ngày thực tế dựa trên ngày bắt đầu học kỳ.
     */
    private void printAll(int soTuan, Tuan tuan) {
        LocalDate startDate = LayVeNgayHienTai.getStartDate();
        if (startDate == null) {
            System.out.println("⚠️ Chưa có ngày bắt đầu học kỳ được thiết lập!");
            return;
        }

        for (Map.Entry<Integer, Ngay> entryNgay : tuan.getDanhSachNgay().entrySet()) {
            int thu = entryNgay.getKey();
            Ngay ngay = entryNgay.getValue();
            LocalDate date = startDate.plusDays((soTuan - 1) * 7 + (thu - 2));
            inNgay(thu, date, ngay);
        }
    }

    /*
     * Chuyển đổi tiết học và số tiết thành khung giờ cụ thể.
     * Mỗi tiết học kéo dài 50 phút, nghỉ giữa các tiết 5 phút.
     * Bắt đầu từ 7h00 sáng.
     * Trả về chuỗi định dạng "giờ bắt đầu - giờ kết thúc"
     */
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
