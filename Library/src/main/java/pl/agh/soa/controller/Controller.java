package pl.agh.soa.controller;

import pl.agh.soa.DAO.AuthorDAO;
import pl.agh.soa.DAO.BookDAO;
import pl.agh.soa.DAO.LoanDAO;
import pl.agh.soa.DAO.UserDAO;
import pl.agh.soa.entities.Author;
import pl.agh.soa.entities.Book;
import pl.agh.soa.entities.LibraryUser;
import pl.agh.soa.entities.Loan;

import java.util.Date;
import java.util.List;

public class Controller {


    UserDAO userDAO = new UserDAO();
    AuthorDAO authorDAO = new AuthorDAO();
    BookDAO bookDAO = new BookDAO();
    LoanDAO loanDAO = new LoanDAO();

    public String getUserFullName(int loggedUserID){
        try{
            return userDAO.getUser(loggedUserID).getFullName();
        }catch (Exception e){
            System.out.println("Controller(getUserFullName): " + e.getMessage());
            return null;
        }
    }

    public List<Book> getBooks() {
        List<Book> list = null;
        try {
            list = bookDAO.getAllBooks();
        }catch (Exception e){
            System.out.println("Controller(getBooks): "+ e.getMessage());
        }
        return list;
    }

    public List<Author> getAuthors() {
        List<Author> list = null;
        try {
            list = authorDAO.getAuthors();
        }catch (Exception e){
            System.out.println("Controller(getAuthors): "+ e.getMessage());
        }
        return list;
    }

    public void addAuthor(String authorName, String authorSurname){
        try {
            authorDAO.addAuthor(authorName, authorSurname);
        }catch (Exception e){
            System.out.println("Controller(addAuthor): "+ e.getMessage());
        }
    }

    public void addBook(int pickedAuthor, String title, int numberOfBooks){
        Author author = null;
        try{
            author = authorDAO.getAuthor(pickedAuthor);
            bookDAO.addBook(author, title, numberOfBooks);
        }catch (Exception e){
            System.out.println("Controller(addBook): "+ e.getMessage());
        }
    }

    public void addLoan(int pickedBook, int loggedUserId, Integer numberOfDays){
        try{
            if(bookDAO.checkIfAvailable(pickedBook)){
                Book book = bookDAO.getBookByID(pickedBook);
                LibraryUser user = userDAO.getUser(loggedUserId);
                loanDAO.addLoan(book, user, numberOfDays);
                bookDAO.decrementNumberOfBooks(book);
            }
        }catch (Exception e){
            System.out.println("Controller(addLoan): "+ e.getMessage());
        }
    }

    public List<LibraryUser> getUsers() {
        List<LibraryUser> list = null;
        try {
            list = userDAO.getUsers();
        }catch (Exception e){
            System.out.println("Controller(getUsers): "+ e.getMessage());
        }
        return list;
    }

    public void addUser(String name, String surname){
        try {
            userDAO.addUser(name, surname);
        }catch (Exception e){
            System.out.println("Controller(addUser): "+ e.getMessage());
        }
    }

    public List<Loan> getLoans(int userID){
        List<Loan> list = null;
        try{
            list = loanDAO.getUserLoans(userID);
        }catch (Exception e){
            System.out.println("Controller(getLoans): "+ e.getMessage());
        }
        return list;
    }

    public String getBookTitleByID(int id){
        String s = null;
        try{
            s = bookDAO.getBookByID(id).getTitle();
        }catch (Exception e){
            System.out.println("Controller(getBookTitleByID): "+ e.getMessage());
        }
        return s;
    }


    public String getAuthorFullNameByBookID(int id) {
        String s = null;
        try{
            s = bookDAO.getBookByID(id).getAuthor().getFullName();
        }catch (Exception e){
            System.out.println("Controller(getAuthorFullNameByBookID): "+ e.getMessage());
        }
        return s;
    }

    public void deleteBook(int pickedBook) {
        try{
            bookDAO.deleteBook(pickedBook);
        }catch (Exception e){
            System.out.println("Controller(deleteBook): "+e.getMessage());
        }
    }

    public void deleteAuthor(int pickedAuthor) {
        try{
            authorDAO.deleteAuthor(pickedAuthor);
        }catch (Exception e){
            System.out.println("Controller(deleteBook): "+e.getMessage());
        }
    }

