package network;

import java.net.*;

public class UDPService {
    private final DatagramSocket socket;
    private final InetAddress address;

    /**
     * Set network interactions for localhost
     *
     * @param port Port to attach to
     */
    public UDPService(int port) throws PortUnavailable {
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException("Could not establish connection to localhost");
        }

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new PortUnavailable("Port " + port + " in unavailable.");
        }

    }

    public UDPService(String host, int port) throws UnknownHostException, SocketException {
        socket = new DatagramSocket(port);
        address = InetAddress.getByName(host);
    }

    public void send(String message) {
        byte[] payload = message.getBytes();
        DatagramPacket packet = new DatagramPacket(payload, payload.length);
    }

    public void receive() {

    }

    public void disconnect() {
        if (!socket.isClosed())
            socket.close();
    }
}
