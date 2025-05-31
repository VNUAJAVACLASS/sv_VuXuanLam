package HRMnangcao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentDAO {

    @SuppressWarnings("unchecked")
    public static List<Student> getAllStudents() {
        Transaction transaction = null;
        List<Student> listOfStudents = null;

        try (Session session = CreditHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            listOfStudents = session.createQuery("from Student").list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return listOfStudents;
    }
}
