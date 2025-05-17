package com.vnua.edu.thoikhoabieu;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LichHoc {
    private Map<Integer, Tuan> dsTuan = new HashMap<>();
    private Map<LocalDate, Ngay> dsNgayTheoNgay = new HashMap<>();

    public void addTuan(Integer tuanThu, Tuan tuan) {
        dsTuan.put(tuanThu, tuan);
        updateNgayMap(tuanThu, tuan);
    }

    public Map<Integer, Tuan> getDanhSachTuan() {
        return dsTuan;
    }

    public Tuan getTuan(Integer tuanThu) {
        return dsTuan.get(tuanThu);
    }
    
    // Lấy Ngay theo LocalDate
    public Ngay getNgayTheoNgay(LocalDate date) {
        return dsNgayTheoNgay.get(date);
    }

    // Cập nhật map ngày theo LocalDate
    private void updateNgayMap(Integer tuanThu, Tuan tuan) {
        if (tuan == null) return;

        LocalDate startDate = LayVeNgayHienTai.getStartDate();
        if (startDate == null) {
            System.out.println("⚠️ Ngày bắt đầu học kỳ chưa được thiết lập!");
            return;
        }

        for (Map.Entry<Integer, Ngay> entryNgay : tuan.getDanhSachNgay().entrySet()) {
            int thu = entryNgay.getKey();
            Ngay ngay = entryNgay.getValue();
            // Tính ngày tương ứng dựa vào startDate
            LocalDate date = startDate.plusDays((tuanThu - 1) * 7 + (thu - 2)); // Vì thứ 2 là .plusDays(0)
            dsNgayTheoNgay.put(date, ngay);
        }
    }

}
