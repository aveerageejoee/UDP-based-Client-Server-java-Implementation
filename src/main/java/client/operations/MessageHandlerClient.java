package client.operations;

import MessageObjects.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;


public class MessageHandlerClient implements Runnable {


    private DatagramSocket socket = null;

    private byte[] sendingBuffer = new byte[1024];
    private byte[] receivingBuffer = new byte[1024];
    private final InetAddress inetAddress = InetAddress.getByName("localhost");

    public MessageHandlerClient() throws UnknownHostException {
    }

    public MessageHandlerClient(DatagramSocket socket) throws UnknownHostException {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){

            Message message  = new Message(UUID.randomUUID().toString(),0);
            try {
                Thread.sleep(1000);
                objectWrapper(message);
                sendUDP(message);
                receiveUDP();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void objectWrapper(Message message) throws IOException {

        ByteArrayOutputStream byteStreamOut = new ByteArrayOutputStream();
        ObjectOutputStream objectStreamOut = new ObjectOutputStream(byteStreamOut);
        objectStreamOut.writeObject(message);
        this.sendingBuffer = byteStreamOut.toByteArray();
    }

    private void sendUDP(Message message) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(sendingBuffer, sendingBuffer.length, inetAddress, 700);
        socket.send(sendPacket);

    }

    private void receiveUDP() throws IOException, ClassNotFoundException {
        DatagramPacket receivePacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
        socket.receive(receivePacket);
        ByteArrayInputStream byteStreamIn = new ByteArrayInputStream(receivePacket.getData());
        ObjectInputStream objectStreamIn = new ObjectInputStream(byteStreamIn);
        Message message = (Message) objectStreamIn.readObject();
        System.out.println("Message received from" + message.getSendersName());
    }
}
