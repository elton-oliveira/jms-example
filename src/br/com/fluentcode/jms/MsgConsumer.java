package br.com.fluentcode.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class MsgConsumer {
	
	private final static boolean asynchronousConsumer = true;
	private final static boolean durableTopic = true;
	
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Destination destination = (Destination) context.lookup("FluentCodeTopic");
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
		Connection connection = connectionFactory.createConnection("elton", "123");
		if(durableTopic){
			connection.setClientID("fluentCodeId");
		}
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer messageConsumer;
		if (durableTopic) {
			messageConsumer = session.createDurableSubscriber((Topic) destination, "assinaturaMundoAgil");
		} else {
			messageConsumer = session.createConsumer(destination);
		}
		connection.start();
		if(asynchronousConsumer){
			messageConsumer.setMessageListener(new AsynchronousConsumer());
			synchronized (args) {
				args.wait();
			}
		}else{
			Message message = messageConsumer.receive();
			TextMessage textMessage = (TextMessage) message;
			System.out.println(textMessage.getText());
			connection.close();
		}
		
		
	}
	
	public static class AsynchronousConsumer implements MessageListener {

		@Override
		public void onMessage(Message message) {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println(textMessage.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
