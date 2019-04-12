import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;

@ManagedBean(name = "bookCatalog")
@RequestScoped
public class BookCatalog{

    private ArrayList<Book> books;
    private ArrayList<Book> filteredBooks;
    private Double lowBorder;
    private Double highBorder;
    private String currency;
    private String titleFragment;
    private String categoryFragment;

    public BookCatalog() { }

    @PostConstruct
    public void init() {
        books = new ArrayList<>();
        books.add(new Book("Lalka", "Boleslaw Prus", "epopeja", 50d, "PLN", 280));
        books.add(new Book("Krzyzacy", "Henryk Sienkiewicz", "historyczna", 40d, "PLN", 640));
        books.add(new Book("Rok 1984", "George Orwell", "polityczna", 15d, "USD", 330));
        books.add(new Book("Millenium", "Stieg Larsson", "kryminal", 12d, "EUR", 530));
        books.add(new Book("Wladca Pierscieni", "Tolkien", "fantasy", 18d, "EUR", 780));

    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public Double getLowBorder() {
        return lowBorder;
    }

    public void setLowBorder(Double lowBorder) {
        this.lowBorder = lowBorder;
    }

    public Double getHighBorder() {
        return highBorder;
    }

    public void setHighBorder(Double highBorder) {
        this.highBorder = highBorder;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTitleFragment() {
        return this.titleFragment;
    }

    public void setTitleFragment(String titleFragment) {
        this.titleFragment = titleFragment;
    }


    public String getCategoryFragment() {
        return categoryFragment;
    }

    public void setCategoryFragment(String categoryFragment) {
        this.categoryFragment = categoryFragment;
    }

    public ArrayList<Book> getFilteredBooks() {

        filteredBooks = BookCurrencyConverter.convertBookCatalogCurrency(this.books, this.currency);
        filteredBooks = BookCatalog.filterPrice(filteredBooks, this.lowBorder, this.highBorder);
        filteredBooks = BookCatalog.filterName(filteredBooks, this.titleFragment);
        filteredBooks = BookCatalog.filterCategory(filteredBooks, this.categoryFragment);
        return filteredBooks;
    }

    public void setFilteredBooks(ArrayList<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }
    private static ArrayList<Book> filterPrice(ArrayList<Book> inputBooks, Double lowBorder, Double highBorder) {
        if(lowBorder == null && highBorder == null){
            return inputBooks;
        }

        ArrayList<Book> filteredBooks = new ArrayList<>();

        for (Book book : inputBooks) {
            if(lowBorder == null && book.getPrice() <= highBorder){
                filteredBooks.add(book);
            }else if(highBorder == null && book.getPrice() >= lowBorder){
                filteredBooks.add(book);
            }else if (highBorder != null && lowBorder != null && book.getPrice() >= lowBorder && book.getPrice() <= highBorder) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    private static ArrayList<Book> filterName(ArrayList<Book> inputBooks, String titleFragment) {

        if(titleFragment == null){
            return inputBooks;
        }

        ArrayList<Book> filteredBooks = new ArrayList<>();

        for (Book book : inputBooks) {
            if(book.getTitle().toLowerCase().contains(titleFragment.toLowerCase())){
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    private static ArrayList<Book> filterCategory(ArrayList<Book> inputBooks, String categoryFragment) {

        if(categoryFragment == null){
            return inputBooks;
        }

        ArrayList<Book> filteredBooks = new ArrayList<>();

        for (Book book : inputBooks) {
            if(book.getGenre().toLowerCase().contains(categoryFragment.toLowerCase())){
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }
}