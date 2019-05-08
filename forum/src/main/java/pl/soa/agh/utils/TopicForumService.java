package pl.soa.agh.utils;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import java.util.*;

@Stateless
public class TopicForumService{

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory cf;

    @Resource(mappedName = "java:/jms/topic/SOA_Test")
    private Topic jmsTopic;

    private List<Topic> topics = new LinkedList<>();

    public void registerConsumer(String topicName, ConsumerMessageListener listener) throws JMSException {
        System.out.println("Registering consumer: " + listener.getConsumerName());
        Connection connection = cf.createConnection();
        connection.setClientID(listener.getConsumerName()+topicName);
        TopicSession topicSession = (TopicSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        for (Topic topic : topics) {
            if (topic.getTopicName().equals(topicName)) {
                System.out.println("Found topic: " + topicName +"\ntest\n");
                System.out.println(listener.getConsumerName()+topicName);
                MessageConsumer consumer = topicSession.createDurableSubscriber(topic, listener.getConsumerName()+topicName);
                System.out.println("Consumer: "+listener.getConsumerName()+" subscribed " +topicName +" successfully!");
                consumer.setMessageListener(listener);
                connection.start();
            }
        }
    }

    public void saveTopic(String topic) throws JMSException {
        Connection connection = cf.createConnection();
        TopicSession topicSession = (TopicSession) connection.createSession();
        Topic newTopic = topicSession.createTopic(topic);
        topics.add(newTopic);
        connection.close();
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

    public void sendMessage(String topicName, String message, String subscribers) throws JMSException {
        Connection connection = null;
        try {
            connection = cf.createConnection();
            TopicSession topicSession = (TopicSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher publisher = topicSession.createPublisher(getJmsTopic(topicName));
            MapMessage mapMessage = topicSession.createMapMessage();
            mapMessage.setString("message", topicName+": "+message);
            mapMessage.setString("subscribers", subscribers);
            System.out.println(topicName + " sends message: " + message + " to: "+ subscribers);
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