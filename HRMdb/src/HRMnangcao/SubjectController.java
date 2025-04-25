package HRMnangcao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.SubjectDAO;


public class SubjectController {
	
	private SubjectDAO subjectdao;
	
	public SubjectController(){
		subjectdao = new SubjectDAO();
	}
	public void printSubjectList() {
		List<Subject> subjectList = new ArrayList<Subject>();
		subjectList = subjectdao.getAllSubjects();
		System.out.println("Danh sach mon hoc la: ");
		for(Subject sub :subjectList) {
			System.out.println(sub);
		}
	}
	public void addSubject(Scanner sc) {
		Subject sub = new Subject();
		sub.enterSubject(sc);
		subjectdao.addSubject(sub);
	}
	public void dangKyMonHoc(Scanner sc) {
		StudentSubject sub = new StudentSubject();
		sub.enterStudentSub(sc);
		subjectdao.addSutdenSub(sub);
		
	}

}
