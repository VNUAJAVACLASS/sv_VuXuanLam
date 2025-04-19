package HRMnangcao;

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
	@Override
	public void enterInfo(Scanner sc) {
		System.out.println("Nhap vao ten: ");
		this.fullname = sc.nextLine();
		System.out.println("Nhap vao ma code: ");
		this.code = sc.nextLine();
		System.out.println("Nhap vao dia chi: ");
		this.address = sc.nextLine();
		System.out.println("Nhap vao password: ");
		this.password = sc.nextLine();
	}

	@Override
	public String toString() {
		 StringBuilder stringb = new StringBuilder();
		 stringb.append("====================////==================\n");
		 stringb.append("Ho va ten giang vien: ").append(fullname).append("\n");
		 stringb.append("Ma giang vien: ").append(code).append("\n");
		 stringb.append("Đia chi: ").append(address).append("\n");
		 stringb.append("Password: ").append(password).append("\n");
			return stringb.toString();
	}
}
