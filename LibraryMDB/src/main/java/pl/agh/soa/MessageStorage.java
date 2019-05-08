package pl.agh.soa;


import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MessageStorage {

    private List<String> messages = new ArrayList<>();

    public MessageStorage() {
    }

    public void addMessage(String msg){
        messages.add(msg);
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<String> getMessagesAndDelete(String fullName){
        List<String> l = messages.stream().filter(str -> isFullNameMatch(str, fullName)).collect(Collectors.toList());
        messages.removeAll(l);
        return l;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Boolean isFullNameMatch(String str, String fullName){
        String[] splitted = str.split(":");
        return splitted[0].equals(fullName);
    }
}
