	package com.vnua.edu.thoikhoabieu;
	
	import java.io.IOException;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.time.temporal.ChronoUnit;
	
	public class LayVeNgayHienTai {  
	
	    private static final String PATH = "src/main/java/resources/firstDayOfTerm.txt";
	
	    // Hàm đọc ngày bắt đầu học kỳ từ file và set vào singleton ChuongTrinhChinh
	    public static void loadStartDateFromFile() {
	        try {
	            String dateString = Files.readString(Path.of(PATH)).trim();
	            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
	            LocalDate startDate = LocalDate.parse(dateString, formatter);
	
	            ChuongTrinhChinh.setFirstDayOfTerm(startDate);
	            System.out.println("📅 Đã load ngày bắt đầu học kỳ từ file: " + startDate);
	
	        } catch (IOException e) {
	            System.err.println("❌ Lỗi đọc file startDateOfTerm.txt: " + e.getMessage());
	        } catch (Exception e) {
	            System.err.println("❌ Lỗi parse ngày từ file: " + e.getMessage());
	        }
	    }
	
	    public static int getWeekFromDate(LocalDate date) {
	        LocalDate startDate = ChuongTrinhChinh.getFirstDayOfTerm();
	
	        if (startDate == null) {
	            System.out.println("⚠️ Không có ngày bắt đầu học kỳ được thiết lập!");
	            return -1;
	        }
	
	        long daysBetween = ChronoUnit.DAYS.between(startDate, date);
	        return (int) (daysBetween / 7) + 1;
	    }
	
	    public static int getThuFromDate(LocalDate date) {
	        int thu = date.getDayOfWeek().getValue();
	        return thu;
	    }
	
	    public static LocalDate getStartDate() {
	        return ChuongTrinhChinh.getFirstDayOfTerm();
	    }
	}
