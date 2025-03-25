package UDP.authenticationService.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
public class Main {
    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<>();

        users.put("bellatinker", new User("bellatinker", "password", LocalDate.of(2008, 10, 28)));
        users.put("wallaceleedale", new User("wallaceleedale", "password", LocalDate.of(1989, 11, 4)));
        users.put("maxwellbay", new User("maxwellbay", "password", LocalDate.of(2014, 10, 23)));
        users.put("miawallace", new User("miawallace", "password", LocalDate.of(1970, 4, 29)));
        users.put("katherinejohnson", new User("katherinejohnson", "password", LocalDate.of(1918, 8, 26)));

        // Establish listening port
        int port = 2121;

        boolean isRunning = true;
        int packetsReceived = 0;

        while (isRunning) {
            // Create a window for server to access network
            try (DatagramSocket mySocket = new DatagramSocket(port)) {
                // Array to store incoming message
                byte[] payload = new byte[1024 * 64];
                // Packet to store incoming message
                DatagramPacket incomingPacket = new DatagramPacket(payload, payload.length);
                // Try to receive the message
                System.out.println("Waiting for packet....");
                mySocket.receive(incomingPacket);
                System.out.println("Packet received!");
                packetsReceived++;

                // Extract sender information from received packet:
                InetAddress clientIP = incomingPacket.getAddress();
                int clientPort = incomingPacket.getPort();

                // Extract data from received packet:
                int len = incomingPacket.getLength();
                String incomingMessage = new String(payload, 0, len);
                String[] messageFragments = incomingMessage.split("%%");

                // Create response message
                String response = "";
                switch (messageFragments[0].toLowerCase()) {
                    case "auth" -> {
                        if (messageFragments.length < 3 /* Command + username + pass */) {
                            response = "BAD_REQUEST";
                        } else {
                            String username = messageFragments[1];
                            String password = messageFragments[2];

                            User user = users.get(username);

                            if (user != null && user.getPassword().equals(password)) {
                                response = "SUCCESS";
                                log.info("User ({}) logged in.", username);
                            } else {
                                response = "INCORRECT_CREDENTIALS";
                                log.warn("Failed login attempt ({})", username);
                            }
                        }
                    }
                    default -> response = "BAD_REQUEST";
                }

                // Display to screen:
                System.out.println(clientIP.getHostAddress() + ":" + clientPort + " -> " + incomingMessage);

                // TRANSMISSION:
                // Build byte array out of reply (without padding)
                byte[] payloadToBeSent = response.getBytes();
                // Build packet to hold the information
                DatagramPacket sendingPacket = new DatagramPacket(payloadToBeSent, payloadToBeSent.length, clientIP,
                        clientPort);
                // Send the response packet
                mySocket.send(sendingPacket);
                System.out.println("Response sent.");
            } catch (BindException e) {
                System.out.println("Port already in use. Please supply a different port next time!");
            } catch (SocketException e) {
                System.out.println("An error occurred while setting up local socket.");
            } catch (IOException e) {
                System.out.println("Problem occurred when waiting to receive/receiving a message. Please try again later.");
            }
            System.out.println();
        }

        System.out.println("The server has received " + packetsReceived + " messages in total.");
        System.out.println("Server shutting down...");
    }
}
