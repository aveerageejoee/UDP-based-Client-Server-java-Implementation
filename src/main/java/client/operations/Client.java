package client.operations;

import MessageObjects.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {

    private final static String clientName = "Client One";
    private DatagramSocket socket = null;

    public Client(DatagramSocket socket) throws UnknownHostException {
        this.socket = socket;
    }

    private byte [] sendingBuffer = new byte[1024];
    private byte[]receivingBuffer = new byte[1024];

    private final InetAddress inetAddress = InetAddress.getByName("localhost");


    public void sendMessageToServer() throws IOException, ClassNotFoundException {

        Message message = new Message(clientName,0);

        objectWrapper(message);
        sendUDP(message);
        System.out.println("Received message from server: " + receiveUDP().toString());
        socket.close();
    }

private void objectWrapper(Message message) throws IOException {

    ByteArrayOutputStream byteStreamOut = new ByteArrayOutputStream();
    ObjectOutputStream objectStreamOut = new ObjectOutputStream(byteStreamOut);
    objectStreamOut.writeObject(message);
    this.sendingBuffer =byteStreamOut.toByteArray();
}

private void sendUDP(Message message) throws IOException {
    DatagramPacket sendPacket = new DatagramPacket(sendingBuffer, sendingBuffer.length, inetAddress, 700);
    socket.send(sendPacket);

}
private Message receiveUDP() throws IOException, ClassNotFoundException {
    DatagramPacket receivePacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
    socket.receive(receivePacket);
    ByteArrayInputStream byteStreamIn = new ByteArrayInputStream(receivePacket.getData());
    ObjectInputStream objectStreamIn = new ObjectInputStream(byteStreamIn);
    Message message = (Message) objectStreamIn.readObject();
    return message;

}



}
