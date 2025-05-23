package tcpThreaded.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static tcpThreaded.common.MovieUtilities.*;

public class MovieServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = new ServerSocket(PORT);
        MovieManager movieManager = new MovieManager();
        UserManager userManager = new UserManager();
        boolean isRunning = true;

        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                ServiceClientHandler clientHandler = new ServiceClientHandler(clientSocket, movieManager, userManager);
                Thread wrapper = new Thread(clientHandler);
                wrapper.start();
            } catch (IOException e) {
                System.out.println("Connection error");
            }
        }
    }
}
