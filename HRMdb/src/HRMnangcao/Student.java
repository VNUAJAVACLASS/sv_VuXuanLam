package HRMnangcao;
import java.util.HashMap;
import java.util.Scanner;

public class Student extends Human {
	private String class_;
	private HashMap<String,Subject> subjectList = new HashMap<String,Subject>();
	
	public Student(){
		
	}
	
	public Student(String code){
		super(code);
	}
	
	public Student(String code, String fullname){
		super(code, fullname);
	}
	
	public Student(String code, String fullname, String class_){
		super(code, fullname);
		this.class_ = class_;
	}

	public Student(String code, String fullname, String address,String class_){
		super(code, fullname, address);
		this.class_ = class_;
	}

	public void addSubject(Subject sub) {
		subjectList.put(sub.getSubjectCode(), sub);
	}
	public void removeSubject(String key) {
		subjectList.remove(key);
	}
	
	public float calTermAverageMark() {
		int sumCredit = 0;
	    int sumGrade = 0;
	    StudentSubject stdsub = new StudentSubject();
	    for (HashMap.Entry<String, Subject> entry : subjectList.entrySet()) {
	        Subject subject = entry.getValue();
	        sumCredit += subject.getCredit();
	        sumGrade += stdsub.calConversionMark(stdsub.calGrade()) * subject.getCredit();
	    }
	    return (float) sumGrade / sumCredit;
	}
	
	
	@Override
	public void enterInfo(Scanner sc) {
		System.out.println("Nhap vao ten: ");
		this.fullname = sc.nextLine();
		System.out.println("Nhap vao ma code: ");
		this.code = sc.nextLine();
		System.out.println("Nhap vao dia chi: ");
		this.address = sc.nextLine();
		System.out.println("Nhap vao lop: ");
		this.class_ = sc.nextLine();
	}

	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Override
	public String toString() {
		StringBuilder stringb = new StringBuilder();
		 stringb.append("====================////==================\n");
		 stringb.append("Ho va ten sinh vien: ").append(fullname).append("\n");
		 stringb.append("Ma sinh vien: ").append(code).append("\n");
		 stringb.append("Đia chi: ").append(address).append("\n");
		 stringb.append("Lop: ").append(class_).append("\n");
		 if (subjectList.isEmpty()) {
			 stringb.append("Sinh vien khong co mon hoc nao trong ky");
			 return stringb.toString();
		 }
		stringb.append("Danh sach mon hoc:\n");
		for (Subject s : subjectList.values()) {
		    	stringb.append(s).append("\n");
		}	
		stringb.append("Diem trung binh hoc ky: ").append(calTermAverageMark()).append("\n");
		return stringb.toString();
	}
}
