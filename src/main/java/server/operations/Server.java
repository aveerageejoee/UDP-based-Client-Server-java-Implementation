package server.operations;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.*;

public class Server {
    public Server() {
    }

    public void start() throws SocketException {

       DatagramSocket socket = new DatagramSocket(700);
       ExecutorService executorService = Executors.newFixedThreadPool(5);
       MessageHandlerServer handler = new MessageHandlerServer(socket);
       executorService.submit(handler);

   }

}


