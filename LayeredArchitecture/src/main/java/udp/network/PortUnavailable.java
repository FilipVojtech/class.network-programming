package udp.network;

public class PortUnavailable extends Exception {
    public PortUnavailable() {
        super();
    }

    public PortUnavailable(String message) {
        super(message);
    }
}