    public void deleteUser(int pickedUserID) {
        try{
            userDAO.deleteUser(pickedUserID);
        }catch (Exception e){
            System.out.println("Controller(deleteUser): "+ e.getMessage());
        }
    }

    public void updateUser(int pickedUserID, String editName, String editSurname) {
        try{
            userDAO.updateUser(pickedUserID, editName, editSurname);
        }catch (Exception e){
            System.out.println("Controller(updateUser): "+e.getMessage());
        }
    }

    public void updateAuthor(int pickedAuthor, String editName, String editSurname) {
        try{
            authorDAO.updateAuthor(pickedAuthor, editName, editSurname);
        }catch (Exception e){
            System.out.println("Controller(updateAuthor): "+e.getMessage());
        }
    }

    public void endLoans(List<Integer> checkedLoansID) {
        try{
            for (Integer id: checkedLoansID) {
                Book book = loanDAO.changeLoanStatus(id);
                bookDAO.incrementNumberOfBooks(book);
            }
        }catch (Exception e){
            System.out.println("Controller(endLoans): "+ e.getMessage());
        }
    }

    public List<LibraryUser> searchUsersByBook(int pickedBook, Date filterFromDate, Date filterToDate) {
        List<LibraryUser> users = null;
        try{
            Book book = bookDAO.getBookByID(pickedBook);
            users = loanDAO.getUsersByBookWithDates(book, filterFromDate, filterToDate);
        }catch (Exception e){
            System.out.println("Controller(searchUsersByBook-dates): "+ e.getMessage());
        }
        return users;
    }

    public List<LibraryUser> searchUsersByBook(int pickedBook) {
        List<LibraryUser> users = null;
        try{
            Book book = bookDAO.getBookByID(pickedBook);
            users = loanDAO.getUsersByBook(book);
        }catch (Exception e){
            System.out.println("Controller(searchUsersByBook-noDates): "+ e.getMessage());
        }
        return users;
    }

    public List<LibraryUser> searchUsersByAuthor(int pickedAuthor, Date filterFromDate, Date filterToDate) {
        List<LibraryUser> users = null;
        try{
            Author author = authorDAO.getAuthor(pickedAuthor);
            List<Book> books = bookDAO.getAllAuthorBooks(author);
            users = loanDAO.getUsersByBooksWithDates(books, filterFromDate, filterToDate);
        }catch (Exception e){
            System.out.println("Controller(searchUsersByAuthor-dates): "+ e.getMessage());
        }
        return users;
    }

    public List<LibraryUser> searchUsersByAuthor(int pickedAuthor) {
        List<LibraryUser> users = null;
        try{
            Author author = authorDAO.getAuthor(pickedAuthor);
            List<Book> books = bookDAO.getAllAuthorBooks(author);
            users = loanDAO.getUsersByBooks(books);
        }catch (Exception e){
            System.out.println("Controller(searchUsersByAuthor-noDates): "+ e.getMessage());
        }
        return users;
    }

    public List<Author> searchAuthorsByUser(int pickedUser, Date filterFromDate, Date filterToDate) {
        List<Author> authors = null;
        try {
            LibraryUser libraryUser = userDAO.getUser(pickedUser);
            List<Book> books = loanDAO.getBooksByUser(libraryUser, filterFromDate, filterToDate);
            authors = bookDAO.getAuthorsByBooks(books);
        }catch (Exception e){
            System.out.println("Controller(searchAuthorsByUser-dates): "+e.getMessage());
        }
        return authors;
    }

    public List<Author> searchAuthorsByUser(int pickedUser) {
        List<Author> authors = null;
        try {
            LibraryUser libraryUser = userDAO.getUser(pickedUser);
            List<Book> books = loanDAO.getBooksByUser(libraryUser);
            authors = bookDAO.getAuthorsByBooks(books);
        }catch (Exception e){
            System.out.println("Controller(searchAuthorsByUser-noDates): "+e.getMessage());
        }
        return authors;
    }

    public void updateBook(int pickedBook, String editTitle) {
        try{
            bookDAO.updateBook(pickedBook, editTitle);
        }catch (Exception e){
            System.out.println("Controller(updateBook): "+e.getMessage());
        }
    }

    public List<Object[]> getAuthorsWithCounter() {
        List<Object[]> list = null;
        try{
            list = loanDAO.getLoanedAuthors();
        }catch (Exception e){
            System.out.println("Controller(getAuthorsListWithCounter): "+e.getMessage());
        }
        return list;
    }
}
