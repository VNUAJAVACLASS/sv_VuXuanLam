package baiToanHRM;

import java.util.Scanner;

public class Human {
	protected String fullname;
	protected String code;
	protected String address;
	
	public Human() {}
	
	public Human(String code) {
		this.code=code;
	}
	
	public Human(String code, String fullname ) {
		this.code=code;
		this.fullname=fullname;
	}
	
	public Human(String code, String fullname, String address) {
		this.code= code;
		this.fullname=fullname;
		this.address=address;
	}
	
	public void enterInfo(Scanner sc) {
		System.out.println("Nhap vao ma: ");
		code=sc.nextLine();
		System.out.println("\nNhap vao ho ten: ");
		fullname=sc.nextLine();
		System.out.println("\nNhap vao dia chi: ");
		address=sc.nextLine();
	}
	
	//Get 
	public String getAddress() {
		return address;
	}
	public String getFullName() {
		return fullname;
	}
	public String getCode() {
		return code;
	}
	
	//Set
	
	public void setCode(String code) {
		this.code=code;
	}
	public void setFullName(String fullname) {
		this.fullname=fullname;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	
	@Override
	public String toString() {
		return code + "-" + fullname + "-" + address;
		
	}
}
