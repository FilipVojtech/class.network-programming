package tcpThreaded.server;

import tcp.business.Movie;

import java.util.HashMap;
import java.util.Iterator;

public class MovieManager {
    private static int movieIdCount = 0;
    private HashMap<Integer, Movie> movies;

    public MovieManager() {
        this.movies = new HashMap<>();
    }

    public boolean add(String name, int year, String genre) {
        Movie m = new Movie(movieIdCount, name, year, genre);
        movieIdCount++;

        return add(m);
    }

    public boolean add(Movie m) {
        if (!movies.containsKey(m.getId())) {
            movies.put(m.getId(), m);
            return true;
        }
        return false;
    }

    public Movie remove(int movieId) {
        if (movieId < 0) return null;

        return movies.remove(movieId);
    }

    public Movie[] getAll() {
        Movie[] movieArray = new Movie[movies.size()];
        movies.values().toArray(movieArray);
        return movieArray;
    }

    public boolean isEmpty() {
        return movies.isEmpty();
    }

    public boolean contains(int movieId) {
        return movies.containsKey(movieId);
    }

    public Iterator<Movie> getIter() {
        return movies.values().iterator();
    }
}
