package HRMnangcao;

public interface iCreditSubject {
	
	public int getCredit();
	public String getSubjectCode();
	public String getSubjectName();
	public float calConversionMark(String grade);
	public String calGrade();
	public float calSubjectMark();
}
