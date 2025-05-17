package com.vnua.edu.thoikhoabieu;

import java.time.LocalDate;

import controller.Controller;

public class ChuongTrinhChinh {
	private static ChuongTrinhChinh instance; 
	public static ChuongTrinhChinh getInstance() {
        if (instance == null) {
            instance = new ChuongTrinhChinh();
        }
        return instance;
    }
	private LocalDate firstDayOfTerm;
	 public static void main(String[] args) {
	        Controller controller = new Controller();
	        controller.start();
	    }
	public LocalDate getFirstDayOfTerm() {
		return firstDayOfTerm;
	}
	public void setFirstDayOfTerm(LocalDate firstDayOfTerm) {
		this.firstDayOfTerm = firstDayOfTerm;
	}
}
