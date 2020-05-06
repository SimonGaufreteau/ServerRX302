package com.rx302;

import java.net.InetAddress;
import java.util.Date;

public class Communication extends UDPInterface implements Runnable {
	private static final String MESSAGE_RECEIVED ="Message received by %s:%d at %s : %s";
	private static final String SERVER_RESPONSE = "Your message has been received by the server.";
	public Communication(InetAddress clientAddress, int clientPort){
		super();
		send(SERVER_RUNNING,clientAddress,clientPort);
	}

	@Override
	public void run() {
		String message;
		do{
			receive();
			Date date = new Date(System.currentTimeMillis());
			message = getMessageFromBuffer();
			System.out.println(String.format(MESSAGE_RECEIVED,getSenderAddress(),getSenderPort(),date,message));
			send(SERVER_RESPONSE,getSenderAddress(),getSenderPort());
		}while (!message.equals(DISCONNECT_MESSAGE));
		close();
	}
}
