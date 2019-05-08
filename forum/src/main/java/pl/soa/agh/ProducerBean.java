package pl.soa.agh;


import pl.soa.agh.utils.TopicForumService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Topic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("producerBean")
@SessionScoped
public class ProducerBean implements Serializable {

    @EJB
    private TopicForumService topicForumService;

    private String newTopicName;
    private String message;
    private String subscribers;

    public void addTopic() throws JMSException {
        topicForumService.saveTopic(newTopicName);
    }

    public void sendDirectMessage(String topic) throws JMSException {
        topicForumService.sendMessage(topic, message, subscribers);
    }

    public void sendMessage(String topic) throws  JMSException{
        topicForumService.sendMessage(topic, message, "");
    }

    public List<String> getTopics() throws JMSException {
        List<String> topics = new ArrayList<>();

        for(Topic topic: topicForumService.findAllTopics()){
            topics.add(topic.getTopicName());
        }
        return topics;
    }

    public String getNewTopicName() {
        return newTopicName;
    }

    public void setNewTopicName(String newTopicName) {
        this.newTopicName = newTopicName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(String subscribers) {
        this.subscribers = subscribers;
    }

    public ProducerBean(){}
}
