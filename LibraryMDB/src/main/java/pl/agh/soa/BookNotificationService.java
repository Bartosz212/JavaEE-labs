package pl.agh.soa;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class BookNotificationService {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory cf;

    @Resource(mappedName = "java:/jms/topic/SOA_Test")
    private Topic jmsTopic;

    private List<Topic> topics = new LinkedList<>();

    public void registerConsumer(String topicName, UserMessageListener listener) throws JMSException {
        //System.out.println("Registering consumer: " + listener.getUserName()+":"+topicName);
        Connection connection = cf.createConnection();
        connection.setClientID(listener.getUserName()+topicName);
        TopicSession topicSession = (TopicSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        for (Topic topic : topics) {
            if (topic.getTopicName().equals(topicName)) {
//                System.out.println("Found topic: " + topicName +"\ntest\n");
//                System.out.println(listener.getUserName()+topicName);
                MessageConsumer consumer = topicSession.createDurableSubscriber(topic, listener.getUserName()+topicName);
//                System.out.println("Consumer: "+listener.getUserName()+" subscribed " +topicName +" successfully!");
                consumer.setMessageListener(listener);
                connection.start();
            }
        }
    }

    public void saveTopic(String topic) throws JMSException {
        if (topics.stream().noneMatch(t -> {
            try {
                return t.getTopicName().equals("jms.topic."+topic);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            return false;
        })){
            Connection connection = cf.createConnection();
            TopicSession topicSession = (TopicSession) connection.createSession();
            Topic newTopic = topicSession.createTopic(topic);
//            System.out.println("Added topic: " + topic);
            topics.add(newTopic);
            connection.close();
        }
    }

    public List<Topic> findAllTopics() {
        return topics;
    }

    private Topic getJmsTopic(String topicName) throws JMSException {
        for (Topic topic : topics) {
            if (topic.getTopicName().equals(topicName)) {
                return topic;
            }
        }
        return null;
    }

    public void sendMessage(String topicName, String message, String subscribers){
//        System.out.println("Tu??");
        Connection connection = null;
//        System.out.println("Przed try"+topicName+" "+message);
        try {
            connection = cf.createConnection();
//            System.out.println("Przed connection"+topicName+" "+message);
            TopicSession topicSession = (TopicSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicPublisher publisher = topicSession.createPublisher(getJmsTopic(topicName));
            MapMessage mapMessage = topicSession.createMapMessage();
            Date d = new Date();
//            System.out.println("Po connection"+topicName+" "+message);
            mapMessage.setString("message", d.toString()+": "+message);
            mapMessage.setString("subscribers", subscribers);
//            System.out.println(topicName + " sends message: " + message + " to: "+ subscribers);
            publisher.publish(mapMessage);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ignored) {
                }
            }
        }
    }
}

