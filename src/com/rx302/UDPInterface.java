package com.rx302;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPInterface {
	protected static final int UDP_PORT = 8008;
	protected static final String SERVER_RUNNING = "server RX302 ready";
	protected static final String CONNECT_MESSAGE = "hello server RX302";
	protected static final String DISCONNECT_MESSAGE = "stop server RX302";

	private DatagramSocket socket;
	private DatagramPacket datagramPacket;
	private InetAddress address;

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
