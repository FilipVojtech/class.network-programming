package udp.client;

import udp.network.UDPService;

public class Main {
    private static UDPService network;

    public static void main(String[] args) {
        network = new UDPService(7777);
    }
}
