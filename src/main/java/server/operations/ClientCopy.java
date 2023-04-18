package server.operations;

import java.net.InetAddress;

public class ClientCopy {

    private String namer = "";
    private InetAddress address;
    private int port ;

    public void setNamer(String namer) {
        this.namer = namer;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNamer() {
        return namer;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public ClientCopy(String namer, InetAddress address, int port) {
        this.namer = namer;
        this.address = address;
        this.port = port;
    }
}
