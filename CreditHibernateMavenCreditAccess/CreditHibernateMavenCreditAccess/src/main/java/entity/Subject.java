package entity;

public class Subject {
	private String subjectCode;
	private String subjectName;
	private int credit;
	private float attendanceMark;
	private float midExamMark;
	private float finalExamMark;
	
	public Subject() {
		
	}
	
	public Subject(String subjectCode, String subjectName, int credit) {
		super();
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.credit = credit;
	}
	
	public float calConversionMark() {
		return (attendanceMark * 0.1f) + (midExamMark * 0.3f) + (finalExamMark * 0.6f);

	}
	
	public float calConversionMark(String grade) {
        switch (grade) {
        case "A": return 4.0f;
        case "B+": return 3.5f;
        case "B": return 3.0f;
        case "C+": return 2.5f;
        case "C": return 2.0f;
        case "D+": return 1.5f;
        case "D": return 1.0f;
        default: return 0.0f;
        }
	}
	
	public String calGrade() {
        float subjectMark = calConversionMark();
        
        if (subjectMark >= 8.5) return "A";
        if (subjectMark >= 7.5) return "B+";
        if (subjectMark >= 7.0) return "B";
        if (subjectMark >= 6.5) return "C+";
        if (subjectMark >= 6.0) return "C";
        if (subjectMark >= 5.5) return "D+";
        if (subjectMark >= 5.0) return "D";
        return "F";
	}


	public int getCredit() {
		return credit;
	}


	public void setAttendanceMark(float attendanceMark) {
		this.attendanceMark = attendanceMark;
	}


	public void setMidExamMark(float midExamMark) {
		this.midExamMark = midExamMark;
	}


	public void setFinalExamMark(float finalExamMark) {
		this.finalExamMark = finalExamMark;
	}

	@Override
	public String toString() {
		return "Subject [subjectCode=" + subjectCode + ", subjectName=" + subjectName + ", credit=" + credit
				+ ", attendanceMark=" + attendanceMark + ", midExamMark=" + midExamMark + ", finalExamMark="
				+ finalExamMark + "]";
	}
		
}
