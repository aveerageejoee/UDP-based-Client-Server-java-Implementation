package client.operations;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.start();
    }
}
