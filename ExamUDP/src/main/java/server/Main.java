package server;

import common.UDPService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static common.InventoryUtils.*;

@Slf4j
public class Main {
    static IProductManager productManager = new ProductManager();
    static UDPService network;

    public static void main(String[] args) {
        try {
            network = new UDPService(HOST, SERVER_PORT, CLIENT_PORT);
            network.connect();
        } catch (UnknownHostException e) {
            log.error("Could not connect to host {}", HOST);
        } catch (SocketException e) {
            log.error("Could not open connection");
        }

        try {
            while (true) {
                String request = network.receive();
                String[] requestFragments = request.split(DELIMITER);
                String response = UNKNOWN;

                switch (requestFragments[0].toUpperCase()) {
                    case ADD_COMMAND -> {
                        if (requestFragments.length != 5) break;
                        int id;
                        double unitPrice;
                        try {
                            id = Integer.parseInt(requestFragments[1]);
                            unitPrice = Double.parseDouble(requestFragments[4]);
                        } catch (NumberFormatException e) {
                            response = NON_NUMERIC_DATA;
                            break;
                        }
                        String name = requestFragments[2];
                        String category = requestFragments[3];

                        if (productManager.contains(id)) {
                            response = ID_TAKEN;
                            break;
                        }

                        if (!Product.validCategories.contains(category)) {
                            response = INVALID_CATEGORY;
                            break;
                        }

                        var addResult = productManager.add(id, name, category, unitPrice);
                        if (addResult) response = ADDED;
                    }
                    case DELETE_COMMAND -> {
                        if (requestFragments.length != 2) break;
                        int id;
                        try {
                            id = Integer.parseInt(requestFragments[1]);
                        } catch (NumberFormatException e) {
                            response = NON_NUMERIC_ID;
                            break;
                        }
                        var removedProd = productManager.delete(id);
                        if (removedProd == null) {
                            response = ID_NOT_FOUND;
                            break;
                        }

                        response = REMOVED;
                    }
                    case GET_COMMAND -> {
                        if (requestFragments.length != 2) break;
                        int id;
                        try {
                            id = Integer.parseInt(requestFragments[1]);
                        } catch (NumberFormatException e) {
                            response = NON_NUMERIC_ID;
                            break;
                        }

                        var product = productManager.get(id);

                        if (product == null) {
                            response = ID_NOT_FOUND;
                            break;
                        }

                        response = product.toString();
                    }
                    case SEARCH_COMMAND -> {
                        if (requestFragments.length != 2) break;
                        String category = requestFragments[1];
                        if (!Product.validCategories.contains(category)) {
                            response = INVALID_CATEGORY;
                            break;
                        }

                        var products = productManager.search(category);
                        if (products.length == 0) {
                            response = NO_MATCHES_FOUND;
                            break;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (var product : products) {
                            sb.append(product.toString()).append(SUB_DELIMITER);
                        }
                        // Get products response without
                        response = sb.substring(0, sb.length() - SUB_DELIMITER.length());
                    }
                }

                network.send(response);
            }
        } catch (IOException e) {
            log.error("Could not receive request");
        } finally {
            network.disconnect();
        }
    }
}
