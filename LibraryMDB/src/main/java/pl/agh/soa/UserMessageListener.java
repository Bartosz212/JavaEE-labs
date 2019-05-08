package pl.agh.soa;

import javax.jms.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class UserMessageListener implements MessageListener, Serializable {

    private String userName;
    private UserMessages allMessages;

    public UserMessageListener(String userName, UserMessages messages) {
        this.userName = userName;
        this.allMessages = messages;
    }

    public UserMessageListener(String userName){
        this.userName = userName;
    }

    public void onMessage(Message m) {
        try {
            MapMessage mapMessage = (MapMessage) m;
            String message = mapMessage.getString("message");
            String subscribers = mapMessage.getString("subscribers");
            if (shouldReceiveMessage(subscribers)) {
                System.out.println("Consumer " + userName +" received message: " +message);
                allMessages.addMessage(message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    private boolean shouldReceiveMessage(String subscribers) {
        return subscribers.isEmpty()
                || subscribers.trim().length() == 0
                || Arrays
                .stream(subscribers.split(","))
                .anyMatch(s -> s.equals(userName));
    }

    public List<String> getAllMessages() {
        return allMessages.getMessages();
    }
}