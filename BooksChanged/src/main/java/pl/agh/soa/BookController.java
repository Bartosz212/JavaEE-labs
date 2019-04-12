package pl.agh.soa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.util.List;

public class BookController {

    private EntityManager em;
    private UserTransaction utx;

    public BookController(EntityManager em, UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    public List<Book> getBooks() throws Exception{
        TypedQuery<Book> query = em.createQuery("SELECT b FROM book b ORDER BY b.title", Book.class);
        return query.getResultList();
    }

    public void add(String name, String surname, String title, String isbn, Integer year, Integer price) throws Exception{
        System.out.println("Controller: add!");
        Book book = new Book();
        book.setYear(year);
        book.setPrice(price);
        book.setTitle(title);
        book.setISBN(isbn);
        book.setName(name);
        book.setSurname(surname);

        utx.begin();
        em.persist(book);
        try {
            utx.commit();
        } catch (Exception ex){
            try{
                System.out.println("Try: "+ex.getMessage());
                utx.rollback();
            } catch (Exception re) {
                System.out.println("Rollback: "+re.getMessage());
            }
        }
    }

    public void change(String changeISBN, String changeName, String changeSurname, String changeTitle, Integer changeYear, Integer changePrice) throws Exception{
        System.out.println("Controller: change!");
        if(changeISBN != null){
            utx.begin();
            Book book = null;
            try {
                book = (Book) em.createQuery("SELECT b FROM book b WHERE b.ISBN LIKE :changeISBN").setParameter("changeISBN", changeISBN).getSingleResult();
            } catch(Exception e){
                System.out.println("Message(Controller - Change): "+ e.getMessage());
            }
            if(book != null){
                if(changeName.length() != 0)
                    book.setName(changeName);
                if(changeSurname.length() != 0)
                    book.setSurname(changeSurname);
                if(changeTitle.length() != 0)
                    book.setTitle(changeTitle);
                if(changeYear != null)
                    book.setYear(changeYear);
                if(changePrice != null)
                    book.setPrice(changePrice);
                em.merge(book);
            }
            utx.commit();
        }
    }

    public void delete(String deleteISBN) throws Exception{
        System.out.println("Controller: delete!");
        if(deleteISBN != null) {
            utx.begin();
            Book book = null;
            try {
                book = (Book) em.createQuery("SELECT b FROM book b WHERE b.ISBN LIKE :deleteISBN").setParameter("deleteISBN", deleteISBN).getSingleResult();
            } catch(Exception e){
                System.out.println("Message(Controller - Delete): "+ e.getMessage());
            }
            if(book!=null){
                em.remove(book);
            }
            utx.commit();
        }
    }
}
