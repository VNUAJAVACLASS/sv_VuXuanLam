package com.vnua.edu.thoikhoabieu;

import java.util.Scanner;

public class NguoiDung {
	private String taiKhoan;
	private String matKhau;
	private String hocKyDaChon;

	public NguoiDung() {}
	
	public NguoiDung(String taiKhoan, String matKhau) {
		this.taiKhoan=taiKhoan;
		this.matKhau=matKhau;
	}
	
	public String getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public void dangNhap(Scanner sc) {
		System.out.println("Nhap vao ten tai khoan: ");
		taiKhoan=sc.nextLine();
		System.out.println("\nNhap vao mat khau: ");
		matKhau=sc.nextLine();
	}

	public String getHocKyDaChon() {
		return hocKyDaChon;
	}

	public void setHocKyDaChon(String hocKyDaChon) {
		this.hocKyDaChon = hocKyDaChon;
	}
}
