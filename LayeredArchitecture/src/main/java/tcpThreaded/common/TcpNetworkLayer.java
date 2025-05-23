package tcpThreaded.common;

import lombok.NonNull;
import tcp.common.NetworkLayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpNetworkLayer implements NetworkLayer {
    private Socket dataSocket;
    private Scanner inputStream;
    private PrintWriter outputStream;
    private String hostname;
    private int port;

    public TcpNetworkLayer(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public TcpNetworkLayer(@NonNull Socket dataSocket) throws IOException {
        this.dataSocket = dataSocket;
        this.inputStream = new Scanner(dataSocket.getInputStream());
        this.outputStream = new PrintWriter(dataSocket.getOutputStream());
    }

    public void connect() throws IOException {
        dataSocket = new Socket(hostname, port);
    }

    public void disconnect() throws IOException {
        if (this.dataSocket != null) {
            this.outputStream.close();
            this.inputStream.close();
            this.dataSocket.close();
        }
    }

    public void send(String message) {
        outputStream.println(message);
        outputStream.flush();
    }

    public String receive() {
        return inputStream.nextLine();
    }
}
