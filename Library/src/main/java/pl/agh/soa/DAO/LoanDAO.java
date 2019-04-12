package pl.agh.soa.DAO;

import pl.agh.soa.entities.Book;
import pl.agh.soa.entities.LibraryUser;
import pl.agh.soa.entities.Loan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public LoanDAO(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Library");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addLoan(Book book, LibraryUser user, Integer numberOfDays) throws Exception{
        Date from = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(from);
        c.add(Calendar.DATE, numberOfDays);
        Date to = c.getTime();

        Loan loan = new Loan(book, user, from, to);

        entityManager.getTransaction().begin();
        entityManager.persist(loan);
        entityManager.getTransaction().commit();
    }

    public List<Loan> getUserLoans(int id) throws Exception{
        TypedQuery<Loan> loan =  entityManager.createQuery("SELECT l FROM loan l WHERE l.user.id = :id", Loan.class).setParameter("id", id);
        return loan.getResultList();
    }

    public Book changeLoanStatus(Integer id) throws Exception{
        Loan loan = entityManager.createQuery("SELECT l FROM loan l WHERE l.id = :id", Loan.class).setParameter("id", id).getSingleResult();
        Book book = loan.getBookID();
        loan.setLoaned(false);
        entityManager.getTransaction().begin();
        entityManager.merge(loan);
        entityManager.getTransaction().commit();
        return book;
    }
}
