package com.rx302;

import java.io.IOException;
import java.net.*;

/**
 * A class representing an UDP interface used in {@link ServerRX302},  {@link Client} and {@link Communication}.
 */
public class UDPInterface {
	protected static final int UDP_PORT = 8008;
	protected static final String SERVER_RUNNING = "server RX302 ready";
	protected static final String CONNECT_MESSAGE = "hello server RX302";
	protected static final String DISCONNECT_SERVER = "stop server RX302";
	protected static final String DISCONNECT_CLIENT = "stop client";
	protected static final String SERVER_RESPONSE = "Your message has been received by the server.";
	protected static final String MESSAGE_RECEIVED ="Message received by %s:%d at %s : %s";

	protected static InetAddress serverAddress;
	static {
		try {
			serverAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private byte[] buffer;
	private DatagramSocket socket;
	private DatagramPacket datagramPacket;

	public static final int MIN_PORT=1024;
	public static final int MAX_PORT=4096;
	public static final int MAX_MESSAGE_LENGTH=1024;

	/**
	 * Creates a new Interface with the first port available (after 1024)
	 */
	public UDPInterface() throws SocketException {
		this(getFirstAvailablePort());
	}

	public UDPInterface(int port) throws SocketException {
		socket = new DatagramSocket(port);
		buffer= new byte[MAX_MESSAGE_LENGTH];
		datagramPacket=new DatagramPacket(buffer,buffer.length);
	}

	/**
	 * Sends a message (represented by a String) to the given destination.
	 */
	public void send(String message, InetAddress destination,int port){
		buffer=new byte[message.length()];
		buffer = message.getBytes();
		datagramPacket = new DatagramPacket(buffer,buffer.length,destination,port);
		try {
			socket.send(datagramPacket);
		} catch (IOException e) {
			System.out.println("Could not send the packet with the following message : "+message);
			e.printStackTrace();
		}
	}

	/**
	 * Receives a packet. This method will pause the Thread until a new packet is received.
	 */
	public void receive(){
		buffer= new byte[MAX_MESSAGE_LENGTH];
		datagramPacket=new DatagramPacket(buffer,buffer.length);
		try {
			socket.receive(datagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buffer=datagramPacket.getData();

	}

	/**
	 * Closes the socket corresponding to the interface.
	 */
	public void close(){
		socket.close();
	}

	/**
	 * Returns the message contained in the buffer.
	 */
	public String getMessageFromBuffer(){
		return new String(buffer).trim();
	}

	/**
	 * Returns the address of the last received message's sender.
	 */
	public InetAddress getSenderAddress(){
		return datagramPacket.getAddress();
	}

	/**
	 * Returns the port of the last received message's sender.
	 */
	public int getSenderPort(){
		return datagramPacket.getPort();
	}

	/**
	 * Returns the first available port between min and max.
	 */
	public static int getFirstAvailablePort(int min,int max) throws SocketException {
		DatagramSocket datagramSocket;
		for(int i=min;i<max;i++){
			try{
				datagramSocket = new DatagramSocket(i);
			}catch (SocketException ex){
				continue;
			}
			datagramSocket.close();
			return i;
		}
		throw new SocketException("Impossible to find an available port between "+min+" and "+max+".");
	}

	public static int getFirstAvailablePort() throws SocketException {
		return getFirstAvailablePort(MIN_PORT,MAX_PORT);
	}
}
