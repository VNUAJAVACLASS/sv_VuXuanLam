package com.vnua.edu.thoikhoabieu;

import java.time.LocalDate;
import java.util.Map;

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
        int tuan = LayVeNgayHienTai.getWeekFromDate(today);
        int thu = LayVeNgayHienTai.getThuFromDate(today);

        System.out.println("Hôm nay là: " + today + " (Tuần " + tuan + ", " + inThu(thu) + ")");

        Tuan t = tkb.getDanhSachTuan().get(tuan);
        if (t == null) {
            System.out.println("Không có dữ liệu thời khóa biểu cho tuần này.");
            return;
        }

        Ngay ngay = t.getNgay(thu);
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
	            System.out.println("     🛌 Không có môn học.");
	            return;
	        }
	        for (MonHoc mh : ngay.getDanhSachMon()) {
	            inMon(mh);
	        }
	    }
	 private void inMon(MonHoc mh) {
	        System.out.println("    Môn học:" + mh.getMaMonHoc() + " - " + mh.getTenMonHoc());
	        System.out.println("    Tiết BD: " + mh.getTietBatDau() + ", Số tiết: " + mh.getSoTiet());
	        System.out.println("    Phòng: " + mh.getPhongHoc() + ", GV: " + mh.getGiangVien());
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

	        for (int thu = 2; thu <= 7; thu++) { 
	            Ngay ngay = t.getNgay(thu);
	            LocalDate date = LayVeNgayHienTai.START_DATE.plusDays((tuan - 1) * 7 + (thu - 2));
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
	            System.out.println("====== Tuần " + soTuan + " ======");
	            printAll(soTuan, tuan);
	        }
	    }


	    private void printAll(int soTuan, Tuan tuan) {
	        for (Map.Entry<Integer, Ngay> entryNgay : tuan.getDanhSachNgay().entrySet()) {
	            int thu = entryNgay.getKey();
	            Ngay ngay = entryNgay.getValue();
	            LocalDate date = LayVeNgayHienTai.START_DATE.plusDays((soTuan - 1) * 7 + (thu - 2));
	            inNgay(thu, date, ngay);
	        }
	    }
}
