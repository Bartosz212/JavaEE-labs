package pl.agh.soa.DAO;

import pl.agh.soa.entities.Author;
import pl.agh.soa.entities.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    public BookDAO(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Library");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Book> getAllBooks() throws Exception{
        TypedQuery<Book> user = entityManager.createQuery("SELECT b FROM book b ORDER BY b.title", Book.class);
        return user.getResultList();
    }

    public Book getBookByID(int id) throws Exception{
        return (Book) entityManager.createQuery("SELECT b FROM book b WHERE b.id = :id").setParameter("id", id).getSingleResult();
    }

    public Boolean checkIfAvailable(int id){
        Integer number = (Integer) entityManager.createQuery("SELECT b.numberOfItems FROM book b WHERE b.id = :id").setParameter("id", id).getSingleResult();
        if(number>0){
            return true;
        }else {
            return false;
        }
    }

    public void addBook(Author author, String title, int number) throws Exception{
        Book add = new Book(author, title, number);
        entityManager.getTransaction().begin();
        entityManager.persist(add);
        entityManager.getTransaction().commit();
    }

    public void decrementNumberOfBooks(Book book) throws Exception{
        book.setNumberOfItems(book.getNumberOfItems()-1);
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
    }

    public void deleteBook(int pickedBook) throws Exception{
        Book book = (Book) entityManager.createQuery("SELECT b FROM book b WHERE b.id = :id").setParameter("id", pickedBook).getSingleResult();
        entityManager.getTransaction().begin();
        entityManager.remove(book);
        entityManager.getTransaction().commit();
    }

    public void incrementNumberOfBooks(Book book) throws Exception{
        book.setNumberOfItems(book.getNumberOfItems()+1);
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
    }
}
