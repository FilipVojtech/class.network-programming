package tcp.common;

import java.io.IOException;

public interface NetworkLayer {
    void connect() throws IOException;

    void disconnect() throws IOException;

    void send(String message);

    String receive();
}
