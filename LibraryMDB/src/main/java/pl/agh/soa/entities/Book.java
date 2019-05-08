package pl.agh.soa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "book")
public class Book {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "number_of_items")
    private Integer numberOfItems;

    @OneToMany(mappedBy = "bookID", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Loan> loans = new ArrayList<Loan>();

    public Book() {}

    public Book(Author author, String title, Integer numberOfItems) {
        this.author = author;
        this.title = title;
        this.numberOfItems = numberOfItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
