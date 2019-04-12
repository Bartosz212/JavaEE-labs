package pl.agh.soa;


import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.util.List;


@Named
@RequestScoped
public class BookManager {

    @PersistenceContext(unitName = "Books")
    private EntityManager em;
    @Resource
    UserTransaction utx;

    private List<Book> books;

    private String name;
    private String surname;
    private String title;
    private String isbn;
    private Integer year;
    private Integer price;

    private String deleteISBN;

    private String changeName;
    private String changeSurname;
    private String changeTitle;
    private String changeISBN;
    private Integer changeYear;
    private Integer changePrice;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void add() throws Exception {
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

    public void change() throws Exception{
        if(changeISBN != null){
            utx.begin();
            Book book = null;
            try {
                book = (Book) em.createQuery("SELECT b FROM book b WHERE b.ISBN LIKE :changeISBN").setParameter("changeISBN", changeISBN).getSingleResult();
            } catch(Exception e){

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


    public void deleteBook() throws Exception{
        System.out.println("usuwanie\n");
        if(deleteISBN != null) {
            utx.begin();
            Book book = null;
            try {
                book = (Book) em.createQuery("SELECT b FROM book b WHERE b.ISBN LIKE :deleteISBN").setParameter("deleteISBN", deleteISBN).getSingleResult();
            } catch(Exception e){

            }
            if(book!=null){
                em.remove(book);
            }
            utx.commit();
        }
    }

    public List<Book> getBooks() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM book b ORDER BY b.title", Book.class);
        setBooks(query.getResultList());
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getDeleteISBN() {
        return deleteISBN;
    }

    public void setDeleteISBN(String deleteISBN) {
        this.deleteISBN = deleteISBN;
    }

    public String getChangeISBN() {
        return changeISBN;
    }

    public void setChangeISBN(String changeISBN) {
        this.changeISBN = changeISBN;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getChangeSurname() {
        return changeSurname;
    }

    public void setChangeSurname(String changeSurname) {
        this.changeSurname = changeSurname;
    }

    public String getChangeTitle() {
        return changeTitle;
    }

    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle;
    }

    public Integer getChangeYear() {
        return changeYear;
    }

    public void setChangeYear(Integer changeYear) {
        this.changeYear = changeYear;
    }

    public Integer getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(Integer changePrice) {
        this.changePrice = changePrice;
    }
}
