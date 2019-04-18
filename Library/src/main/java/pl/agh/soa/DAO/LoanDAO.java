package pl.agh.soa.DAO;

import pl.agh.soa.entities.Book;
import pl.agh.soa.entities.LibraryUser;
import pl.agh.soa.entities.Loan;

import javax.persistence.*;
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

    public List<LibraryUser> getUsersByBook(Book pickedBook) throws Exception{
        TypedQuery<LibraryUser> users = entityManager.createQuery("SELECT DISTINCT l.user FROM loan l WHERE l.bookID = :book", LibraryUser.class).setParameter("book", pickedBook);
        return users.getResultList();
    }

    public List<LibraryUser> getUsersByBookWithDates(Book book, Date filterFromDate, Date filterToDate) {
        TypedQuery<LibraryUser> users = entityManager
                .createQuery("SELECT DISTINCT l.user FROM loan l WHERE l.bookID = :book AND l.loanDate BETWEEN :fromDate AND :toDate", LibraryUser.class)
                .setParameter("book", book)
                .setParameter("fromDate", filterFromDate)
                .setParameter("toDate", filterToDate);
        return users.getResultList();
    }

    public List<LibraryUser> getUsersByBooks(List<Book> books) throws Exception{
        TypedQuery<LibraryUser> users = entityManager.createQuery("SELECT DISTINCT l.user FROM loan l WHERE l.bookID IN :books", LibraryUser.class).setParameter("books", books);
        return users.getResultList();
    }

    public List<LibraryUser> getUsersByBooksWithDates(List<Book> books, Date filterFromDate, Date filterToDate) throws Exception{
        TypedQuery<LibraryUser> users = entityManager
                .createQuery("SELECT DISTINCT l.user FROM loan l WHERE l.bookID IN :books AND l.loanDate BETWEEN :fromDate AND :toDate", LibraryUser.class)
                .setParameter("books", books)
                .setParameter("fromDate", filterFromDate)
                .setParameter("toDate", filterToDate);
        return users.getResultList();
    }

    public List<Book> getBooksByUser(LibraryUser libraryUser) throws Exception{
        TypedQuery<Book> books = entityManager
                .createQuery("SELECT DISTINCT l.bookID FROM loan l WHERE l.user = :user", Book.class)
                .setParameter("user", libraryUser);
        return books.getResultList();
    }

    public List<Book> getBooksByUser(LibraryUser libraryUser, Date filterFromDate, Date filterToDate) throws Exception{
        TypedQuery<Book> books = entityManager
                .createQuery("SELECT DISTINCT l.bookID FROM loan l WHERE l.user = :user AND l.loanDate BETWEEN :fromDate AND :toDate", Book.class)
                .setParameter("user", libraryUser)
                .setParameter("fromDate", filterFromDate)
                .setParameter("toDate", filterToDate);
        return books.getResultList();
    }

    public List<Object[]> getLoanedAuthors() throws Exception{
        TypedQuery<Object[]> query = entityManager
                .createQuery("SELECT a, COUNT(a) FROM loan l JOIN book b ON l.bookID = b JOIN author a ON b.author = a GROUP BY a ORDER BY COUNT(a) DESC ", Object[].class);
        return query.getResultList();
    }
}
