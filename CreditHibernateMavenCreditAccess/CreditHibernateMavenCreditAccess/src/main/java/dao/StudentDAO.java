package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import credit.CreditHibernateUtil;
import entity.Student;

public class StudentDAO {
	@SuppressWarnings("unchecked")
	public static List<Student> getAllStudent() {
		
		Transaction transaction = null;
		List<Student> listOfStudent = null;
        try (Session session = CreditHibernateUtil.getSessionFactory().openSession()) {
			
	        transaction = session.beginTransaction();
	        
	        // Fetch all students from the 'Student' table
	        listOfStudent = session.createQuery("from Student", Student.class).getResultList();
	        
	        // Commit the transaction
	        transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return listOfStudent;
	}
	
    public static Student getStudent(int id) {
        Transaction transaction = null;
        Student student = null;
        try (Session session = CreditHibernateUtil.getSessionFactory().openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();

            // Get the student object by ID
            student = session.get(Student.class, id);

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return student;
    }
    
    public static void main(String[] args) {
    	List<Student> studentList = StudentDAO.getAllStudent();
    	studentList.forEach(std->System.out.println(std));
    }
}
