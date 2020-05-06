package com.rx302;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPInterface {
	protected static final int UDP_PORT = 8008;
	protected static final String SERVER_RUNNING = "server RX302 ready";
	protected static final String CONNECT_MESSAGE = "hello server RX302";
	protected static final String DISCONNECT_SERVER = "stop server RX302";
	protected static final String DISCONNECT_CLIENT = "stop client";
	protected static final String SERVER_RESPONSE = "Your message has been received by the server.";
	protected static final String MESSAGE_RECEIVED ="Message received by %s:%d at %s : %s";


	private DatagramSocket socket;
	private DatagramPacket datagramPacket;
	protected static InetAddress serverAddress;

	static {
		try {
			serverAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static final int MIN_PORT=1024;
	public static final int MAX_PORT=4096;

	public UDPInterface(){}

	public UDPInterface(int port){}

	public void send(String message, InetAddress destination,int port){}

	public void receive(){}

	public void close(){}

	public String getMessageFromBuffer(){return null;}

	public InetAddress getSenderAddress(){return null;}

	public int getSenderPort(){return 0;}

	public int getFirstAvailablePort(int min,int max){return 0;}

	public int getFirstAvailablePort(){return 0;}
}
