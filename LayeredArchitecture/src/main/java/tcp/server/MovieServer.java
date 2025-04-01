package tcp.server;

import java.net.ServerSocket;
import java.net.Socket;

import tcp.business.User;
import tcp.common.NetworkLayer;
import tcp.common.TcpNetworkLayer;

import static tcp.common.MovieUtilities.*;

public class MovieServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = new ServerSocket(PORT);
        MovieManager movies = new MovieManager();
        UserManager users = new UserManager();
        boolean isRunning = true;
        User sessionUser = null;

        while (isRunning) {
            Socket clientSocket = serverSocket.accept();
            NetworkLayer network = new TcpNetworkLayer(clientSocket);
            boolean hasClientSession = true;

            while (hasClientSession) {
                String request = network.receive();
                String response = INVALID;
                String[] components = request.split(DELIMITER);

                System.out.println("Request: " + request);

                switch (components[0]) {
                    case LOGIN -> {
                        if (components.length != 3) break;

                        User u = users.getUser(components[1]);
                        if (u.getPassword().equals(components[2])) {
                            sessionUser = u;
                            response = SUCCESS;
                        } else response = INVALID_LOGIN;
                    }
                    case LOGOUT -> {
                        if (sessionUser != null)
                            sessionUser = null;
                        response = SUCCESS;
                    }
                    case ADD -> {
                        if (sessionUser == null) {
                            response = UNAUTHENTICATED;
                            break;
                        }
                    }
                    case LIST -> {
                    }
                    case REMOVE -> {
                    }
                    case EXIT -> {
                    }
                }

                network.send(response);
            }
        }
    }
}
