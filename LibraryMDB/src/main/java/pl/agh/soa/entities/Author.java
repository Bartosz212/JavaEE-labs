package pl.agh.soa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "author")
public class Author {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, name = "author_name")
    private String authorName;

    @Column(nullable = false, name = "author_surname")
    private String authorSurname;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Book> books = new ArrayList<Book>();

    public Author(){}

    public Author(String authorName, String authorSurname) {
        this.authorName = authorName;
        this.authorSurname = authorSurname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFullName(){
        return this.authorName+" "+this.authorSurname;
    }
}
