package entity;

import java.util.Scanner;

public class Lecturer extends Human{
	private String password;

	public Lecturer() {
		
	}
	 
	public Lecturer(String code, String password) {
		super(code);
		this.password = password;
	}
	
	public Lecturer(String code, String fullname, String address) {
		super(code, fullname, address);
	}
	
	public void enterInfo(Scanner sc) {
		super.enterInfo(sc);
	}

	@Override
	public String toString() {
		return "Lecturer [password=" + password + ", address=" + address + ", code=" + code + ", fullname=" + fullname
				+ "]";
	}
}
