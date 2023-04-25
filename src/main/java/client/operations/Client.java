package client.operations;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public Client() {
    }

    public void start() throws SocketException, UnknownHostException {
        DatagramSocket socket = new DatagramSocket(702);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MessageHandlerClient handler = new MessageHandlerClient(socket);
        executorService.submit(handler);
    }
    /*public void sendMessageToServer() throws IOException, ClassNotFoundException {

        Message message = new Message(clientName, 0);

        objectWrapper(message);
        sendUDP(message);
        System.out.println("Received message from server: " + receiveUDP().toString());
        socket.close();
    }*/




}



