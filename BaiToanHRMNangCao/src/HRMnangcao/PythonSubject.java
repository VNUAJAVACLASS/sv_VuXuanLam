package HRMnangcao;

public class PythonSubject extends Subject{
	private float attendanceMark;
	private float midExamMark1;
	private float midExamMark2;
	private float finalExamMark;
	
	public PythonSubject() {
		super();
	}
	
	public PythonSubject(String subjectName, String SubjectCode, int credit) {
		super(subjectName,SubjectCode,credit);
	}

	public void setAttendanceMark(float attendanceMark) {
		this.attendanceMark = attendanceMark;
	}

	public void setMidExamMark1(float midExamMark1) {
		this.midExamMark1 = midExamMark1;
	}
	
	public void setMidExamMark2(float midExamMark2) {
		this.midExamMark2 = midExamMark2;
	}
	
	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}

	
	@Override
	public String toString() {
	    return super.toString()
	           + "\nDiem chuyen can: " + attendanceMark
	           + "\nDiem giua ky: " + midExamMark1
	           + "\nDiem de cuong: " + midExamMark2
	           + "\nDiem cuoi ky: " + finalExamMark
	           + "\nDiem tong ket(10): " + calSubjectMark()
	           + "\nDiem tong ket(4)" + super.calGrade()
	           + "\n===============//==================";

	}

	@Override
	public float calSubjectMark() {
		return attendanceMark*0.1f + midExamMark1 * 0.2f +  midExamMark2 * 0.2f + finalExamMark* 0.5f;
	}
}
