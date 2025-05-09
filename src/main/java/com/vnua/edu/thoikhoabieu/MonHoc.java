package com.vnua.edu.thoikhoabieu;

import java.util.ArrayList;
import java.util.List;

public class MonHoc {
	private String maMonHoc;
	public String getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public int getNhom() {
		return nhom;
	}

	public void setNhom(int nhom) {
		this.nhom = nhom;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getSoTinChi() {
		return soTinChi;
	}

	public void setSoTinChi(int soTinChi) {
		this.soTinChi = soTinChi;
	}

	public int getSoTietLyThuyet() {
		return soTietLyThuyet;
	}

	public void setSoTietLyThuyet(int soTietLyThuyet) {
		this.soTietLyThuyet = soTietLyThuyet;
	}

	public int getSoTietThucHanh() {
		return soTietThucHanh;
	}

	public void setSoTietThucHanh(int soTietThucHanh) {
		this.soTietThucHanh = soTietThucHanh;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}

	public int getTietBatDau() {
		return tietBatDau;
	}

	public void setTietBatDau(int tietBatDau) {
		this.tietBatDau = tietBatDau;
	}

	public int getSoTiet() {
		return soTiet;
	}

	public void setSoTiet(int soTiet) {
		this.soTiet = soTiet;
	}

	public String getPhongHoc() {
		return phongHoc;
	}

	public void setPhongHoc(String phongHoc) {
		this.phongHoc = phongHoc;
	}

	public String getGiangVien() {
		return giangVien;
	}

	public void setGiangVien(String giangVien) {
		this.giangVien = giangVien;
	}

	private String tenMonHoc;
	private int nhom;
	private int to;
	private int soTinChi;
	private int soTietLyThuyet;
	private int soTietThucHanh;
	private String lop;
	private int tietBatDau;
	private int soTiet;
	private String phongHoc;
	private String giangVien;
	
	public MonHoc() {}
	
	 public MonHoc(String maMonHoc, String tenMonHoc, int nhom,int to, int soTinChi, int soTietLyThuyet, int soTietThucHanh, String lop) {
	        this.maMonHoc = maMonHoc;
	        this.tenMonHoc = tenMonHoc;
	        this.nhom = nhom;
	        this.to = to;
	        this.soTinChi = soTinChi;
	        this.soTietLyThuyet = soTietLyThuyet;
	        this.soTietThucHanh = soTietThucHanh;
	        this.lop = lop;
	    }
	 
	 public MonHoc(MonHoc mhk) {
	        this.maMonHoc = mhk.maMonHoc;
	        this.tenMonHoc = mhk.tenMonHoc;
	        this.nhom = mhk.nhom;
	        this.to = mhk.to;
	        this.soTinChi = mhk.soTinChi;
	        this.soTietLyThuyet = mhk.soTietLyThuyet;
	        this.soTietThucHanh = mhk.soTietThucHanh;
	        this.lop = mhk.lop;
	    }
}
