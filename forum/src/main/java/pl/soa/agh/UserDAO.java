package pl.soa.agh;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    public UserDAO(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Forum");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<User> getUsers() throws Exception{
        TypedQuery<User> userList = entityManager.createQuery("SELECT u FROM forum_user u ORDER BY u.name, u.surname", User.class);
        return userList.getResultList();
    }

    public User getUser(int loggedUserID) {
        TypedQuery<User> user = entityManager.createQuery("SELECT u FROM forum_user u WHERE u.id=:id ORDER BY u.name, u.surname", User.class).setParameter("id", loggedUserID);
        return user.getSingleResult();
    }
}
