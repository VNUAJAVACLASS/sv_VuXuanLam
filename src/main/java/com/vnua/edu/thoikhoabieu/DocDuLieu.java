package com.vnua.edu.thoikhoabieu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public class DocDuLieu {
	public static LichHoc parseTKB(String filePath) throws Exception {
	    InputStream inputStream = new FileInputStream(filePath);
	    
	    Document doc = Jsoup.parse(inputStream, String.valueOf(StandardCharsets.UTF_8), "");
	    LichHoc tkb = new LichHoc();
	    
	    Element table = doc.select("table").first();
	    Elements rows = table.select("tr");
	    MonHoc lastMonHoc = null;
	    
	    for (Element row : rows) {
	        Elements cols = row.select("td");
	        
	        if (!cols.isEmpty() && !cols.get(0).attr("rowspan").isEmpty()) {
	            String maMonHoc = cols.get(0).text();
	            String tenMonHoc = cols.get(1).text();
	            String[] nhomTo = cols.get(2).text().split("-");

	            int nhom = 0, to = 0;
	            if (nhomTo.length == 2) {
	                nhom = Integer.parseInt(nhomTo[0]);
	                to = Integer.parseInt(nhomTo[1]);
	            } else if (nhomTo.length == 1) {
	                nhom = Integer.parseInt(nhomTo[0]);
	                to = 0;
	            }

	            int soTinChi = Integer.parseInt(cols.get(3).text());
	            int soTietHocLT = cols.get(4).text().isEmpty() ? 0 : Integer.parseInt(cols.get(4).text());
	            int soTietHocTH = cols.get(5).text().isEmpty() ? 0 : Integer.parseInt(cols.get(5).text());
	            String lopHoc = cols.get(6).text();
	            int thu = cols.get(8).text().equals("CN") ? 8 : Integer.parseInt(cols.get(8).text());
	            int tietBD = Integer.parseInt(cols.get(9).text());
	            int soTiet = Integer.parseInt(cols.get(10).text());
	            String phong = cols.get(11).text();
	            String gv = cols.get(12).text();
	            List<Integer> tuan = extractWeeks(cols.get(13).text());

	            lastMonHoc = new MonHoc(maMonHoc, tenMonHoc, nhom, to, soTinChi, soTietHocLT, soTietHocTH, lopHoc);
	            addMonHoc(tkb, lastMonHoc, thu, tietBD, soTiet, phong, gv, tuan);
	        } else if (!cols.isEmpty()) {
	            int thu = cols.get(0).text().equals("CN") ? 8 : Integer.parseInt(cols.get(0).text());
	            int tietBD = Integer.parseInt(cols.get(1).text());
	            int soTiet = Integer.parseInt(cols.get(2).text());
	            String phong = cols.get(3).text();
	            String gv = cols.get(4).text();
	            List<Integer> tuan = extractWeeks(cols.get(5).text());
	            addMonHoc(tkb, lastMonHoc, thu, tietBD, soTiet, phong, gv, tuan);
	        }
	    }
	    return tkb;
	}
	
	private static void addMonHoc(LichHoc tkb, MonHoc lastMonHoc, int thu, int tietBD, int soTiet, String phong, String gv, List<Integer> tuan) {
        for (Integer s : tuan) {
            MonHoc mh = new MonHoc(lastMonHoc);
            if (tkb.getDanhSachTuan().containsKey(s)){
                Tuan t = tkb.getDanhSachTuan().get(s);
                Ngay n = t.getNgay(thu);
                if (n == null) {
                    n = new Ngay();
                    t.addNgay(thu, n);
                }
                mh.setTietBatDau(tietBD);
                mh.setSoTiet(soTiet);
                mh.setPhongHoc(phong);
                mh.setGiangVien(gv);
                n.themMonHoc(mh);
            }else {
                Tuan t = new Tuan();
                Ngay n = new Ngay();
                mh.setTietBatDau(tietBD);
                mh.setSoTiet(soTiet);
                mh.setPhongHoc(phong);
                mh.setGiangVien(gv);
                n.themMonHoc(mh);
                t.addNgay(thu,n);
                tkb.addTuan(s,t);
            }
        }
    }
	public static List<Integer> extractWeeks(String weeksText) {
	    List<Integer> weeks = new ArrayList<>();
	    // Kiểm tra nếu tuần học có dấu "-" và có dữ liệu hợp lệ
	    if (weeksText != null && !weeksText.isEmpty()) {
	        String[] weekParts = weeksText.split("-");
	        if (weekParts.length == 2) {
	            try {
	                int startWeek = Integer.parseInt(weekParts[0].trim());
	                int endWeek = Integer.parseInt(weekParts[1].trim());
	                for (int i = startWeek; i <= endWeek; i++) {
	                    weeks.add(i);
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Lỗi khi chuyển đổi tuần học: " + weeksText);
	            }
	        } else if (weekParts.length == 1) {
	            try {
	                int week = Integer.parseInt(weekParts[0].trim());
	                weeks.add(week);
	            } catch (NumberFormatException e) {
	                System.out.println("Lỗi khi chuyển đổi tuần học: " + weeksText);
	            }
	        }
	    }
	    return weeks;
	}

}
