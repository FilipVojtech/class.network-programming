package tcpThreaded.common;

public class MovieUtilities {
    public static final String HOSTNAME = "localhost";
    public static final int PORT = 11000;

    // REQUESTS
    public static final String LOGIN = "LOGIN";
    public static final String LOGOUT = "LOGOUT";
    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";
    public static final String LIST = "LIST";
    public static final String EXIT = "EXIT";
    
    // DELIMITER
    public static final String DELIMITER = "%%";
    public static final String SUB_DELIMITER = "~~";
    
    // RESPONSES
    public static final String ADDED = "ADDED";
    public static final String NON_NUMERIC = "NON_NUMERIC_DATA";
    public static final String INVALID_YEAR = "INVALID_YEAR";
    public static final String REMOVED = "REMOVED";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String NO_MOVIES_FOUND = "NO_MOVIES_FOUND";
    public static final String SUCCESS = "SUCCESS";
    public static final String INVALID_LOGIN = "INVALID_LOGIN";

    // GENERAL MALFORMED RESPONSE:
    public static final String INVALID = "INVALID";
    public static final String UNAUTHENTICATED = "UNAUTHENTICATED";
}
