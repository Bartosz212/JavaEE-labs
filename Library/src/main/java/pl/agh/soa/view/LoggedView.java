package pl.agh.soa.view;


import pl.agh.soa.controller.Controller;
import pl.agh.soa.entities.Author;
import pl.agh.soa.entities.Book;
import pl.agh.soa.entities.Loan;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ManagedBean(name = "loggedView")
@SessionScoped
public class LoggedView implements Serializable {

    @ManagedProperty(value = "#{loginView.pickedUserID}")
    private int loggedUserId;

    private List<Author> authors;
    private List<Book> books;
    private List<Loan> loans;
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();

    private String authorName;
    private String authorSurname;

    private String editName;
    private String editSurname;

    private int pickedAuthor;
    private int pickedBook;
    private String title;
    private Integer numberOfBooks;
    private Integer numberOfDays;

    private List<Integer> daysList = IntStream.rangeClosed(5, 30).boxed().collect(Collectors.toList());

    Controller controller = new Controller();

    public void endLoans(){
        List<Integer> checkedLoansID = new ArrayList<>();

        for (Map.Entry<Integer, Boolean> entry : checked.entrySet()) {
            if(entry.getValue()){
                checkedLoansID.add(entry.getKey());
            }
        }
        controller.endLoans(checkedLoansID);
        checked.clear();
    }

    public void editAuthor(){
        controller.updateAuthor(pickedAuthor, editName, editSurname);
    }

    public String getUserFullName(){
        return controller.getUserFullName(loggedUserId);
    }

    public List<Loan> getLoans() {
        setLoans(controller.getLoans(loggedUserId));
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public String getBookTitle(int id){
        return controller.getBookTitleByID(id);
    }

    public String getAuthorFullNameByBookID(int id){
        return controller.getAuthorFullNameByBookID(id);
    }

    public List<Book> getBooks(){
        setBooks(controller.getBooks());
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Author> getAuthors() {
        setAuthors(controller.getAuthors());
        return authors;
    }

    public void addAuthor(){
        controller.addAuthor(authorName, authorSurname);
    }

    public void addBook(){
        controller.addBook(pickedAuthor, title, numberOfBooks);
    }

    public void addLoan(){
        controller.addLoan(pickedBook, loggedUserId, numberOfDays);
    }

    public void deleteBook(){
        controller.deleteBook(pickedBook);
    }

    public void deleteAuthor(){
        controller.deleteAuthor(pickedAuthor);
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(int loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public int getPickedAuthor() {
        return pickedAuthor;
    }

    public void setPickedAuthor(int pickedAuthor) {
        this.pickedAuthor = pickedAuthor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(Integer numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    public int getPickedBook() {
        return pickedBook;
    }

    public void setPickedBook(int pickedBook) {
        this.pickedBook = pickedBook;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public List<Integer> getDaysList() {
        return daysList;
    }

    public void setDaysList(List<Integer> daysList) {
        this.daysList = daysList;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public String getEditSurname() {
        return editSurname;
    }

    public void setEditSurname(String editSurname) {
        this.editSurname = editSurname;
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }
}
