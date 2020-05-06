package com.rx302;

import java.net.SocketException;
import java.util.Date;
import java.util.Scanner;

public class Client extends UDPInterface implements Runnable{
	public Client() throws SocketException {
		super();
	}

	@Override
	public void run() {
		send(CONNECT_MESSAGE,serverAddress,UDP_PORT);
		do{
			receive();
		}while(!getMessageFromBuffer().equals(SERVER_RESPONSE));
		String input="";
		Scanner scanner = new Scanner(System.in);
		while (!input.equals(DISCONNECT_CLIENT)){
			System.out.println("Please enter the message to be sent to the server :");
			input=scanner.nextLine();
			send(input,serverAddress,UDP_PORT);
			receive();
			Date date = new Date(System.currentTimeMillis());
			System.out.println(String.format(MESSAGE_RECEIVED,getSenderAddress(),getSenderPort(),date,getMessageFromBuffer()));
		}
		close();
		System.out.println("Client disconnected successfully !");
	}

	public static void main(String[] args) throws SocketException {
		Client client = new Client();
		client.run();
	}
}
