package pl.soa.agh;

import pl.soa.agh.utils.ConsumerMessageListener;
import pl.soa.agh.utils.TopicForumService;
import pl.soa.agh.utils.UserMessages;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Topic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("consumerBean")
@SessionScoped
public class ConsumerBean implements Serializable {

    @Inject
    LoginBean loginBean;

    private String userFullName;
    private User loggedUser;

    private List<String> topics;
    private String pickedTopic;
    private UserMessages userMessages = new UserMessages();

    @EJB
    private TopicForumService topicForumService;
    private Controller controller = new Controller();
    private ConsumerMessageListener listener;

    @PostConstruct
    public void init(){
        int loggedUserID = loginBean.getPickedUserID();
        System.out.println(loggedUserID);
        loggedUser = controller.getUserByID(loggedUserID);
        setUserFullName(loggedUser.getFullName());
        listener = new ConsumerMessageListener(userFullName, userMessages);
    }

    public String getUserFullName(){
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    private void register(String topicName) throws JMSException {
        System.out.println("Between register a new listener");
        topicForumService.registerConsumer(topicName, listener);
    }

    public void performSub() {
        try {
            System.out.println("Before register");
            register(pickedTopic);
        } catch (JMSException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void resetOutput(){
        userMessages.getMessages().clear();
    }

    public List<String> getTopics() throws JMSException {
        List<String> topics = new ArrayList<>();
        for(Topic topic: topicForumService.findAllTopics()){
            topics.add(topic.getTopicName());
        }
        setTopics(topics);
        return this.topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getMessages() {
        return userMessages.getMessages();
    }

    public void setMessages(List<String> messages) {
        this.userMessages.setMessages(messages);
    }

    public void refresh(){
        setMessages(listener.getAllMessages());
    }

    public String getPickedTopic() {
        return pickedTopic;
    }

    public void setPickedTopic(String pickedTopic) {
        this.pickedTopic = pickedTopic;
    }
}
