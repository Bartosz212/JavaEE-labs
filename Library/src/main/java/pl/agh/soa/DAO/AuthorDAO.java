package pl.agh.soa.DAO;

import pl.agh.soa.entities.Author;
import pl.agh.soa.entities.LibraryUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    public AuthorDAO(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Library");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Author> getAuthors() throws Exception{
        TypedQuery<Author> userList = entityManager.createQuery("SELECT a FROM author a ORDER BY a.authorName, a.authorSurname", Author.class);
        return userList.getResultList();
    }

    public Author getAuthor(int id) throws Exception{
        TypedQuery<Author> user =  entityManager.createQuery("SELECT a FROM author a WHERE a.id=:id", Author.class).setParameter("id", id);
        return user.getSingleResult();
    }

    public void addAuthor(String name, String surname) throws Exception{
        Author add = new Author(name, surname);
        entityManager.getTransaction().begin();
        entityManager.persist(add);
        entityManager.getTransaction().commit();
    }

    public void deleteAuthor(int pickedAuthor) throws Exception {
        Author author = (Author) entityManager.createQuery("SELECT a FROM author a WHERE a.id = :id").setParameter("id", pickedAuthor).getSingleResult();
        entityManager.getTransaction().begin();
        entityManager.remove(author);
        entityManager.getTransaction().commit();
    }

    public void updateAuthor(int pickedAuthor, String editName, String editSurname) throws Exception{
        Author author = (Author) entityManager.createQuery("SELECT a FROM author a WHERE a.id = :id").setParameter("id", pickedAuthor).getSingleResult();
        if(!editName.equals("")){
            author.setAuthorName(editName);
        }
        if (!editSurname.equals("")){
            author.setAuthorSurname(editSurname);
        }
        entityManager.getTransaction().begin();
        entityManager.merge(author);
        entityManager.getTransaction().commit();
    }
}
