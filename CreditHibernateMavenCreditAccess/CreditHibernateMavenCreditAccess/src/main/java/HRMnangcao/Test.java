package HRMnangcao;

import java.util.List;

public class Test {
	public static void main(String[] args) {
        List<Student> students = StudentDAO.getAllStudents();

        for (Student s : students) {
            System.out.println(s); // Tự động gọi toString()
        }
    }
}
