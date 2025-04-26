
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class CtrinhChinh {
    private HashMap<Integer, Tuan> danhSachTuan = new HashMap<>();

    public void loadHTML(String filePath) throws Exception {
        Document doc = Jsoup.parse(new File(filePath), "UTF-8");

        Elements rows = doc.select("tr");
        for (Element row : rows) {
            Elements cols = row.select("td");
            if (cols.size() < 10) continue;  // Bỏ dòng tiêu đề

            String maMon = cols.get(0).text();
            String tenMon = cols.get(1).text();
            int tietBatDau = Integer.parseInt(cols.get(9).text().trim());
            int soTiet = Integer.parseInt(cols.get(10).text().trim());
            String phong = cols.get(11).text();
            String giangVien = cols.get(12).text();
            int thu = Integer.parseInt(cols.get(8).text().trim());
            int tuan = 1; // Em tạm hardcode tuần = 1 nha, lát mình sửa sau khi có dữ liệu tuần chính xác!

            LichHoc lichHoc = new LichHoc(maMon, tenMon, tietBatDau, soTiet, phong, giangVien);

            danhSachTuan.putIfAbsent(tuan, new Tuan());
            danhSachTuan.get(tuan).getThu(thu).themLichHoc(lichHoc);
        }
    }

    public void xemThoiKhoaBieuHomNay() {
        LocalDate today = LocalDate.now();
        int thu = today.getDayOfWeek().getValue() + 1;
        int tuan = 1; // Em sẽ thêm cách xác định tuần từ ngày nếu cần nha

        Tuan t = danhSachTuan.get(tuan);
        if (t != null) {
            Thu th = t.getThu(thu);
            if (th != null) {
                th.getDanhSachLichHoc().forEach(System.out::println);
            } else {
                System.out.println("Không có lịch học hôm nay");
            }
        } else {
            System.out.println("Không có lịch học tuần này");
        }
    }

    public void xemThoiKhoaBieuTuan(int tuan) {
        Tuan t = danhSachTuan.get(tuan);
        if (t != null) {
            for (int i = 2; i <= 8; i++) {
                Thu th = t.getThu(i);
                if (th != null && !th.getDanhSachLichHoc().isEmpty()) {
                    System.out.println("Thứ " + i + ":");
                    th.getDanhSachLichHoc().forEach(System.out::println);
                }
            }
        } else {
            System.out.println("Không có dữ liệu tuần này");
        }
    }

    public void xemThoiKhoaBieuTuanThu(int tuan, int thu) {
        Tuan t = danhSachTuan.get(tuan);
        if (t != null) {
            Thu th = t.getThu(thu);
            if (th != null) {
                th.getDanhSachLichHoc().forEach(System.out::println);
            } else {
                System.out.println("Không có lịch học ngày này");
            }
        } else {
            System.out.println("Không có dữ liệu tuần này");
        }
    }

    public void xemThoiKhoaBieuNgay(LocalDate ngay) {
        int thu = ngay.getDayOfWeek().getValue() + 1;
        int tuan = 1; // Phần xác định tuần tự động em sẽ hỗ trợ sau nhaa

        xemThoiKhoaBieuTuanThu(tuan, thu);
    }

    public static void main(String[] args) throws Exception {
        CtrinhChinh ctrinh = new CtrinhChinh();
        Scanner sc = new Scanner(System.in);

        ctrinh.loadHTML("thoikhoabieu.html");

        while (true) {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Xem thời khóa biểu hôm nay");
            System.out.println("2. Xem thời khóa biểu theo tuần");
            System.out.println("3. Xem thời khóa biểu theo tuần + thứ");
            System.out.println("4. Xem thời khóa biểu theo ngày cụ thể");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int chon = sc.nextInt();
            switch (chon) {
                case 1:
                    ctrinh.xemThoiKhoaBieuHomNay();
                    break;
                case 2:
                    System.out.print("Nhập tuần: ");
                    int tuan = sc.nextInt();
                    ctrinh.xemThoiKhoaBieuTuan(tuan);
                    break;
                case 3:
                    System.out.print("Nhập tuần: ");
                    int tuan2 = sc.nextInt();
                    System.out.print("Nhập thứ (2-8): ");
                    int thu = sc.nextInt();
                    ctrinh.xemThoiKhoaBieuTuanThu(tuan2, thu);
                    break;
                case 4:
                    System.out.print("Nhập ngày (yyyy-mm-dd): ");
                    String ngayStr = sc.next();
                    ctrinh.xemThoiKhoaBieuNgay(LocalDate.parse(ngayStr));
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Nhập sai nhaa anh yêu ơi!");
            }
        }
    }
}
