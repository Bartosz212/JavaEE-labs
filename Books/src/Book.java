import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "book")
@RequestScoped
public class Book {

    private String title;
    private String author;
    private String genre;
    private Double price;
    private String currency;
    private String originalCurrency;
    private Integer numberOfPages;

    Book(String _title, String _author, String _genre, Double _price, String _currency, Integer _numberOfPages) {

        this.title = _title;
        this.author = _author;
        this.genre = _genre;
        this.price = _price;
        this.currency = _currency;
        this.originalCurrency = _currency;
        this.numberOfPages = _numberOfPages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }
}