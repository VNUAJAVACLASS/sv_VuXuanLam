package HRMnangcao;

import java.util.Scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name ="Lecturer")
public class Lecturer extends Human{
	@Column(name ="password")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Lecturer() {
		
	}
	 
	public Lecturer(String code, String password) {
		super(code);
		this.password = password;
	}
	
	public Lecturer(String code, String fullname, String address) {
		super(code, fullname, address);
	}
	

	public Lecturer(String code, String fullname, String address,String password) {
		super(code, fullname, address);
		this.password=password;
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
