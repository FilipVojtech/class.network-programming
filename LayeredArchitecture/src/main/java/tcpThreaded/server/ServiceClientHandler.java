package tcpThreaded.server;

import lombok.extern.slf4j.Slf4j;
import tcp.business.Movie;
import tcpThreaded.business.User;
import tcpThreaded.common.TcpNetworkLayer;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Iterator;

import static tcpThreaded.common.MovieUtilities.*;

@Slf4j
public class ServiceClientHandler implements Runnable {
    private final Socket clientDataSocket;
    private final TcpNetworkLayer network;
    private final MovieManager movieManager;
    private final UserManager userManager;
    private User sessionUser;
    private boolean isActive;

    public ServiceClientHandler(
            Socket clientDataSocket,
            MovieManager movieManager,
            UserManager userManager
    ) throws IOException {
        this.clientDataSocket = clientDataSocket;
        this.network = new TcpNetworkLayer(this.clientDataSocket);

        this.movieManager = movieManager;
        this.userManager = userManager;

        this.sessionUser = null;
        this.isActive = true;
    }

    @Override
    public void run() {
        try {
            while (isActive) {
                String request = network.receive();
                String[] components = request.split(DELIMITER);
                String response;

                System.out.println("Request: " + request);

                response = switch (components[0]) {
                    case LOGIN -> handleLogin(components);
                    case LOGOUT -> handleLogout();
                    case ADD -> handleAdd(components);
                    case LIST -> handleList();
                    case REMOVE -> handleDelete(components);
                    case EXIT -> handleExit();
                    default -> INVALID;
                };

                network.send(response);
            }

            network.disconnect();
        } catch (IOException e) {
            log.error("Client socket errored out");
        }
    }

    private String handleLogin(String[] components) {
        if (components.length != 3) return INVALID;

        User u = userManager.getUser(components[1]);

        if (u.getPassword().equals(components[2])) {
            sessionUser = u;
            return SUCCESS;
        } else return INVALID_LOGIN;
    }

    private String handleLogout() {
        if (sessionUser != null) sessionUser = null;
        isActive = false;
        return SUCCESS;
    }

    private String handleAdd(String[] components) {
        if (sessionUser == null) return UNAUTHENTICATED;
        if (components.length != 4) return INVALID;

        int year;
        try {
            year = Integer.parseInt(components[2]);
            if (year > LocalDate.now().getYear()) return INVALID_YEAR;
        } catch (NumberFormatException e) {
            return NON_NUMERIC;
        }

        movieManager.add(components[1], year, components[3]);
        return ADDED;
    }

    private String handleList() {
        if (sessionUser == null) return UNAUTHENTICATED;
        if (movieManager.isEmpty()) return NO_MOVIES_FOUND;

        StringBuffer sb = new StringBuffer();

        for (Iterator<Movie> it = movieManager.getIter(); it.hasNext(); ) {
            var movie = it.next();
            sb.append(movie.getId()).append(DELIMITER);
            sb.append(movie.getName()).append(DELIMITER);
            sb.append(movie.getYear()).append(DELIMITER);
            sb.append(movie.getGenre()).append(SUB_DELIMITER);
        }

        return sb.substring(0, sb.length() - SUB_DELIMITER.length());
    }

    private String handleDelete(String[] components) {
        if (sessionUser == null) return UNAUTHENTICATED;
        if (components.length != 2) return INVALID;

        int id;
        try {
            id = Integer.parseInt(components[1]);
        } catch (NumberFormatException e) {
            return NON_NUMERIC;
        }

        if (!movieManager.contains(id)) return NOT_FOUND;

        movieManager.remove(id);
        return REMOVED;
    }

    private String handleExit() {
        isActive = false;
        return SUCCESS;
    }
}
