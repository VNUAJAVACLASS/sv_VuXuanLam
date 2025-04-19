package baiToanHRM;

import java.util.Scanner;

public class Lecturer extends Human {
	private String password;
	
	//Ham khong tham so
	public Lecturer() {}
	
	//Ham 2 tham so
	public Lecturer(String code,String password) {
		super(code);
		this.password=password;
	}
	//Ham 3 tham so
	public Lecturer(String code, String fullname, String address)
	{
		super(code, fullname, address);
	}
	
	//Nhap vao thong tin
	public void enterInfo(Scanner sc)
	{
		super.enterInfo(sc);//goi thong tin cua lop cha
		System.out.print("Nhap vao mat khau: ");
		password=sc.nextLine();
	}
	
	@Override
	public String toString() {
		return code + " - " + fullname + " - " + password;
	}
	
	
}
