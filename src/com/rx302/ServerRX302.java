package com.rx302;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerRX302 extends UDPInterface implements Runnable {
	private AtomicBoolean running;

	public ServerRX302(){
		super(8008);
		running=new AtomicBoolean(false);
		System.out.println("Opened server on port "+UDP_PORT);
	}

	@Override
	public void run() {
		running.set(true);
		while (running.get()){
			do{
				receive();
			} while(!getMessageFromBuffer().equals(CONNECT_MESSAGE) && !getMessageFromBuffer().equals(DISCONNECT_SERVER));

			if(getMessageFromBuffer().equals(DISCONNECT_SERVER)){
				System.out.println("Disconnecting the server...");

				running.set(false);
				break;
			}

			InetAddress senderAddress =getSenderAddress();
			int senderPort=getSenderPort();
			System.out.println("Found a new Client with a following address : "+senderAddress+":"+senderPort);
			System.out.println("Creating a new communication with the client...");
			Communication communication =new Communication(senderAddress,senderPort);
			new Thread(communication).start();
			System.out.println("Communication created between the server and the client at "+senderAddress+":"+senderPort);
		}
		close();
	}

	public static void main(String[] args) {
		ServerRX302 serverRX302 = new ServerRX302();
		serverRX302.run();
	}
}
