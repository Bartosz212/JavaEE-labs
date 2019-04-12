package pl.agh.soa.DAO;

import pl.agh.soa.entities.LibraryUser;

import javax.persistence.*;
import java.util.List;

public class UserDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    public UserDAO(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Library");
        entityManager = entityManagerFactory.createEntityManager();
    }


    public List<LibraryUser> getUsers() throws Exception{
        TypedQuery<LibraryUser> userList = entityManager.createQuery("SELECT u FROM library_user u ORDER BY u.name, u.surname", LibraryUser.class);
        return userList.getResultList();
    }

    public LibraryUser getUser(int id) throws Exception{
        TypedQuery<LibraryUser> user =  entityManager.createQuery("SELECT u FROM library_user u WHERE u.id=:id ORDER BY u.name, u.surname", LibraryUser.class).setParameter("id", id);
        return user.getSingleResult();
    }

    public void addUser(String name, String surname) throws Exception{
        LibraryUser add = new LibraryUser(name, surname);
        entityManager.getTransaction().begin();
        entityManager.persist(add);
        entityManager.getTransaction().commit();
    }

    public void deleteUser(int pickedUserID) throws Exception{
        LibraryUser user = (LibraryUser) entityManager.createQuery("SELECT u FROM library_user u WHERE u.id = :id").setParameter("id", pickedUserID).getSingleResult();
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    public void updateUser(int pickedUserID, String editName, String editSurname) throws Exception{
        LibraryUser user = (LibraryUser) entityManager.createQuery("SELECT u FROM library_user u WHERE u.id = :id").setParameter("id", pickedUserID).getSingleResult();
        if(!editName.equals("")){
            user.setName(editName);
        }
        if (!editSurname.equals("")){
            user.setSurname(editSurname);
        }
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();

    }
}
