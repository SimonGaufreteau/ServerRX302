package com.rx302;

import java.net.SocketException;
import java.util.Date;
import java.util.Scanner;

/**
 * A runnable class representing a Client connected via UDP to a server on localhost.
 */
public class Client extends UDPInterface implements Runnable{
	public Client() throws SocketException {
		super();
	}

	@Override
	public void run() {
		System.out.println("Sending a connection request to the server...");
		send(CONNECT_MESSAGE,serverAddress,UDP_PORT);
		do{
			receive();
		}while(!getMessageFromBuffer().equals(SERVER_RUNNING));
		String input="";
		Scanner scanner = new Scanner(System.in);
		while (!input.equals(DISCONNECT_CLIENT)){
			System.out.println("Please enter the message to be sent to the server :");
			input=scanner.nextLine();
			send(input,getSenderAddress(),getSenderPort());
			receive();
			Date date = new Date(System.currentTimeMillis());
			System.out.println(String.format(MESSAGE_RECEIVED,getSenderAddress(),getSenderPort(),date,getMessageFromBuffer()));
		}
		close();
		System.out.println("Client disconnected successfully !");
	}

	public static void main(String[] args) throws SocketException {
		Client client = new Client();
		new Thread(client).start();
	}
}
