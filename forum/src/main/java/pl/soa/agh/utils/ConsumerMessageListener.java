package pl.soa.agh.utils;

import javax.jms.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ConsumerMessageListener implements MessageListener, Serializable {

    private String consumerName;
    private UserMessages allMessages;

    public ConsumerMessageListener(String consumerName, UserMessages messages) {
        this.consumerName = consumerName;
        allMessages = messages;
    }

    public ConsumerMessageListener(String consumerName){
        this.consumerName = consumerName;
    }

    public void onMessage(Message m) {
        try {
            MapMessage mapMessage = (MapMessage) m;
            String message = mapMessage.getString("message");
            String subscribers = mapMessage.getString("subscribers");
            if (shouldReceiveMessage(subscribers)) {
                System.out.println("Consumer " + consumerName +" received message: " +message);
                allMessages.addMessage(message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String getConsumerName() {
        return consumerName;
    }

    private boolean shouldReceiveMessage(String subscribers) {
        return subscribers.isEmpty()
                || subscribers.trim().length() == 0
                || Arrays
                .stream(subscribers.split(","))
                .anyMatch(s -> s.equals(consumerName));
    }

    public List<String> getAllMessages() {
        return allMessages.getMessages();
    }
}