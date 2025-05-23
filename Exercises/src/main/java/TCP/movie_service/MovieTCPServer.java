package TCP.movie_service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

public class MovieTCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket connectionSocket = new ServerSocket(MovieUtilities.PORT);

            MovieManager movieManager = new MovieManager();

            movieManager.add("The Room", 2003, "Drama");
            movieManager.add("Shrek", 2001, "Comedy");

            while (true) {
                Socket clientDataSocket = connectionSocket.accept();
                // Set up streams to communicate
                Scanner in = new Scanner(clientDataSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientDataSocket.getOutputStream());

                // Receive a message
                String request = in.nextLine();
                System.out.println("Request: " + request);

                String response = MovieUtilities.INVALID;

                String[] components = request.split(MovieUtilities.DELIMITER);
                switch (components[0]) {
                    case MovieUtilities.ADD:
                        if (components.length == 4) {
                            try {
                                String name = components[1];

                                int year = Integer.parseInt(components[2]);
                                if (year > LocalDate.now().getYear()) {
                                    response = MovieUtilities.INVALID_YEAR;
                                    break;
                                }

                                String genre = components[3];

                                movieManager.add(name, year, genre);
                                response = MovieUtilities.ADDED;
                            } catch (NumberFormatException e) {
                                response = MovieUtilities.NON_NUMERIC;
                            }
                        }
                        break;
                    case MovieUtilities.REMOVE:
                        if (components.length != 2) break;

                        int id;

                        try {
                            id = Integer.parseInt(components[1]);
                        } catch (NumberFormatException e) {
                            response = MovieUtilities.NON_NUMERIC;
                            break;
                        }

                        if (movieManager.remove(id) != null) response = MovieUtilities.REMOVED;
                        else response = MovieUtilities.NOT_FOUND;

                        break;
                    case MovieUtilities.LIST:
                        if (movieManager.isEmpty()) {
                            response = MovieUtilities.NO_MOVIES_FOUND;
                            break;
                        }

                        StringBuilder sb = new StringBuilder();
                        for (var movie : movieManager.getAll()) {
                            sb
                                    .append(movie.getId()).append(MovieUtilities.DELIMITER)
                                    .append(movie.getName()).append(MovieUtilities.DELIMITER)
                                    .append(movie.getYear()).append(MovieUtilities.DELIMITER)
                                    .append(movie.getGenre());
                            sb.append(MovieUtilities.SUB_DELIMITER);
                        }
                        response = sb.substring(0, sb.length() - MovieUtilities.SUB_DELIMITER.length());
                        break;
                }

                out.println(response);
                out.flush();

                // Shut down communication
                out.close();
                in.close();

                // Shut down socket
                clientDataSocket.close();

            }
        } catch (IOException e) {
            System.out.println("Connection socket cannot be established");
        }
    }
}
