package com.vnua.edu.thoikhoabieu;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LayVeNgayHienTai {
	   public static final LocalDate START_DATE = LocalDate.of(2025, 1, 13); 

	    public static int getWeekFromDate(LocalDate date) {
	        long daysBetween = ChronoUnit.DAYS.between(START_DATE, date);
	        return (int)(daysBetween / 7) + 1;
	    }

	    public static int getThuFromDate(LocalDate date) {
	        int thu = date.getDayOfWeek().getValue(); 
	        return thu + 1;
	    }
}
