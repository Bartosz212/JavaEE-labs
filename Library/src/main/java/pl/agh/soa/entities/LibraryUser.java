package pl.agh.soa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "library_user")
public class LibraryUser {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Loan> loans = new ArrayList<Loan>();

    public LibraryUser(){}

    public LibraryUser(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getFullName(){
        return this.name+" "+this.surname;
    }
}
