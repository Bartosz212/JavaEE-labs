package pl.agh.soa.view;

import pl.agh.soa.BookNotificationService;
import pl.agh.soa.controller.Controller;
import pl.agh.soa.entities.LibraryUser;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.JMSException;

@ManagedBean(name = "loginView")
@SessionScoped
public class LoginView implements Serializable {

    @EJB
    BookNotificationService bookNotificationService;


    private List<LibraryUser> users;

    private int pickedUserID;

    private String name;
    private String surname;
    private Boolean isSubscriber;

    private String editName;
    private String editSurname;


    private Controller controller;

    @PostConstruct
    public void init(){
        controller = new Controller(bookNotificationService);
        try {
            bookNotificationService.saveTopic("NewBook");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public String logIn(){
        System.out.println("ID: "+pickedUserID);
        return "logged";
    }

    public void updateUser(){
        controller.updateUser(pickedUserID, editName, editSurname);
    }

    public void deleteUser(){
        controller.deleteUser(pickedUserID);
    }

    public List<LibraryUser> getUsers(){
        setUsers(controller.getUsers());
        return users;
    }

    public void addUser(){
        controller.addUser(name, surname, isSubscriber);
    }

    public void setUsers(List<LibraryUser> users) {
        this.users = users;
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

    public int getPickedUserID() {
        return pickedUserID;
    }

    public void setPickedUserID(int pickedUserID) {
        this.pickedUserID = pickedUserID;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public String getEditSurname() {
        return editSurname;
    }

    public Boolean getSubscriber() {
        return isSubscriber;
    }

    public void setSubscriber(Boolean subscriber) {
        isSubscriber = subscriber;
    }

    public void setEditSurname(String editSurname) {
        this.editSurname = editSurname;
    }
}
