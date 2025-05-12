package com.vnua.edu.thoikhoabieu;

import java.util.ArrayList;
import java.util.List;

public class Ngay {
	private List<MonHoc> danhSachMon = new ArrayList<MonHoc>();
	
	public void themMonHoc(MonHoc mh) {
		danhSachMon.add(mh);
	}
	
	public List<MonHoc> getDanhSachMon(){
		return danhSachMon;
	}
}
