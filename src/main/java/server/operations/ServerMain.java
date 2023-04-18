package server.operations;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server(new DatagramSocket(700));
        server.receiveMessage();
    }
}
