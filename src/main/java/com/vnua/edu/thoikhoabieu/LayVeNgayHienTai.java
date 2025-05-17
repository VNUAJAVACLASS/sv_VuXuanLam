package com.vnua.edu.thoikhoabieu;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LayVeNgayHienTai {  
    public static int getWeekFromDate(LocalDate date) {
        LocalDate startDate = ChuongTrinhChinh.getInstance().getFirstDayOfTerm();
        if (startDate == null) {
            System.out.println("⚠️ Chưa set ngày bắt đầu học kỳ!");
            return -1;
        }
        long daysBetween = ChronoUnit.DAYS.between(startDate, date);
        return (int) (daysBetween / 7) + 1;
    }

    public static int getThuFromDate(LocalDate date) {
        int thu = date.getDayOfWeek().getValue(); 
        return thu + 1;
    }

    public static LocalDate getStartDate() {
        return ChuongTrinhChinh.getInstance().getFirstDayOfTerm();
    }
}
