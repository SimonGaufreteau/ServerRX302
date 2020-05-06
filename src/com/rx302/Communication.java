package com.rx302;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

/**
 * A classe representing the communication between the server and a Client. The connection will be close once the "stop client" command or "stop server RX302" is sent by the client.
 */
public class Communication extends UDPInterface implements Runnable {
	public Communication(InetAddress clientAddress, int clientPort) throws SocketException {
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
			if(message.equals(DISCONNECT_SERVER))
				send("Disconnecting the server...",getSenderAddress(),getSenderPort());
			else{
				send(SERVER_RESPONSE,getSenderAddress(),getSenderPort());
			}
			System.out.println(String.format(MESSAGE_RECEIVED,getSenderAddress(),getSenderPort(),date,message));
		}while (!message.equals(DISCONNECT_CLIENT) && !message.equals(DISCONNECT_SERVER));
		if(message.equals(DISCONNECT_CLIENT))
			send("Closing the connection between "+getSenderAddress()+":"+getSenderPort()+" and the server",getSenderAddress(),UDP_PORT);
		else {
			send(DISCONNECT_SERVER,getSenderAddress(),UDP_PORT);
		}
		close();
	}
}
