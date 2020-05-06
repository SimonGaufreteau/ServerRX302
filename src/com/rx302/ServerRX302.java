package com.rx302;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple UDP server creating a new Thread for every new Client. Note : no client management is done.
 */
public class ServerRX302 extends UDPInterface implements Runnable {
	private AtomicBoolean running;

	public ServerRX302() throws SocketException {
		super(8008);
		running=new AtomicBoolean(false);
		System.out.println("Opened server on port "+UDP_PORT);
	}

	@Override
	public void run() {
		running.set(true);
		while (running.get()){
			String message;
			do{
				receive();
				Date date = new Date(System.currentTimeMillis());
				message = getMessageFromBuffer();
				System.out.println(String.format(MESSAGE_RECEIVED,getSenderAddress(),getSenderPort(),date,message));
			} while(!message.equals(CONNECT_MESSAGE) && !message.equals(DISCONNECT_SERVER));

			if(getMessageFromBuffer().equals(DISCONNECT_SERVER)){
				System.out.println("Disconnecting the server...");
				running.set(false);
				break;
			}

			InetAddress senderAddress =getSenderAddress();
			int senderPort=getSenderPort();
			System.out.println("Found a new Client with a following address : "+senderAddress+":"+senderPort);
			System.out.println("Creating a new communication with the client...");
			try {
				new Thread( new Communication(senderAddress,senderPort)).start();
			} catch (SocketException e) {
				e.printStackTrace();
			}
			System.out.println("Communication created between the server and the client at "+senderAddress+":"+senderPort);
		}
		close();
	}

	public static void main(String[] args) throws SocketException {
		ServerRX302 serverRX302 = new ServerRX302();
		serverRX302.run();
	}
}
