package entity;

import java.util.Scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public class Human {
	@Id
	@Column(name = "user_code")
	protected String code;

	@Column(name = "fullname")
	protected String fullname;
	
	@Column(name = "address")
	protected String address;
	
	public Human(){
		
	};
	
	public Human(String code){
		this.code = code;
	}

	public Human(String code, String fullname){
		this.code = code;
		this.fullname = fullname;
	}
	
	public Human(String code, String fullname, String address){
		this.code = code;
		this.fullname = fullname;
		this.address = address;
	}
	
	public void enterInfo(Scanner sc){
		System.out.println("Nhap vao ten: ");
		this.fullname = sc.nextLine();
		System.out.println("Nhap vao ma code: ");
		this.code = sc.nextLine();
		System.out.println("Nhap vao dia chi: ");
		this.address = sc.nextLine();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "Human [address=" + address + ", code=" + code + ", fullname=" + fullname + "]";
	}
}
