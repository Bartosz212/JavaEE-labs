package pl.agh.soa.entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "loan")
public class Loan {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private LibraryUser user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "loan_from", nullable = false)
    private Date loanDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "loan_to", nullable = false)
    private Date returnDate;

    @Column(name = "is_loaned", nullable = false)
    private Boolean isLoaned = true;

    public Loan() {
    }

    public Loan(Book bookID, LibraryUser user, Date loanDate, Date returnDate) {
        this.bookID = bookID;
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBookID() {
        return bookID;
    }

    public void setBookID(Book bookID) {
        this.bookID = bookID;
    }

    public LibraryUser getUser() {
        return user;
    }

    public void setUser(LibraryUser user) {
        this.user = user;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getLoaned() {
        return isLoaned;
    }

    public void setLoaned(Boolean loaned) {
        isLoaned = loaned;
    }

    public String getFormattedLoanDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(loanDate);
    }

    public String getFormattedReturnDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(returnDate);
    }

    public String getStatus(){
        if(isLoaned){
            return "Wypożyczona";
        }else {
            return "Oddana";
        }
    }
}
