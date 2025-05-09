package com.vnua.edu.thoikhoabieu;

import java.util.HashMap;
import java.util.Map;

public class LichHoc {
	private Map<Integer,Tuan> dsTuan = new HashMap();

    public void addTuan(Integer tuanThu,Tuan tuan) {
        dsTuan.put(tuanThu, tuan);
    }

    public Map<Integer,Tuan> getDanhSachTuan() {
        return dsTuan;
    }

    public Tuan getTuan(Integer tuanThu) {
        return dsTuan.get(tuanThu);
    }
}
