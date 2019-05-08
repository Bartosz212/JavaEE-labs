package pl.agh.soa.view;


import pl.agh.soa.*;
import pl.agh.soa.controller.Controller;
import pl.agh.soa.entities.Author;
import pl.agh.soa.entities.Book;
import pl.agh.soa.entities.LibraryUser;
import pl.agh.soa.entities.Loan;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.*;
import javax.jms.Queue;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//@JMSDestinationDefinition(
//        name = "jms/topic/SOA_Test",
//        interfaceName = "javax.jms.Topic",
//        destinationName = "jms/topic/SOA_Test"
//)
@ManagedBean(name = "loggedView")
@SessionScoped
public class LoggedView implements Serializable {

    @ManagedProperty(value = "#{loginView.pickedUserID}")
    private int loggedUserId;
    private LibraryUser loggedUser;

    @EJB
    BookNotificationService bookNotificationService;
    @EJB
    MessageStorage messageStorage;
    @EJB
    MDBSender mdbSender;

    private List<Author> authors;
    private List<Book> books;
    private List<Book> unavailableBooks;
    private List<Loan> loans;
    private List<LibraryUser> libraryUsers;
    private List<LibraryUser> filteredUsers;
    private List<Author> filteredAuthors;
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();

    private String authorName;
    private String authorSurname;

    private String editName;
    private String editSurname;
    private String editTitle;
    private String pickedSubscribeBook;

    private int pickedAuthor;
    private int pickedBook;
    private int pickedUser;
    private String title;
    private Integer numberOfBooks;
    private Integer numberOfDays;

    private Date filterFromDate;
    private Date filterToDate;
    private Boolean checkWithDates;

    private List<Object[]> authorsListWithCounter;


    private List<Integer> daysList = IntStream.rangeClosed(5, 30).boxed().collect(Collectors.toList());

    private Controller controller;
    private UserMessageListener userMessageListener;
    private UserMessages userMessages;

    @PostConstruct
    public void init(){
        controller = new Controller(bookNotificationService, mdbSender, loggedUserId);
        loggedUser = controller.getUserByID(loggedUserId);
        userMessages = new UserMessages();
        userMessageListener = new UserMessageListener(loggedUser.getFullName(), userMessages);
        if (loggedUser.getSubscriber()){
            try {
                bookNotificationService.registerConsumer("jms.topic.NewBook", userMessageListener);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        getBooks().forEach(b -> {
            try {
                bookNotificationService.saveTopic(b.getTitle());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }


    public void subscribeBook(){
        try {
            bookNotificationService.registerConsumer("jms.topic."+pickedSubscribeBook, userMessageListener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMessages() {
        return userMessages.getMessages();
    }

    public void setMessages(List<String> messages) {
        this.userMessages.setMessages(messages);
    }

    public void refresh(){
        setMessages(userMessageListener.getAllMessages());
    }

    public void getAuthorsWithCounter(){
        setAuthorsListWithCounter(controller.getAuthorsWithCounter());
    }

    public void editBook(){
        if(!editTitle.equals("")) {
            controller.updateBook(pickedBook, editTitle);
        }
    }

    public void searchAuthorsByUser(){
        if(checkWithDates && filterFromDate != null && filterToDate != null){
            setFilteredAuthors(controller.searchAuthorsByUser(pickedUser, filterFromDate, filterToDate));
        }else{
            setFilteredAuthors(controller.searchAuthorsByUser(pickedUser));
        }
    }

    public void searchUsersByBook(){
        if(checkWithDates && filterFromDate != null && filterToDate != null){
            setFilteredUsers(controller.searchUsersByBook(pickedBook, filterFromDate, filterToDate));
        }else{
            setFilteredUsers(controller.searchUsersByBook(pickedBook));
        }
    }

    public void searchUsersByAuthor(){
        if(checkWithDates && filterFromDate != null && filterToDate != null){
            setFilteredUsers(controller.searchUsersByAuthor(pickedAuthor, filterFromDate, filterToDate));
        }else{
            setFilteredUsers(controller.searchUsersByAuthor(pickedAuthor));
        }
    }

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

    public List<LibraryUser> getLibraryUsers() {
        setLibraryUsers(controller.getUsers());
        return libraryUsers;
    }

    public void setLibraryUsers(List<LibraryUser> libraryUsers) {
        this.libraryUsers = libraryUsers;
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

    public List<Book> getUnavailableBooks() {
        setUnavailableBooks(controller.getUnavailableBooks());
        return unavailableBooks;
    }

    public void setUnavailableBooks(List<Book> unavailableBooks) {
        this.unavailableBooks = unavailableBooks;
    }

    public String getPickedSubscribeBook() {
        return pickedSubscribeBook;
    }

    public void setPickedSubscribeBook(String pickedSubscribeBook) {
        this.pickedSubscribeBook = pickedSubscribeBook;
    }

    public void addAuthor(){
        controller.addAuthor(authorName, authorSurname);
        messageSucces();
    }

    public void addBook(){
        controller.addBook(pickedAuthor, title, numberOfBooks);
        messageSucces();
    }

    public void addLoan(){
        controller.addLoan(pickedBook, loggedUserId, numberOfDays);
        messageSucces();
    }

    public void deleteBook(){
        controller.deleteBook(pickedBook);
        messageSucces();
    }

    public void deleteAuthor(){
        controller.deleteAuthor(pickedAuthor);
        messageSucces();
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

    public Date getFilterFromDate() {
        return filterFromDate;
    }

    public void setFilterFromDate(Date filterFromDate) {
        this.filterFromDate = filterFromDate;
    }

    public Date getFilterToDate() {
        return filterToDate;
    }

    public void setFilterToDate(Date filterToDate) {
        this.filterToDate = filterToDate;
    }

    public Boolean getCheckWithDates() {
        return checkWithDates;
    }

    public void setCheckWithDates(Boolean checkWithDates) {
        this.checkWithDates = checkWithDates;
    }

    public List<LibraryUser> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<LibraryUser> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public List<Author> getFilteredAuthors() {
        return filteredAuthors;
    }

    public void setFilteredAuthors(List<Author> filteredAuthors) {
        this.filteredAuthors = filteredAuthors;
    }

    public int getPickedUser() {
        return pickedUser;
    }

    public void setPickedUser(int pickedUser) {
        this.pickedUser = pickedUser;
    }

    public String getEditTitle() {
        return editTitle;
    }

    public void setEditTitle(String editTitle) {
        this.editTitle = editTitle;
    }

    public List<Object[]> getAuthorsListWithCounter() {
        return authorsListWithCounter;
    }

    public void setAuthorsListWithCounter(List<Object[]> authorsListWithCounter) {
        this.authorsListWithCounter = authorsListWithCounter;
    }

    private void messageSucces(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> l = messageStorage.getMessagesAndDelete(getUserFullName());
        for (String s: l) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Sukces", s.split(":")[1]));
        }
    }
}
