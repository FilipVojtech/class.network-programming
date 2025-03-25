package TCP.TcpServerClient;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class Server {
    public static void main(String[] args) {
        int port = 10077;

        try {
            ServerSocket connectionSocket = new ServerSocket(port);

            while (true) {
                Socket client = connectionSocket.accept();
                Scanner in = new Scanner(client.getInputStream());
                PrintWriter out = new PrintWriter(client.getOutputStream());

                String req = in.nextLine();
                System.out.println("Request: " + req);

                out.println("Request received");
                out.flush();

                out.close();
                in.close();
                client.close();
            }
        } catch (IOException ex) {
            log.error("Could not create socket");
        }
    }
}
