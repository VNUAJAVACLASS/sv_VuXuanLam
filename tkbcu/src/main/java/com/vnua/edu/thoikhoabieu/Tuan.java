package com.vnua.edu.thoikhoabieu;

import java.util.HashMap;
import java.util.Map;

public class Tuan {
	private Map<Integer,Ngay> danhSachNgay = new HashMap<>();
	
	public void addNgay(Integer thu, Ngay ngay) {
		danhSachNgay.put(thu,ngay);
    }
	
	 public Map<Integer,Ngay> getDanhSachNgay() {
	        return danhSachNgay;
	 }
	 public Ngay getNgay(Integer thu) {
	        return danhSachNgay.get(thu);
	 }
}
