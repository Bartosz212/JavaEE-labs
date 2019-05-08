package pl.soa.agh.utils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserMessages implements Serializable {

    private List<String> messages = new ArrayList<String>();

    public void addMessage(String message){
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}