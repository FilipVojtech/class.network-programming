package TCP.TcpServerClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Socket serverSocket = new Socket("localhost", 10077);

            Scanner in = new Scanner(serverSocket.getInputStream());
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream());

            System.out.println("Message to be sent:");
            String message = sc.nextLine();

            out.println(message);
            out.flush();

            String response = in.nextLine();
            System.out.println("Server responded with: " + response);

            out.close();
            in.close();

            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Issue with server socket");
        }
    }
}
