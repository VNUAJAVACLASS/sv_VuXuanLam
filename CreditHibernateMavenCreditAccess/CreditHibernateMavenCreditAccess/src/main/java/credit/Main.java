package credit;

import entity.HRM;

public class Main {
	public static void main(String[] args) {
        HRM hrm = new HRM();
        hrm.initDemoData();
        
//        hrm.printHRList();
        System.out.println(hrm.searchHuman("MTI01"));
	}
}
