package common;

public class InventoryUtils {
    // Protocol Info
    public static final String HOST = "127.0.0.1";
    public static final int SERVER_PORT = 15700;
    public static final int CLIENT_PORT = 17555;

    // Delimiters
    public static final String DELIMITER = "##";
    public static final String SUB_DELIMITER = "%%";

    // REQUESTS
    // ADD
    public static final String ADD_COMMAND = "ADD";

    // DELETE
    public static final String DELETE_COMMAND = "REMOVE";

    // GET
    public static final String GET_COMMAND = "GET";

    // Search
    public static final String SEARCH_COMMAND = "SEARCH";


    // RESPONSES
    // Shared
    public static final String UNKNOWN = "UNKNOWN";
    public static final String INVALID_CATEGORY = "INVALID_CATEGORY";
    public static final String NON_NUMERIC_ID = "NON_NUMERIC_ID";
    public static final String ID_NOT_FOUND = "ID_NOT_FOUND";

    // ADD
    public static final String ADDED = "ADDED";
    public static final String ID_TAKEN = "ID_TAKEN";
    public static final String NON_NUMERIC_DATA = "NON_NUMERIC_DATA";

    // DELETE
    public static final String REMOVED = "REMOVED";

    // GET
    public static final String NO_MATCHES_FOUND = "NO_MATCHES_FOUND";
}
