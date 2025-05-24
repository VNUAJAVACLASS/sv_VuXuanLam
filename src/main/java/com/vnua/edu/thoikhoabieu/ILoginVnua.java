package com.vnua.edu.thoikhoabieu;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import com.microsoft.playwright.Page;

public interface ILoginVnua {
	String taoFileHtml(NguoiDung nguoidung);
	void dangNhapHeThong(Page page, NguoiDung nguoidung);
	String Login(NguoiDung nguoidung);
	List<String> layDanhSachHocKy(Page page);
	LocalDate startDateOfTerm(Page page);
	Path taoDuongDanFile(String tenFile);
	String chonHocKy(Page page);
	String layThoiKhoaBieu(Page page);
}
