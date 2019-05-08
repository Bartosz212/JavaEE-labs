package pl.soa.agh;

import java.util.List;

public class Controller {

    UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers(){
        List<User> list = null;
        try {
            list = userDAO.getUsers();
        }catch (Exception e){
            System.out.println("Controller(getUsers): "+ e.getMessage());
        }
        return list;
    }

    public User getUserByID(int loggedUserID){
        try{
            return userDAO.getUser(loggedUserID);
        }catch (Exception e){
            System.out.println("Controller(getUserFullName): " + e.getMessage());
            return null;
        }
    }
}
