package pl.agh.soa;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;

@Stateless
public class MDBSender {

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jboss/exported/jms/queue/SOA_test")
    private Queue queue;

    public void sendMess(String txt){
        try {
            TextMessage msg = context.createTextMessage(txt);
            context.createProducer().send(queue, msg);
            System.out.println("In send");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
