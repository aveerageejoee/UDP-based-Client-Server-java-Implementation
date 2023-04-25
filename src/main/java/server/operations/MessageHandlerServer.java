package server.operations;

import MessageObjects.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MessageHandlerServer implements  Runnable{

    private DatagramSocket socket;

    public MessageHandlerServer(DatagramSocket socket) {
        this.socket = socket;
    }

    private byte[]receivingBuffer = new byte[1024];
    private byte [] sendingBuffer = new byte[1024];
    private List<ClientCopy> clientsRegistered = new ArrayList<ClientCopy>();
    @Override
    public void run() {
        try {
            receiveMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void receiveMessage() throws IOException, ClassNotFoundException, InterruptedException {

        while(true) {
            //Message message = receiveData();
            // message.setNumber(message.getNumber() + 1);
            // sendData(message);
            DatagramPacket packet = new DatagramPacket(receivingBuffer, receivingBuffer.length);
            System.out.println("waiting for data....");
            socket.receive(packet);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String name = new String(packet.getData(), 0, packet.getLength());
            checkIfRegistered(name, address, port);

            ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            Message message = (Message) objectStream.readObject();
            message.setNumber(message.getNumber() + 1);

            sendData(message);
        }
    }

    private void checkIfRegistered(String name, InetAddress address, int port) {
        boolean registered = false;
        for(ClientCopy c: clientsRegistered){
            if(c.getNamer().equals(name)) registered=true;
            break;
        }
        if(!registered)clientsRegistered.add(new ClientCopy(name,address,port));

    }

    private void sendData(Message message) throws IOException {
        ByteArrayOutputStream byteStreamOut = new ByteArrayOutputStream();
        ObjectOutputStream objectStreamOut = new ObjectOutputStream(byteStreamOut);
        objectStreamOut.writeObject(message);
        sendingBuffer = byteStreamOut.toByteArray();
        for(ClientCopy c : clientsRegistered){
            DatagramPacket sendPacket = new DatagramPacket(sendingBuffer, sendingBuffer.length, c.getAddress(), c.getPort());
            socket.send(sendPacket);
        }

    }

}
