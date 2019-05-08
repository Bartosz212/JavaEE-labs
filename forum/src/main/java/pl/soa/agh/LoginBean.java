package pl.soa.agh;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private Controller controller = new Controller();

    private List<User> users;

    private int pickedUserID;


    public String logIn(){
        System.out.println("ID: "+pickedUserID);
        return "logged";
    }

    public int getPickedUserID() {
        return pickedUserID;
    }

    public void setPickedUserID(int pickedUserID) {
        this.pickedUserID = pickedUserID;
    }

    public List<User> getUsers() {
        setUsers(controller.getAllUsers());
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
