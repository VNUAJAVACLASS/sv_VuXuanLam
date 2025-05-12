package com.vnua.edu.thoikhoabieu;

import controller.Controller;

public class ChuongTrinhChinh {
	private String firstDayOfTerm;
	 public static void main(String[] args) {
	        Controller controller = new Controller();
	        controller.start();
	    }
	public String getFirstDayOfTerm() {
		return firstDayOfTerm;
	}
	public void setFirstDayOfTerm(String firstDayOfTerm) {
		this.firstDayOfTerm = firstDayOfTerm;
	}
}
