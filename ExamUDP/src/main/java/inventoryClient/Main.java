package inventoryClient;

import common.InventoryUtils;
import common.UDPService;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import static common.InventoryUtils.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        UDPService network;

        try {
            network = new UDPService(HOST, SERVER_PORT, CLIENT_PORT);
            network.connect();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host.");
            return;
        } catch (SocketException e) {
            System.out.println("Could not establish connection");
            return;
        }

        while (isRunning) {
            try {
                System.out.print("> ");
                String input = sc.nextLine();

                if (input.equals("EXIT")) {
                    System.exit(0);
                }
                network.send(input);
                String response = network.receive();

                System.out.println(response);
            } catch (IOException e) {
                System.out.println("Could not send request.");
                continue;
            }
        }

        network.disconnect();
    }
}
