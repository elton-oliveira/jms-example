package br.com.fluentcode.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class MsgSender {
	
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Destination destination = (Destination) context.lookup("FluentCodeQueue");
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
		Connection connection = connectionFactory.createConnection("elton","123");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer messageProducer = session.createProducer(destination);
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("Planejamento é essencial!");
		messageProducer.send(textMessage);
		System.out.println("Mensagem enviada!");
		connection.close();
	}

}
