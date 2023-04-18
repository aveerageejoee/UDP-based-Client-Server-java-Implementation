package client.operations;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client(new DatagramSocket());
        client.sendMessageToServer();
    }
}
