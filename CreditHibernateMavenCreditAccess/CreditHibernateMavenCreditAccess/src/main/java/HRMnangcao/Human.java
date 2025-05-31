package HRMnangcao;

import java.util.Scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Human {
	@Id
	@Column(name= "user_code")
	protected String code;
	@Column(name ="fullname")
	protected String fullname;
	@Column(name ="address")
	protected String address;
	@Column(name ="type")
	protected int type;

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
	
	public Human(String code, String fullname, String address,int type){
		this(code,fullname,address);
		this.type=type;
	}
	
	public abstract void enterInfo(Scanner sc);
		

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
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
