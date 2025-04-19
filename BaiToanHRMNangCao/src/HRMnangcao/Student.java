package HRMnangcao;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student extends Human {
	private String class_;
	private Map<String,iCreditSubject> subjectList = new HashMap<String,iCreditSubject>();
	
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

	public Student(String code, String fullname, String class_, String address){
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

	    for (HashMap.Entry<String, iCreditSubject> entry : subjectList.entrySet()) {
	        iCreditSubject iCreditSubject = entry.getValue();
	        sumCredit += iCreditSubject.getCredit();
	        sumGrade += iCreditSubject.calConversionMark(iCreditSubject.calGrade()) * iCreditSubject.getCredit();
	    }
	    return (float) sumGrade / sumCredit;
	}
	
	public HashMap<String, iCreditSubject> searchSubject(String key) {
	    HashMap<String, iCreditSubject> resultSearch = new HashMap<>();
	    for (HashMap.Entry<String, iCreditSubject> entry : subjectList.entrySet()) {
	    	iCreditSubject iCreditSubject = entry.getValue();
	        if (iCreditSubject.getSubjectName().contains(key)) {
	        	resultSearch.put(entry.getKey(), iCreditSubject);
	        }
	    }
	    return resultSearch;
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
		for (iCreditSubject s : subjectList.values()) {
		    	stringb.append(s).append("\n");
		}	
		stringb.append("Diem trung binh hoc ky: ").append(calTermAverageMark()).append("\n");
		return stringb.toString();
	}
}
