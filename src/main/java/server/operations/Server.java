package server.operations;

import MessageObjects.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Server {

    private DatagramSocket socket;

    public Server(DatagramSocket socket) {
        this.socket = socket;
    }

    private byte[]receivingBuffer = new byte[1024];
    private byte [] sendingBuffer = new byte[1024];
    private List<ClientCopy> clientsRegistered = new ArrayList<ClientCopy>();
    private final BlockingQueue<Message> incomingRequestQueue = new LinkedBlockingQueue<>();

    public void receiveMessage() throws IOException, ClassNotFoundException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
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
            clientsRegistered.add(new ClientCopy(name, address, port));
            ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            Message message = (Message) objectStream.readObject();
            message.setNumber(message.getNumber() + 1);

            incomingRequestQueue.offer(message);
            executorService.submit(new Worker());
        }
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

    private Message receiveData() throws IOException, ClassNotFoundException {
        DatagramPacket packet = new DatagramPacket(receivingBuffer, receivingBuffer.length);
        System.out.println("waiting for data....");
        socket.receive(packet);
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        String name = new String(packet.getData(), 0, packet.getLength());
        clientsRegistered.add(new ClientCopy(name, address, port));
        ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData());
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Message message = (Message) objectStream.readObject();
        message.setNumber(message.getNumber() + 1);
        return message;

    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                Message message = incomingRequestQueue.take();
                sendData(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}


