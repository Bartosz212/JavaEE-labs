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

    public List<Book> getBooks(){
        BookController bookController = new BookController(em, utx);
        List<Book> bookList = null;
        try {
            bookList = bookController.getBooks();
        }catch (Exception e){
            System.out.println("Manager (getBooks): "+e.getMessage());
        }
        setBooks(bookList);
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


    public void add(){
        BookController bookController = new BookController(em, utx);
        try {
            bookController.add(name, surname, title, isbn, year, price);
        }catch (Exception e){
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public void change(){
        BookController bookController = new BookController(em, utx);
        try {
            bookController.change(changeISBN, changeName, changeSurname, changeTitle, changeYear, changePrice);
        }catch (Exception e){
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public void deleteBook() {
        BookController bookController = new BookController(em, utx);
        try {
            bookController.delete(deleteISBN);
        }catch (Exception e){
            System.out.println("Exception: "+e.getMessage());
        }
    }

}
